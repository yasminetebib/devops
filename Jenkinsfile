pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('sonar-token') // Ton token SonarQube
        IMAGE_NAME = "yasminetebib/devops:latest"
    }

    stages {
        stage('Checkout SCM') {
            steps {
                git branch: 'main', url: 'https://github.com/yasminetebib/devops.git'
            }
        }

        stage('Maven Build') {
            steps {
                // Compile et package le projet en ignorant les tests
                sh 'mvn clean package install -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            environment {
                // On utilise le token Sonar
                SONAR_HOST_URL = 'http://localhost:9000'
            }
            steps {
                withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                    sh "mvn sonar:sonar -Dsonar.projectKey=devopss -Dsonar.projectName=devopss -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=$SONAR_TOKEN"
                }
            }
        }

        stage('Docker Build') {
            steps {
                // On build avec l'image déjà existante
                sh "docker build -t $IMAGE_NAME ."
            }
        }

        stage('Docker Push') {
            steps {
                // Push sur Docker Hub
                sh "docker push $IMAGE_NAME"
            }
        }
    }

    post {
        success {
            echo "Pipeline réussie ✅"
        }
        failure {
            echo "Pipeline échouée ❌"
        }
    }
}
