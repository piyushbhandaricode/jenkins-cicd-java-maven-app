def gv

pipeline {
    agent any
    environment {
        CLOUDSDK_CORE_PROJECT='playground-s-11-62963bf6'
        CLIENT_EMAIL='112268103611-compute@developer.gserviceaccount.com'
    }
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
                    gv.deployApptoGCP()
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

    post {
      always {
          sh 'gcloud auth revoke $CLIENT_EMAIL'
        }
    }   
}