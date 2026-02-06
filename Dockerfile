FROM eclipse-temurin:25.0.2_10-jre-alpine
LABEL authors="ShizukiNatsuki"

COPY target/korrent.jar /app/korrent/
WORKDIR /app/korrent
VOLUME /app/korrent

EXPOSE 8080

ENTRYPOINT exec java -jar korrent.jar
