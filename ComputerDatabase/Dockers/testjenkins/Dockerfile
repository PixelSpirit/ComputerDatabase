FROM jenkins:1.596
 
USER root

RUN apt-get update && apt-get install -y curl
RUN curl -fsSL https://get.docker.com/ | sh

COPY jobs /home/jobs

ENV DOCKER_HOST=tcp://testdind:2375


