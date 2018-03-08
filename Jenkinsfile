pipeline {
    agent {
        label "android"
    }
    stages {
        stage('Test') {
             steps {
                 sh "./gradlew clean lintDevDebug checkDevDebug testDevDebug"
                 archiveArtifacts artifacts: '**/reports/**', fingerprint: true
             }
        }
    }
}