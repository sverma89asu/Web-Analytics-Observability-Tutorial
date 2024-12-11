FROM maven:3.8.4-openjdk-17-slim

WORKDIR /encrypt

COPY . .

RUN mvn clean install

EXPOSE 8081

CMD ["mvn", "spring-boot:run"]