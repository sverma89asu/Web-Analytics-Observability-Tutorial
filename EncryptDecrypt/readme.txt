NOTE: Make sure that 8081 and 8082 ports are free to be able to run below microservices

Valid API (GET API):
http://localhost:8081/encrypt?text=bar&key=3
http://localhost:8081/encrypt?key=3&text=foo

http://localhost:8082/decrypt?text=foo&key=3
http://localhost:8082/decrypt?key=3&text=cll

Build and run test using mvn
1. Go to encrypt folder
2. Run test using below command:
mvn clean install
3. Run on port 8081 using below command:
mvn spring-boot:run

Build and run test using mvn
1. Go to decrypt folder
2. Run test using below command:
mvn clean install
3. Run on port 8082 using below command:
mvn spring-boot:run

Build and run using docker:
1. Build the docker images using command:
docker build -t encrypt_amex encrypt/. 
docker build -t decrypt_amex decrypt/.

2. Run the docker images using command:
docker run -d --name encrypt -p 8087:8081 encrypt_amex
docker run -d --name decrypt -p 8086:8082 decrypt_amex