# Run the application using Docker
1. Build the docker image <br />
`docker build -t grocery .`
2. Run the docker container <br />
`docker run -d --name groceryV1 -p 8081:8080 grocery_v1`

# Run the application using gradle or Eclipse
1. At the terminal, "./gradlew bootRun"
2. In Eclipse, install the Spring Boot tools, then right-click on the Project in Package Explorer or select "Run As... --> Spring Boot Application"

Once running, test by going to http://localhost:8081/api/groceries, you should see an initial list of grocery items in JSON.

# Dependencies
1. gradle wrapper 8.10.1
2. Java 21 or later
3. Spring 3.1.2 or later