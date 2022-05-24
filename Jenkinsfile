def gv

pipeline {
    agent any
    tools {
        maven 'maven-3.8.5'
    }

    stages {
        stage("Initialize") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        
        stage("Increment Version") {
            steps {
                script {
                    gv.incrementMavenAppVersion()
                }
            }
        }

        stage("Build Artifact") {
            steps {
                script {
                    gv.buildJar()
                }
            }
        }
        stage("Build Image") {
            steps {
                script {
                    gv.buildDockerImage()
                }
            }
        }
        stage("Deploy") {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }

        stage("Commit Version Update") {
            steps {
                script {
                    gv.commitVersionUpdate()
                }
            }
        }


    }   
}