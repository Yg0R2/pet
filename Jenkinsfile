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
                    def props = readProperties file: 'gradle.properties'
                    env.PROJECT_VERSION = props['version']

                    env.RELEASE = params.RELEASE as boolean \
                        && params.RELEASE_VERSION as boolean \
                        && params.NEXT_SNAPSHOT_VERSION as boolean
                }
                buildName "#${BUILD_NUMBER} - ${PROJECT_VERSION}"

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
                buildName "#${BUILD_NUMBER} - ${params.RELEASE_VERSION}"
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
                script {
                    def version = env.RELEASE == 'true' ? params.RELEASE_VERSION : env.PROJECT_VERSION
                    gradleExec('docker -x build', ["-Pversion=${version}"])
                }
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

                exec('docker-compose up -d')
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