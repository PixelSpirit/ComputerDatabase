docker exec testmaven mvn -f /home/cdb/ComputerDatabase clean install
docker cp testmaven:/home/cdb/ComputerDatabase/target/cdb-0.3.0-SNAPSHOT.war /home
docker cp /home/cdb-0.3.0-SNAPSHOT.war prodtomcat:/usr/local/tomcat/webapps/cdb.war
