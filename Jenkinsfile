pipeline {
    agent {
        label "android"
    }
    stages {
        stage('Test') {
             steps {
                sh "./gradlew --info clean lintDevDebug checkDevDebug testDevDebug"
             }
        }
    }
}
