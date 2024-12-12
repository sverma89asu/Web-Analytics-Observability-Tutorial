# Run good code (with Exception handling and logging) using Docker
1. Go to GroceryRestAPIV2 <br />
`cd GroceryRestAPIV2`
2. Build the docker image <br />
`docker build -t grocery .`
3. Run the docker container in detached mode(-d) on port 8082 <br />
`docker run -d --name groceryV2 -p 8082:8080 grocery`
4. Run the docker container in **DEBUG** mode by passing **LOG_LEVEL** in detached mode(-d) <br />
`docker run -d --name groceryV2 -p 8082:8080 -e LOG_LEVEL=DEBUG grocery`

# Run service and Prometheus on same network
1. Go to GroceryRestAPIV2 <br />
   `cd GroceryRestAPIV2`
2. Create a docker network <br />
   `docker network create spring_boot`
3. Build the docker image <br />
   `docker build -t grocery .`
4. Run the docker container in detached mode(-d) on port 8081 using the network created in Step 2 <br />
   `docker run -d --name groceryV2 -p 8082:8080 --network spring_boot grocery_v2`
5. Run the docker container in **DEBUG** mode by passing **LOG_LEVEL** while running the container in detached mode(-d) <br />
   `docker run -d --name groceryV2 -p 8082:8080 -e LOG_LEVEL=DEBUG --network spring_boot grocery_v2`

## Run Prometheus on same network created in Step 2
1. Go to GroceryRestAPIV2 <br />
   `cd GroceryRestAPIV2`
2. Make sure `prometheus.yml` is present with the configuration
3. Run below command to run prometheus <br />
   `docker run -p 9090:9090 -v $(pwd)/prometheus.yml:/etc/prometheus/prometheus.yml --name prom --network spring_boot -d prom/prometheus`
4. Published metrics can be found at `http://localhost:8081/actuator/prometheus`
5. Go to `localhost:9090` to view different metrics

# Run the application using gradle or Eclipse
1. At the terminal, "./gradlew bootRun"
2. In Eclipse, install the Spring Boot tools, then right-click on the Project in Package Explorer or select "Run As... --> Spring Boot Application"

Once running, test by going to http://localhost:8082/api/groceries, you should see an initial list of grocery items in JSON.

# Dependencies
1. gradle wrapper 8.10.1
2. Java 21 or later
3. Spring 3.1.2 or later