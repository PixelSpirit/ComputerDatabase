
rm -Rf /home/cdb

git clone https://github.com/PixelSpirit/ComputerDatabase.git /home/cdb
cp /home/properties/main.properties /home/cdb/ComputerDatabase/src/main/resources/db.properties
cp /home/properties/test.properties /home/cdb/ComputerDatabase/src/test/resources/db.properties

docker exec testmaven rm -Rf /home/cdb
docker cp /home/cdb testmaven:/home

docker exec testmaven mvn -f /home/cdb/ComputerDatabase test

