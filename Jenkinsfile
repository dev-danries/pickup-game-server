pipeline {
    agent any
    triggers {
        pollSCM('H/5 * * * *')
    }
    tools {
        jdk 'JDK_15'
        maven "MAVEN_3.6"
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '1'))
    }
    stages {
        stage('build') {
            steps {
                script {
                    slackSend color: "good", message: "Starting Build"
                    echo 'Building...'
                    if (isUnix()) {
                        sh 'mvn clean package'
                    } else {
                        bat 'mvn clean package'
                    }
                }
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
            }
        }
        stage('Deploy') {
            when {
                branch 'master'
            }
            steps {
                script {
                    echo 'Deploying'
                }
            }
        }
    }
}