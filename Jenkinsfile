pipeline {
    agent any

    tools {
        maven 'Maven 3.9.6' // Ensure this name matches your Jenkins Global Tool Configuration
        jdk 'JDK 21'        // Ensure this name matches your Jenkins Global Tool Configuration
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                // Use 'bat' for Windows or 'sh' for Linux/macOS
                bat 'mvn -B clean test'
            }
        }
    }

    post {
        always {
            // Archive JUnit test results
            junit '**/target/surefire-reports/*.xml'
        }
        failure {
            // Archive screenshots if the build fails
            archiveArtifacts artifacts: 'target/screenshots/**', allowEmptyArchive: true
        }
    }
}
