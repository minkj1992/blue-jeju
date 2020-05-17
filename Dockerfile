FROM openjdk:11-jdk
VOLUME /tmp
EXPOSE 8081
ARG JAR_FILE=target/blue-jeju-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} blue-jeju.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/authentication.jar"]