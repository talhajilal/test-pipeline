pipeline{
  agent any
  environment {
    RELEASENAME="yourProject-ci"
  }
  stages{
    stage("Get the charts..."){
        steps {checkout scm}
    }
stage('SSH transfer') {
        steps([$class: 'BapSshPromotionPublisherPlugin']) {
            sshPublisher(
                continueOnError: false, failOnError: true,
                publishers: [
                    sshPublisherDesc(
                        configName: "docker-build",
                        verbose: true,
                        transfers: [
                            sshTransfer(execCommand: "git clone -b master https://github.com/talhajilal/tomcat-1.git tomcat-1"),
                      //      sshTransfer(sourceFiles: "docker images",)
                            sshTransfer(execCommand: "docker build -t tomcat:1 tomcat-1/."),
     //                       sshTransfer(execCommand: "docker tag  $(docker images | grep tomcat| awk '{print $3}') localhost:5000/tomcat:1"),
     //                       sshTransfer(execCommand: "docker push localhost:5000/tomcat:1"),
                            //sshTransfer(execCommand: "docker rmi -f $(docker images | grep tomcat | awk '{print $3}')")
                        ]
                    )
                ]
            )
        }
    }
 
  }
}