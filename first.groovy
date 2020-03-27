pipeline{
  agent any
  environment {
    RELEASENAME="yourProject-ci"
  }
  stages{
    stage("Get the charts..."){
        steps {checkout scm}
    }
  }

stage('SSH transfer') {
        steps([$class: 'BapSshPromotionPublisherPlugin']) {
            sshPublisher(
                continueOnError: false, failOnError: true,
                publishers: [
                    sshPublisherDesc(
                        configName: "docker-build",
                        verbose: false,
                        transfers: [
                            sshTransfer(execCommand: "docker ps -a"),
                            sshTransfer(sourceFiles: "docker images",)
                        ]
                    )
                ]
            )
        }
    }
 
  }