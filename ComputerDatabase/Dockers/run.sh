#!/bin/bash

# Build dockers
docker build -t testjenkins testjenkins
docker build -t testdind testdind

# Network configuration
docker network create --subnet=172.19.0.0/16 network1 

# Run dockers
docker run --privileged --net network1 --ip 172.19.0.2 --name=testdind -d testdind
docker run -d --name=testjenkins --net network1 --ip 172.19.0.3 \
	   -v  ~/workspace/ComputerDatabase/ComputerDatabase/Dockers/testjenkins/jenkins_home:/var/jenkins_home \
	   -p 8080:8080 testjenkins

# Run initialisations scripts
docker exec testdind sh /home/scripts/initDind.sh
