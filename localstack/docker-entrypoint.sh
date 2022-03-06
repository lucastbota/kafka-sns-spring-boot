## Create sns
aws --endpoint-url=http://localhost:4566 sns create-topic --name persons-sns-topic
aws --endpoint-url=http://localhost:4566 sns list-subscriptions
## Create sqs
aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name persons-queue
aws --endpoint-url=http://localhost:4566 sqs list-queues

## Subscribing
aws --endpoint-url=http://localhost:4566 sns subscribe --topic-arn arn:aws:sns:sa-east-1:000000000000:persons-sns-topic --protocol sqs --notification-endpoint http://localhost:4566/000000000000/persons-queue

aws --endpoint-url=http://localhost:4566 sns list-subscriptions