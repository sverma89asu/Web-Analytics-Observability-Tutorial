# Run the flask and redis locally
1. Go to service/task_service.py and look for redis_host (Line#7). Make sure the redis_host is `localhost` to connect to Redis server. Line#7 should look like:
`def __init__(self, redis_host='localhost', redis_port=6379):`
2. Start the redis server using below command <br />
`redis-server`
3. Install the required libraries (do once) <br />
`pip3 install -r requirements.txt`
4. Start the flask service <br />
`python3 main.py`

# Run the service (good code) using Docker containers
1. Go to tasks-manager-v2 <br />
`cd tasks-manager-v2`
2. Create a docker network (no need to create if already created) <br />
`docker network create flask_net`
3. Pull redis image <br />
`docker pull redis`
4. Run redis container on the above created network (not required if redis server is already running) <br />
`docker run -d --name my_redis --network flask_net redis`
5. Change the redis_host to `my_redis` in the .env file (the name of the redis docker container)
6. Build the flask service docker image <br />
`docker build -t task_v2 .`
7. Run the flask microservice <br />
`docker run -d --name task_v2 --network flask_net -p 8050:8050 --env-file .env task_v2`

# Enable multi-threading and test Redis connection pool
1. Change the **THREADED** flag to **True** to enable multi-threading. If it's not multi-threaded, then one request will be processed at a time.
2. To test redis connection pool, change **MAX_CONN** as required.
3. Follow Steps 3-7 from Good Code Docker container instructions
4. Jmeter test is located at `jmeter/Redis_Conn_Pool.jmx`. A variable **PORT** is configured with default value as **5000**. There are 4 thread groups with 10, 25, 50 and 100 users. The API is configured to be `POST http://localhost:5000/tasks` to create the task. <br />
5. Run below command to run the jmeter test where **JPORT** can be changed: <br />
`jmeter -n -t jmeter_test/Redis_Conn_Pool.jmx -JPORT=5000 -l results.jtl`
6. Open the `results.jtl` file and look for **INTERNAL SERVER ERROR**. Other option is to open the Docker container logs and look for **ERROR**. You should see errors saying Too many connections.

# Run Promethus locally
1. Make sure `prometheus.yml` is present with the configuration
2. Run below command to run prometheus <br />
`docker run -p 9090:9090 -v $(pwd)/prometheus.yml:/etc/prometheus/prometheus.yml --name prom --network flask_net -d prom/prometheus`
3. Published metrics can be found at `http://localhost:5000/metrics`
4. Go to `localhost:9090` to view different metrics