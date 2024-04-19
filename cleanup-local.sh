SAM=$(ps -wwo pid,args | grep "sam local start-api" | grep -v grep | awk '{print $1}')
if [[ ! -z "$SAM" ]]
then
  kill $SAM
fi