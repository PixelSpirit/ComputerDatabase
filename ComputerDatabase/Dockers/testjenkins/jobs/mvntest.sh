
rm -Rf /home/cdb

git clone https://github.com/PixelSpirit/ComputerDatabase.git /home/cdb

docker exec maven rm -Rf /home/cdb

docker cp /home/cdb maven:/home

docker exec maven mvn -f /home/cdb/ComputerDatabase test

