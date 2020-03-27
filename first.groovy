// Scripted Pipeline
node ('') {
    stage('dev') {
  // get code from our Git repository
  sh "echo 'Hello World'"
}
}
node {
  sh "ssh root@10.0.2.15 'docker images'"
}
node {
    git 'https://github.com/talhajilal/jenkins.git'
}

node {
  sh  "echo 'docker ps'"
}
// Scripted Pipeline
node ('') {
    stage('prod') {
  // get code from our Git repository
  sh "echo 'Hello World'"
}
}
//node {
  //sh "ssh root@10.0.2.15 'docker images'"
//}
node {
    git 'https://github.com/talhajilal/jenkins.git'
}
    stage('SSH transfer') {
        steps([$class: 'BapSshPromotionPublisherPlugin']) {
            sshPublisher(
                continueOnError: false, failOnError: true,
                publishers: [
                    sshPublisherDesc(
                        configName: "kubernetes_master",
                        verbose: true,
                        transfers: [
                            sshTransfer(execCommand: "docker ps -a"),
                            sshTransfer(sourceFiles: "docker images",)
                        ]
                    )
                ]
            )
        }
    }
 