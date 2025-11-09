FROM eclipse-temurin:25
LABEL authors="ShizukiNatsuki"

COPY target/korrent.jar /app/korrent/
WORKDIR /app/korrent
VOLUME /app/korrent

ENTRYPOINT exec java -jar korrent.jar
