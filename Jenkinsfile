def gv

pipeline {
    agent any
    stages {
        stage("Initialize") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("Build Artifact") {
            steps {
                script {
                    echo "building jar"
                    gv.buildJar()
                }
            }
        }
        stage("Build Image") {
            steps {
                script {
                    echo "building image"
                    gv.buildImage()
                }
            }
        }
        stage("Deploy") {
            steps {
                script {
                    echo "deploying"
                    gv.deployApp()
                }
            }
        }
    }   
}