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

def buildImage() {
    echo "building the docker image..."

    withCredentials([usernamePassword(credentialsId: 'jenkins-dockerhub', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "docker build -t piyushbhandari/demo-app:${IMAGE_NAME} ."
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "docker push piyushbhandari/demo-app:${IMAGE_NAME}"
    }
} 

def deployApp() {
    echo 'deploying the application...'

} 

return this