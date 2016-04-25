#!/bin/bash

function test_status {
  if [ $? = 1 ]; then
    echo $'\n$1'
    exit 1 
  else 
    echo $'\nCheck Ok !!!'
  fi
}

echo "***********************"
echo "CheckStyle Validation :"
echo $'***********************\n'

mvn checkstyle:check
test_status "Invalid CheckStyle !!!"

echo "*****************"
echo "Tests validation:"
echo $'*****************\n'

mvn test
test_status "Tests Failed !!!"

git commit $*

