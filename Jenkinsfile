#!/usr/bin/env groovy

@Library('pet-pipeline') _

pipeline {
    agent any

    options {
        skipDefaultCheckout()

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
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM',
                    branches: [[name: "${BRANCH_NAME}"]],
                    doGenerateSubmoduleConfigurations: false,
                    extensions: [
                        [$class: 'SubmoduleOption', disableSubmodules: false, parentCredentials: true, recursiveSubmodules: true, reference: '', trackingSubmodules: false],
                        [$class: 'LocalBranch', localBranch: "${BRANCH_NAME}"]
                    ],
                    submoduleCfg: [],
                    userRemoteConfigs: [[credentialsId: 'Git', url: 'git@github.com:Yg0R2/pet.git']]
                ])
            }
        }

        stage('Preparation') {
            steps {
                script {
                    def props = readProperties file: 'gradle.properties'
                    env.VERSION = props['version']
                }
                buildName "#${BUILD_NUMBER} - ${VERSION}"

                echo 'Environment variables:'
                printEnv()
            }
        }

        stage('Build') {
            steps {
                gradleExec(['clean'])

                gradleExec(['build', '-x test', '-x :ui:build'])
            }
        }

        stage('Unit Test') {
            steps {
                gradleExec(['test', '-x build', '-x jacocoTestReport', '-x :acceptance-test:test', '-x :ui:test'])
            }
            post {
                always {
                    junit '**/test-results/**/*.xml'
                }
            }
        }

        stage('Test coverage') {
            steps {
                gradleExec(['jacocoTestReport'])
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
                gradleExec([':acceptance-test:test', '-x jacocoTestReport'])

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
                gradleExec([':ui:build'])
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

                gradleExec(tasks: ['release'], params: ['-Prelease.useAutomaticVersion=true'])
            }
            post {
                always {
                    script {
                        env.VERSION = exec(script: 'git describe --abbrev=0', returnStdout: true)
                    }
                    buildName "#${BUILD_NUMBER} - ${VERSION}"
                    buildDescription "Release"
                }
            }
        }

        stage('Prepare Docker Image') {
            steps {
                gradleExec(tasks: ['docker', '-x build'], params: ["-Pversion=${VERSION}"])

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
