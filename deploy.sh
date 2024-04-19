#!/bin/bash
mvn -pl bedrock-gateway-api -am clean install
sed -i 's/java11/java17/g' bedrock-gateway-api/target/sam.jvm.yaml
RANDOM=$$
BUCKET_NAME=bedrock-gateway-bucket-$RANDOM
STACK_NAME=bedrock-gateway-stack
echo $BUCKET_NAME > bucket-name.txt
aws s3 mb s3://$BUCKET_NAME
#sam deploy -t bedrock-gateway-api/target/sam.jvm.yaml --s3-bucket $BUCKET_NAME --stack-name  $STACK_NAME --capabilities CAPABILITY_IAM
sam deploy -t bedrock-gateway-api/src/main/resources/template.yaml --s3-bucket $BUCKET_NAME --stack-name  $STACK_NAME --capabilities CAPABILITY_IAM
API_ENDPOINT=$(aws cloudformation describe-stacks --stack-name $STACK_NAME --query 'Stacks[0].Outputs[0].OutputValue' --output text)
mvn -pl bedrock-gateway-web -Durl=$API_ENDPOINT clean install
#mvn -DskipTests=false failsafe:integration-test
docker run --name bedrock -p 8082:8082 --rm --network host nicolasduminil/bedrock-gateway-web:1.0-SNAPSHOT



