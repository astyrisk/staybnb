// Jenkinsfile - NO DEFAULT VALUES AT ALL
pipeline {
    agent any
    
    parameters {
        string(
            name: 'TEST_BASE_URL',
            description: 'Base URL for tests (e.g., https://qa.example.com)'
        )
    }
    
    stages {
        stage('Run Tests') {
            steps {
                withCredentials([
                    string(credentialsId: 'staybnb-test-user', variable: 'TEST_USER'),
                    string(credentialsId: 'staybnb-test-password', variable: 'TEST_PASSWORD')
                ]) {
                    // Use 'bat' for Windows or 'sh' for Linux/macOS
                    // Since the current Jenkinsfile uses 'bat', I'll use 'bat' here too.
                    sh """
                        mvn clean test ^
                          -DTEST_BASE_URL=${params.TEST_BASE_URL} ^
                          -DTEST_USER=%TEST_USER% ^
                          -DTEST_PASSWORD=%TEST_PASSWORD%
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
