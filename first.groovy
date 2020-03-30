pipeline{
  agent any
  environment {
    RELEASENAME="Docker-image"
  }
  stages{
    stage("Pull Bitbucket Code"){
        steps {checkout scm}
    }
stage('Docker Image Build') {
        steps([$class: 'BapSshPromotionPublisherPlugin']) {
            sshPublisher(
                continueOnError: false, failOnError: true,
                publishers: [
                    sshPublisherDesc(
                        configName: "docker-build",
                        verbose: true,
                        transfers: [
                            sshTransfer(execCommand: "rm -rf  tomcat-1"),
                            sshTransfer(execCommand: "git clone -b master https://github.com/talhajilal/tomcat-1.git tomcat-1"),
                      //      sshTransfer(sourceFiles: "docker images",)
                            sshTransfer(execCommand: "docker build -t tomcat:1 tomcat-1/."),
     //                       sshTransfer(execCommand: "docker tag  $(docker images | grep tomcat| awk '{print $3}') localhost:5000/tomcat:1"),
     //                       sshTransfer(execCommand: "docker push localhost:5000/tomcat:$build"),
                            //sshTransfer(execCommand: "docker rmi -f $(docker images | grep tomcat | awk '{print $3}')")
                        ]
                    )
                ]
            )
        }
    }
 
  }
}