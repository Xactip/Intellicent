FROM openjdk8-jdk-alpine
COPY . /app
WORKDIR /app
RUN ./mvnw package
EXPOSE 8081
CMD [java, -jar, target/usermanagementservice-0.0.1-SNAPSHOT.jar]