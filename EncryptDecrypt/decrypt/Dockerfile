FROM maven:3.8.4-openjdk-17-slim

WORKDIR /decrypt

COPY . .

RUN mvn clean install

EXPOSE 8082

CMD ["mvn", "spring-boot:run"]