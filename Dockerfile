FROM openjdk:17
ARG JAR_FILE=build/libs/rcon-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} ./rcon-api.jar
EXPOSE 8080/tcp
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "./rcon-api.jar"]