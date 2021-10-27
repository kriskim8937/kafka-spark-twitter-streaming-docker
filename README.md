# kafka_metamorphosis
Twitter analysis project with kafka, hadoop, kubernetes.
This repository contains a docker-compose stack with Kafka and Spark Streaming.
Please Refer to
https://www.notion.so/1e4b7c21dd0a4d0fb5d8f7bbb314991d

## Running Docker Compose

To run docker compose simply run the following command in the current folder:

```
docker-compose up -d
```

This will run deattached. If you want to see the logs, you can run:

```
docker-compose logs -f -t --tail=10
```

To see the memory and CPU usage (which comes in handy to ensure docker has enough memory) use:

```
docker stats
```
