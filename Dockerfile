FROM maven:3.8.1-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM tomcat:10.1.36-jdk17
COPY --from=build /home/app/target/*.war /usr/local/tomcat/webapps/app.war
EXPOSE 8080