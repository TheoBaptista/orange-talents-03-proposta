## Runner Image
FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY  ${JAR_FILE}  /usr/app/app.jar
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]