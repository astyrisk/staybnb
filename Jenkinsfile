// Jenkinsfile
pipeline {
    agent any

    parameters {
        string(
            name: 'TEST_BASE_URL',
            defaultValue: 'https://qa-playground.nixdev.co/t/automation-adel',
            description: 'Base URL for tests'
        )
    }

    stages {
        stage('Run Tests') {
            // Spin up a container that has Java 21, Maven, and Chrome pre-installed
            agent {
                docker {
                    image 'markhobson/maven-chrome:jdk-21'
                    reuseNode true
                }
            }
            steps {
                withCredentials([
                    string(credentialsId: 'staybnb-test-user', variable: 'TEST_USER'),
                    string(credentialsId: 'staybnb-test-password', variable: 'TEST_PASSWORD')
                ]) {
                    sh """
                        mvn clean test \\
                          -Dheadless=true \\
                          -DTEST_BASE_URL="${params.TEST_BASE_URL}" \\
                          -DTEST_USER="\$TEST_USER" \\
                          -DTEST_PASSWORD="\$TEST_PASSWORD"
                    """
                }
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}