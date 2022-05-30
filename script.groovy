def buildJar() {
    echo "building the application..."
    sh 'mvn package'
    sh 'mvn clean package'
} 

def incrementMavenAppVersion() {
    echo "incrementing the maven app version..."
    sh 'mvn build-helper:parse-version versions:set \
     -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.nextMinorVersion}.0 \
     versions:commit'
    
    def matchedResultArray = readFile('pom.xml') =~ '<version>(.+)</version>'
    def version = matchedResultArray[0][1]
    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
}

def buildDockerImage() {
    echo "building the docker image..."

    withCredentials([usernamePassword(credentialsId: 'jenkins-dockerhub', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "docker build -t piyushbhandari/demo-app:${IMAGE_NAME} ."
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "docker push piyushbhandari/demo-app:${IMAGE_NAME}"
    }
} 

def deployApptoGCP() {
    echo 'deploying the application...'
    withCredentials([file(credentialsId: 'gcloud-creds', variable: 'GCLOUD_CREDS')]) {
          sh '''
            gcloud version
            gcloud auth activate-service-account --key-file="$GCLOUD_CREDS"
            gcloud compute ssh gce-ubuntu --zone=us-central1-a

            docker run -p 3080:3080 -d piyushbhandari/demo-app:3.4.0-11
            '''
    }

}

def commitVersionUpdate() {
    withCredentials([usernamePassword(credentialsId: 'gitlab-jenkins', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        // Only for first time or set them once in the container
        sh 'git config --global user.email "jenkins@example.com"'
        sh 'git config --global user.name "Jenkins"'

        
        sh 'git status'
        sh 'git branch'
        sh 'git config --list'
        
        sh "git remote set-url origin https://${USER}:${PASS}@gitlab.com/piyush.bhandari/java-maven-app.git"
        sh 'git add .'
        sh 'git commit -m "ci: version bump"'
        sh 'git push origin HEAD:jenkins-dockerhub'
    }
    
}

return this