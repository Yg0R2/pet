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
        booleanParam(name: 'DEPLOY', description: 'Deploy Docker container', defaultValue: true)
        booleanParam(name: 'RELEASE', description: 'Perform release?', defaultValue: false)
        string(name: 'RELEASE_VERSION', description: 'Release version', defaultValue: '', trim: true)
        string(name: 'NEXT_SNAPSHOT_VERSION', description: 'Nex snapshot version', defaultValue: '', trim: true)
    }

    stages {
        stage('Preparation') {
            steps {
                script {
                    env.RELEASE = params.RELEASE as boolean \
                        && params.RELEASE_VERSION as boolean \
                        && params.NEXT_SNAPSHOT_VERSION as boolean

                    def props = readProperties file: 'gradle.properties'
                    env.VERSION = env.RELEASE == 'true' ? params.RELEASE_VERSION : props['version']
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
                    return env.RELEASE == 'true'
                }
            }
            steps {
                buildDescription "Release"

                gradleExec('release', [
                    '-Prelease.useAutomaticVersion=true',
                    "-Prelease.releaseVersion=${params.RELEASE_VERSION}",
                    "-Prelease.newVersion=${params.NEXT_SNAPSHOT_VERSION}"
                ])
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
                exec('docker-compose down')

                script {
                    if (isUnix()) {
                        sh "VERSION=${DOCKER_IMAGE_VERSION} docker-compose up -d"
                    }
                    else {
                        bat("SET \"VERSION=${DOCKER_IMAGE_VERSION}\" && docker-compose up -d")
                    }
                }
            }
        }
    }
}

def gradleExec(String tasks, List<String> params = [], boolean returnStdout = false) {
    if (isUnix()) {
        return sh(script: "./gradlew $args.tasks ${params.join(' ')} --stacktrace", returnStdout: returnStdout)
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
        return bat(script: script, returnStdout: returnStdout)
    }
}
