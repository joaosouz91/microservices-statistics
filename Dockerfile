FROM openjdk:8
ADD target/microservices-statistics.jar microservices-statistics.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","microservices-statistics.jar"]