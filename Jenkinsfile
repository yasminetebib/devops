pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'Maven3'
    }

    environment {
        DOCKER_IMAGE = "yasminetebib/devops:latest"
        SONAR_HOST_URL = "http://127.0.0.1:9000"
    }

    stages {

        stage('SCM Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/yasminetebib/devops.git'
            }
        }

        stage('Build: Clean & Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Package & Install') {
            steps {
                sh 'mvn package install -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Utilisation du token stocké dans Jenkins
                withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                    sh """
                        mvn sonar:sonar \
                            -Dsonar.projectKey=devops \
                            -Dsonar.projectName=devops \
                            -Dsonar.host.url=${SONAR_HOST_URL} \
                            -Dsonar.login=$SONAR_TOKEN
                    """
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE} ."
            }
        }

        stage('Docker Push') {
            steps {
                sh "docker push ${DOCKER_IMAGE}"
            }
        }
    }

    post {
        success {
            echo 'Pipeline terminé avec succès !'
        }
        failure {
            echo 'Pipeline échoué'
        }
    }
}
