// Scripted Pipeline
node ('') {
    stage('dev') {
  // get code from our Git repository
  sh "echo 'Hello World'"
}
}
node {
    stage('SSH transfer') {
         steps([$class: 'BapSshPromotionPublisherPlugin']) {
             sshPublisher(
                 continueOnError: false, failOnError: true,
                     publishers: [
                    sshPublisherDesc(
                        configName: "docker-build",
                        verbose: true,
                        transfers: [
                            sshTransfer(execCommand: "docker ps -a"),
                            sshTransfer(sourceFiles: "docker images",)
                        ]
                    )
                     ]
         }
    }
}