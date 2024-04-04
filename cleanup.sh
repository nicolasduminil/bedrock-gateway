aws cloudformation delete-stack --stack-name rest-gateway-stack
if [ -f bucket-name.txt ]
then
  ARTIFACT_BUCKET=$(cat bucket-name.txt)
  aws s3 rb --force s3://$ARTIFACT_BUCKET
fi
aws logs describe-log-groups --query 'logGroups[*].logGroupName' --output table | \
awk '{print $2}' | grep ^/aws/lambda | while read x; do  echo "deleting $x" ; aws logs delete-log-group --log-group-name $x; done
