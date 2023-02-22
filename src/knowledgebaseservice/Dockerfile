FROM openjdk8-jdk-alpine
COPY . /app
WORKDIR /app
RUN ./mvnw package
EXPOSE 8080
CMD [java, -jar, target/knowledgebaseservice-0.0.1-SNAPSHOT.jar]