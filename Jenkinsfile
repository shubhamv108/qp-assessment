pipeline {
	agent none
	stages {
  	stage('Maven Install') {
    	agent {
      	docker {
        	image 'maven:3.5.0'
        }
      }
      steps {
      	sh 'mvn clean install'
      }
    }
    stage('Docker Build') {
        agent any
      steps {
        sh 'docker build -t ${env.DOCKER_USER}/grocery-booking:latest .'
      }
    }
    stage('Docker Build') {
    	agent any
      steps {
      	sh 'docker build -t ${env.DOCKER_USER}/grocery-booking-fluentbit:latest .'
      }
    }
    stage('Image Scan') {
        steps {
            prismaCloudScanImage ca: '',
            cert: '',
            dockerAddress: 'unix:///var/run/docker.sock',
            image: 'shubham01/grocery-booking:latest',
            key: '',
            logLevel: 'info',
            podmanPath: '',
            // The project field below is only applicable if you are using Prisma Cloud Compute Edition and have set up projects (multiple consoles) on Prisma Cloud.
            project: '',
            resultsFile: 'prisma-cloud-scan-results.json',
            ignoreImageBuildTime:true
        }
    }
    stage('Image Scan - fluentbit') {
        steps {
            prismaCloudScanImage ca: '',
            cert: '',
            dockerAddress: 'unix:///var/run/docker.sock',
            image: 'shubham01/grocery-booking-fluentbit:latest',
            key: '',
            logLevel: 'info',
            podmanPath: '',
            // The project field below is only applicable if you are using Prisma Cloud Compute Edition and have set up projects (multiple consoles) on Prisma Cloud.
            project: '',
            resultsFile: 'prisma-cloud-scan-results.json',
            ignoreImageBuildTime:true
        }
    }
    post {
        always {
           prismaCloudPublish resultsFilePattern: 'prisma-cloud-scan-results.json'
        }
    }
    stage('Docker Push') {
      agent any
      steps {
      	withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USER')]) {
        	sh "docker login -u ${env.DOCKER_USER} -p ${env.DOCKER_PASSWORD}"
          sh 'docker push ${env.DOCKER_USER}/grocery-booking:latest'
        }
      }
    }
    stage('Docker Push - Fluentbit') {
      agent any
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USER')]) {
            sh "docker login -u ${env.DOCKER_USER} -p ${env.DOCKER_PASSWORD}"
          sh 'docker push ${env.DOCKER_USER}/grocery-booking-fluentbit:latest'
        }
      }
    }
  }
}