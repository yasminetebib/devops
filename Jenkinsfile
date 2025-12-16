pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('sonar-token')        // Ton token SonarQube
        IMAGE_NAME = "tebibyasmine/devops:latest"       // Nom de l'image Docker
        SONAR_HOST_URL = 'http://localhost:9000'        // URL de ton serveur SonarQube
    }

    stages {
        stage('Checkout SCM') {
            steps {
                echo "🔄 Clonage du dépôt Git..."
                git branch: 'main', url: 'https://github.com/yasminetebib/devops.git'
            }
        }

        stage('Maven Build') {
            steps {
                echo "📦 Build Maven..."
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo "🔍 Analyse SonarQube..."
                withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                    sh '''
                        mvn sonar:sonar \
                        -Dsonar.projectKey=devopss \
                        -Dsonar.projectName=devopss \
                        -Dsonar.host.url=$SONAR_HOST_URL \
                        -Dsonar.login=$SONAR_TOKEN
                    '''
                }
            }
        }

        stage('Docker Build') {
            steps {
                echo "🐳 Construction de l'image Docker..."
                sh "docker build -t $IMAGE_NAME ."
            }
        }

        stage('Docker Push') {
            steps {
                echo "📤 Push sur Docker Hub..."
                withCredentials([usernamePassword(credentialsId: 'docker-hub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        docker push $IMAGE_NAME
                    '''
                }
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline réussie !"
        }
        failure {
            echo "❌ Pipeline échouée !"
        }
    }
}
