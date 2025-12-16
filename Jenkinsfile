pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('sonar-token') // Ton token SonarQube
        IMAGE_NAME = "tebibyasmine/devops:latest" // Mets ici le nom exact de ton repo Docker Hub
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
                SONAR_HOST_URL = 'http://localhost:9000'
            }
            steps {
                withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=devopss \
                        -Dsonar.projectName=devopss \
                        -Dsonar.host.url=$SONAR_HOST_URL \
                        -Dsonar.login=$SONAR_TOKEN
                    """
                }
            }
        }

        stage('Docker Build') {
            steps {
                // Build l'image Docker
                sh "docker build -t $IMAGE_NAME ."
            }
        }

        stage('Docker Push') {
            steps {
                // Authentification Docker Hub et push
                withCredentials([usernamePassword(credentialsId: 'docker-hub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh "echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin"
                    sh "docker push $IMAGE_NAME"
                }
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
