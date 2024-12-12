#!/bin/bash

# Ensure script stops on errors
set -e

NETWORK_NAME="prom_net"
APP_CONTAINER_NAME="taskv2"
REDIS_CONTAINER_NAME="my_redis"
DOCKERFILE_PATH="."  # Adjust if Dockerfile is not in the current directory
APP_PORT=8001        # Replace with your app's exposed port if different

echo "Starting OpenTelemetry setup with app and Redis..."

# Create a Docker network if it doesn't already exist
if ! docker network ls | grep -q "$NETWORK_NAME"; then
  echo "Creating Docker network: $NETWORK_NAME..."
  docker network create "$NETWORK_NAME"
else
  echo "Docker network $NETWORK_NAME already exists."
fi

# Run Jaeger
echo "Starting Jaeger..."

docker run -d --name jaeger --network "$NETWORK_NAME" \
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
docker run -d --name otel-collector --network "$NETWORK_NAME" \
  -p 4317:4317 \
  -p 55681:55681 \
  otel/opentelemetry-collector:0.85.0

# Run Redis
echo "Starting Redis container..."
docker run -d --name "$REDIS_CONTAINER_NAME" --network "$NETWORK_NAME" redis:latest

# Build and run the application container
echo "Building and running the application container..."
docker build -t "$APP_CONTAINER_NAME" "$DOCKERFILE_PATH"
docker run -d --name "$APP_CONTAINER_NAME" --network "$NETWORK_NAME" -p "$APP_PORT:$APP_PORT" "$APP_CONTAINER_NAME"

# Wait for containers to start
echo "Waiting for containers to initialize..."
sleep 5

# Verify containers
echo "Verifying running containers..."
docker ps --filter "name=jaeger" --filter "name=otel-collector" --filter "name=$REDIS_CONTAINER_NAME" --filter "name=$APP_CONTAINER_NAME"

# Display container URLs and network information
echo -e "\nSetup complete. Access details:"
echo "- Jaeger UI: http://localhost:16686"
echo "- App: http://localhost:$APP_PORT"
echo "- Redis is running inside Docker network: $NETWORK_NAME"
echo "- OpenTelemetry Collector is listening on port 4317 (gRPC)\n"