#!/bin/bash
# Assumes the services have been built with the named images as per the README and put on prom_net

# Create the network
if docker network inspect prom_net >/dev/null 2>&1; then
  echo "Network prom_net exists."
else
  docker network create prom_net
  echo "Network prom_net created."
fi

# Start redis
if docker ps -a | grep -q my_redis; then
  echo "Container my_redis exists."
else
  docker run -d --name my_redis --network prom_net redis
  echo "Container my_redis created."
fi

# Run Jaeger
echo "Starting Jaeger..."

docker run -d --name jaeger --network prom_net \
  -e COLLECTOR_ZIPKIN_HTTP_PORT=9411 \
  -p 5775:5775/udp \
  -p 6831:6831/udp \
  -p 6832:6832/udp \
  -p 5778:5778 \
  -p 16686:16686 \
  -p 14268:14268 \
  -p 14250:14250 \
  -p 9411:9411 \
  jaegertracing/all-in-one:1.28

# Run OpenTelemetry Collector
echo "Starting OpenTelemetry Collector..."
docker run -d --name otel-collector --network prom_net \
  -p 4317:4317 \
  -p 55681:55681 \
  otel/opentelemetry-collector:0.85.0

# Loki
docker run -d --name loki --network prom_net -p 3100:3100 -v $(pwd)/loki-config.yml:/etc/loki/loki.yml grafana/loki:3.0.0 -config.file=/etc/loki/loki.yml

docker run -d --name alertmanager --network prom_net -p 9093:9093 -v $(pwd)/alertmanager.yml:/etc/alertmanager/alertmanager.yml prom/alertmanager

# Grocery services - these do not share a backend DB though so these will be distinct
docker run -d --name groceryV1 -p 8081:8080 --network prom_net grocery_v1
docker run -d --name groceryV2 -p 8082:8080 --network prom_net grocery_v2

# Tasks manager. This shares redis so these will show the same lists just different REST request handling
docker run -d --name taskV1 --network prom_net -p 8051:8050 task_v1
docker run -d --name taskV2 --network prom_net -p 8052:8050 --env-file tasks-manager-v2/.env task_v2

# Booktown GraphQL, no persistent store
docker run -d --name booktown -p 8080:8080 --network prom_net booktown

# encrypt and decrypt services
docker run -d --name encrypt --network prom_net -p 8087:8081 encrypt_amex
docker run -d --name decrypt --network prom_net -p 8086:8082 decrypt_amex

# Prometheus
docker run -p 9090:9090 -v $(pwd)/prometheus_all.yml:/etc/prometheus/prometheus.yml -v $(pwd)/alert_rules.yml:/etc/prometheus/alert_rules.yml --name prom --network prom_net -d prom/prometheus

# Grafana
docker run -d --name grafana --network prom_net -p 3110:3000 grafana/grafana

docker ps -a

echo "To stop and remove all containers do: docker rm -f my_redis booktown taskV1 taskV2 groceryV1 groceryV2 prom encrypt decrypt loki grafana alertmanager"
