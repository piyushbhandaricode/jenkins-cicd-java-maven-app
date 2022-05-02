pipeline {
    agent any
    stages {
        stage("init") {
            steps {
                script {
                    echo "fetching code from gitlab"
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    echo "building jar"

                }
            }
        }
        stage("build image") {
            steps {
                script {
                    echo "building image"

                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    echo "deploying"
                }
            }
        }
    }   
}