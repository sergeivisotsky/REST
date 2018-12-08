pipeline {

    agent any
    tools {
        maven 'maven_3_6_0'
    }

    stages {

        stage('Code analise') {
            steps {
                echo '-=- analyzing code -=-'
                sh 'mvn sonar:sonar ' +
                        '-Dsonar.host.url=http://79.135.149.36:9000 ' +
                        '-Dsonar.login=cd75a80bd9903c713d937241dd880a1056e5b925'
            }
        }

        stage('Compilation') {
            steps {
                echo '-=- compiling project -=-'
                sh 'mvn clean compile'
            }
        }
        stage('Testing') {
            steps {
                echo '-=- execute unit tests -=-'
                sh 'mvn test'
            }
        }
        stage('Installation') {
            steps {
                echo '-=- installing project -=-'
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('Packaging') {
            steps {
                echo '-=- packaging project -=-'
                sh 'mvn package -DskipTests'
            }
        }
    }
}