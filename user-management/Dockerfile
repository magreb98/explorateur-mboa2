FROM ubuntu:latest
LABEL authors="MAGREB"
RUN mvn clean install
EXPOSE 8084
ENTRYPOINT ["springboot", "run"]