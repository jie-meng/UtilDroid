pipeline {
    agent any
    stages {
        stage('Test') {
             steps {
                 sh "./gradlew clean lintDevDebug checkDevDebug testDevDebug"
                 archiveArtifacts artifacts: '**/reports/**', fingerprint: true
             }
        }
    }
}
