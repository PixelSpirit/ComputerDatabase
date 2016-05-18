#!/bin/bash

# Build dockers
docker build -t jenkins jenkins
docker build -t maven maven
docker build -t mysql mysql


# Network configuration
docker network create testnetwork
docker network create prodnetwork

# Run jenkins
#docker run --privileged --net network1 --ip 172.19.0.2 --name=testdind -d testdind
docker run -d --name=jenkins \
	   -v /var/run/docker.sock:/var/run/docker.sock \
	   -v /var/jenkins_home:/var/jenkins_home \
	   -p 8082:8080 jenkins

# Test config
docker run -d --name=testmysql pixelfeather/mysql
docker run -itd --name=testmaven pixelfeather/maven /bin/bash
docker network connect testnetwork testmysql
docker network connect testnetwork testmaven

# Tomcat config
docker run -d --name=prodmysql pixelfeather/mysql
docker run -d --name=prodtomcat -p 8081:8080 tomcat:8.0-jre8
docker network connect prodnetwork prodmysql
#docker network connect prodnetwork prodtomcat
docker network connect testnetwork prodtomcat


