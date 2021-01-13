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
    environment {
        jk_aws_id = '<JENKINS_AWS_CREDENTIAL_ID>'
        GIT_BRANCH_NAME = "${sh(script: 'echo ${GIT_BRANCH##*/}', returnStdout: true).trim()}"
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
            environment {
                gcs = "${sh(script: 'echo -n ${GIT_BRANCH_NAME,,}-${GIT_COMMIT:0:8}', returnStdout: true).trim()}"
            }
            when {
                anyOf { branch 'develop'; branch 'master' }
            }
            steps {
                echo 'Deploying...'
                step([$class: 'AWSEBDeploymentBuilder', credentialId: "${jk_aws_id}",
                      awsRegion: 'us-east-1', applicationName: 'pickup-backend',
                      environmentName: 'PickupBackend-env', rootObject: './bin/pickup-backend.zip',
                      bucketName: 'elasticbeanstalk-us-east-1-858338784463',
                      versionLabelFormat: "$gcs", versionDescriptionFormat: "$gcs",
                      sleepTime: '10', checkHealth: 'true', maxAttempts: '12'])
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