pipeline {
    agent any

    tools {
        // Déclare les outils installés sur Jenkins
        jdk 'JAVA_HOME'       // Ton JDK installé dans Jenkins
        maven 'Maven3'        // Ton Maven installé dans Jenkins
    }

    environment {
        DOCKER_IMAGE = "yasminetebib/devops:latest"  // Nom et tag de ton image Docker
        SONAR_HOST_URL = "http://192.168.33.10:9000" // IP de ta VM SonarQube
    }

    stages {

        stage('SCM Checkout') {
            steps {
                // Récupérer le code depuis Git
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
                sh "docker build -t ${DOCKER_IMAGE} ."
            }
        }

        stage('Docker Push') {
            steps {
                // Connecte-toi à ton registre Docker si nécessaire
                sh "docker push ${DOCKER_IMAGE}"
            }
        }
    }

    post {
        success {
            echo 'Pipeline terminé avec succès ! '
        }
        failure {
            echo 'Pipeline échoué '
        }
    }
}

