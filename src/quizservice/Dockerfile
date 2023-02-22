FROM openjdk8-jdk-alpine
COPY . /app
WORKDIR /app
RUN ./mvnw package
EXPOSE 8082
CMD [java, -jar, target/quizservice-0.0.1-SNAPSHOT.jar]