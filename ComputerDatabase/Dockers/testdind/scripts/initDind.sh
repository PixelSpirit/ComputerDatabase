#!/bin/bash

docker network create --subnet=172.18.0.0/16 network2
docker run -d --name=mysql --ip 172.18.0.2 --net network2 pixelfeather/mysql
docker run -itd --name=maven --ip 172.18.0.3 --net network2 pixelfeather/maven /bin/bash

