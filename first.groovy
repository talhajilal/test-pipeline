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
}