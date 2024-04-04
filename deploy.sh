#!/bin/bash
RANDOM=$$
BUCKET_NAME=bucketname-$RANDOM
echo $BUCKET_NAME > bucket-name.txt
aws s3 mb s3://$BUCKET_NAME
sam deploy -t rest-gateway-api/target/sam.jvm.yaml --s3-bucket $BUCKET_NAME --stack-name rest-gateway-stack --capabilities CAPABILITY_IAM