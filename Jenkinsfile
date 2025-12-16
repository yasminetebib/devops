pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'       // Ton JDK installé dans Jenkins
        maven 'Maven3'        // Ton Maven installé dans Jenkins
    }

    environment {
        DOCKER_IMAGE = "yasminetebib/devops:latest"  // Nom et tag de ton image Docker
        SONAR_HOST_URL = "http://127.0.0.1:9000"    // URL de ton SonarQube
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
                sh """
                    mvn sonar:sonar \
                        -Dsonar.projectKey=devops \
                        -Dsonar.projectName=devops \
                        -Dsonar.host.url=${SONAR_HOST_URL}
                """
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
