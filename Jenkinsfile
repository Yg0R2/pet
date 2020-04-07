#!/usr/bin/env groovy

pipeline {
    agent any

    options {
        disableConcurrentBuilds()

        timestamps()

        buildDiscarder(logRotator(numToKeepStr: '10'))

        timeout(time: 10, unit: 'MINUTES')
    }

    parameters {
        booleanParam(name: 'DEPLOY', defaultValue: true)
        booleanParam(name: 'RELEASE', defaultValue: false)
    }

    stages {
        stage('Preparation') {
            steps {
                script {
                    def props = readProperties file: 'gradle.properties'
                    env.VERSION = props['version']
                }
                buildName "#${BUILD_NUMBER} - ${VERSION}"

                echo 'Environment variables:'
                script {
                    if (isUnix()) {
                        sh 'printenv'
                    }
                    else {
                        bat 'SET'
                    }
                }
            }
        }

        stage('Build') {
            steps {
                gradleExec('clean')

                gradleExec('build -x test -x :ui:build')
            }
        }

        stage('Unit Test') {
            steps {
                gradleExec('test -x build -x jacocoTestReport -x :acceptance-test:test -x :ui:test')
            }
            post {
                always {
                    junit '**/test-results/**/*.xml'
                }
            }
        }

        stage('Test coverage') {
            steps {
                gradleExec('jacocoTestReport')
            }
            post {
                always {
                    publishCoverage adapters: [
                        jacocoAdapter('**/reports/jacoco/**/jacocoTestReport.xml')
                    ]
                }
            }
        }

        stage('Acceptance Test') {
            steps {
                gradleExec(':acceptance-test:test -x jacocoTestReport')

                junit 'acceptance-test/build/test-results/**/*.xml'
            }
            post {
                always {
                    junit '**/test-results/**/*.xml'
                }
            }
        }

        stage('Build UI') {
            steps {
                gradleExec(':ui:build')
            }
        }

        stage('Release artifacts') {
            when {
                expression {
                    return params.RELEASE
                }
            }
            steps {
                buildDescription "Release"

                gradleExec('release', ['-Prelease.useAutomaticVersion=true'])
            }
            post {
                always {
                    script {
                        env.VERSION = exec('git describe --abbrev=0', true)
                    }
                    buildName "#${BUILD_NUMBER} - ${VERSION}"
                }
            }
        }

        stage('Prepare Docker Image') {
            steps {
                gradleExec('docker -x build', ["-Pversion=${VERSION}"])

                exec("docker tag yg0r2/pet yg0r2/pet:${VERSION}")
            }
        }

        stage('Deploy') {
            when {
                expression {
                    return params.DEPLOY
                }
            }
            steps {
                script {
                    try {
                        exec('docker stop -t 0 pet')
                    }
                    catch (error) {
                    }

                    try {
                        exec('docker rm -f pet')
                    }
                    catch (error) {
                    }

                    exec("docker run -d --rm -p 80:80 --name pet --network=\"pet-network\" yg0r2/pet:${VERSION}")
                }
            }
        }
    }

    post {
        always {
            script {
                if (params.RELEASE) {
                    cleanWs()
                }
            }
        }
    }
}

def gradleExec(String tasks, List<String> params = [], boolean returnStdout = false) {
    if (isUnix()) {
        return sh(script: "./gradlew $tasks ${params.join(' ')} --stacktrace", returnStdout: returnStdout)
    }
    else {
        return bat(script: "./gradlew.bat $tasks ${params.join(' ')} --stacktrace", returnStdout: returnStdout)
    }
}

def exec(String script, boolean returnStdout = false) {
    if (isUnix()) {
        return sh(script: script, returnStdout: returnStdout)
    }
    else {
        def result = bat(script: script, returnStdout: returnStdout)

        if (result as boolean) {
            result = result.trim().readLines().drop(1).join(" ")
        }

        return result
    }
}
