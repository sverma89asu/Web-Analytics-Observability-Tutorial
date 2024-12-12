# Run the flask and redis locally (on Mac)
1. Go to service/task_service.py and look for redis_host (Line#7). Make sure the redis_host is `localhost` to connect to Redis server. Line#7 should look like:
`def __init__(self, redis_host='localhost', redis_port=6379):`
2. Start the redis server using below command <br />
`redis-server`
3. Install the required libraries (do once) <br />
`pip3 install -r requirements.txt`
4. Start the flask service <br />
`python3 main.py`

# Run the service using Docker containers (on Mac)
1. Create a docker network <br />
`docker create network flask_net`
2. Pull redis image <br />
`docker pull redis`
3. Run redis container on the above created network <br />
`docker run -d --name my_redis --network flask_net redis`
4. Change the redis_host to `my_redis` (the name of the redis docker container)
5. Build the flask service docker image <br />
`docker build -t task .`
6. Run the flask microservice <br />
`docker run -d --name task --network flask_net -p 5000:5000 task`
