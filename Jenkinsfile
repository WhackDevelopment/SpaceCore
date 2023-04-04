pipeline {
    agent any

    tools {
        jdk 'jdk-17'
    }

    stages {
        stage('Build') { 
            steps { 
               sh 'mvn -Dmaven.test.failure.ignore=true clean package' 
            }
            post {
                success {
                    archiveArtifacts artifacts: 'out/*.jar', fingerprint: true
                }
            }
        }
    }    
}
