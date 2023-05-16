FROM openjdk:17
ARG token
ENV BOT_TOKEN=$token
COPY target/bot-0.0.1.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app.jar"]