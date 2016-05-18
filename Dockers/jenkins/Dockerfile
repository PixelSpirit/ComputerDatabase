FROM jenkins:1.596
 
USER root

RUN apt-get update && apt-get install -y curl
RUN curl -fsSL https://get.docker.com/ | sh

COPY jobs/deploy.sh /home/jobs/deploy.sh
COPY jobs/mvntest.sh /home/jobs/mvntest.sh

COPY properties/main.properties /home/properties/main.properties
COPY properties/test.properties /home/properties/test.properties

