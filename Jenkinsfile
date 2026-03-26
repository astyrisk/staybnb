
// Jenkinsfile
pipeline {
    agent any

    // Tell Jenkins to load the Maven tool we just configured
    tools {
        maven 'maven3'
    }

    parameters {
        string(
            name: 'TEST_BASE_URL',
            defaultValue: 'https://qa-playground.nixdev.co/t/automation-adel',
            description: 'Base URL for tests'
        )
    }

    stages {
        stage('Run Tests') {
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
