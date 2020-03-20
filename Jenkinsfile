#!/usr/bin/env groovy

pipeline {
    agent any

    options {
        disableConcurrentBuilds()

        timestamps()

        buildDiscarder(logRotator(numToKeepStr: '10'))

        timeout(time: 10, unit: 'MINUTES')
    }

    stages {
        stage('Preparation') {
            steps {
                buildName "${BUILD_DISPLAY_NAME}"
                //./gradlew properties -q | grep "version:" | awk '{print $2}'
            }
        }

        stage('Build') {
            steps {
                bat './gradlew.bat clean --stacktrace'

                bat './gradlew.bat build -x test -x :ui:build --stacktrace'
            }
        }

        stage('Unit Test') {
            steps {
                bat './gradlew.bat test -x build -x :acceptance-test:test -x :ui:test --stacktrace'

                junit '**/test-results/**/*.xml'
            }
        }

        stage('Acceptance Test') {
            steps {
                bat './gradlew.bat :acceptance-test:test --stacktrace'

                junit '**/test-results/**/*.xml'
            }
        }

        stage('Build UI') {
            steps {
                bat './gradlew.bat :ui:build --stacktrace'
            }
        }

        stage('Docker') {
            steps {
                bat './gradlew.bat docker -x build --stacktrace'
            }
        }
    }
}
