#!/bin/bash
mvn -Durl=http://localhost:3000 clean install
sed -i 's/java11/java17/g' bedrock-gateway-api/target/sam.jvm.yaml
sam local start-api -t ./bedrock-gateway-api/target/sam.jvm.yaml --log-file ./bedrock-gateway-api/sam.log &
mvn -DskipTests=false failsafe:integration-test
docker run --name bedrock -p 8082:8082 --rm --network host nicolasduminil/bedrock-gateway-web:1.0-SNAPSHOT
./cleanup-local.sh
