FROM openjdk:15-ea-jdk-slim
ADD service-application.jar /usr/local
WORKDIR /usr/local
EXPOSE 8080
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Duser.timezone=CET", "-jar", "service-application.jar"]