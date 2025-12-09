pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'Maven3'  // <-- mettre exactement le nom configuré dans Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/yasminetebib/devops.git'
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }
    }
}
