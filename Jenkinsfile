pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'Maven3'
    }

    environment {
        DOCKER_IMAGE = "yasminetebib/devops"
        SONAR_HOST_URL = "http://192.168.33.10:9000"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh """
                    mvn sonar:sonar \
                    -Dsonar.projectKey=devops \
                    -Dsonar.projectName=devops \
                    -Dsonar.host.url=${SONAR_HOST_URL}
                    """
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE .'
            }
        }
    }
}
