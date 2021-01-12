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
                    slackSend color: "good", message: "Build Started - ${env.JOB_NAME} (<${env.BUILD_URL}|Open>)"
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
            if (env.BRANCH_NAME == 'develop') {
                script {
                    def message = "${env.JOB_NAME}-${env.BUILD_NUMBER} Dev Deployment"
                    sh 'heroku git:remote -a pickup-backend-develop'
                    sh 'git add .'
                    sh "git commit -am ${message}"
                }
            } else if (env.BRANCH_NAME == 'master') {

            }
        }
    }
    post {
        success {
            slackSend color: "good", message: "Job Success - ${env.JOB_NAME} (<${env.BUILD_URL}|Open>)"
        }
        failure {
            slackSend color: "danger", message: "Job Failure - ${env.JOB_NAME} (<${env.BUILD_URL}|Open>)"
        }
    }
}