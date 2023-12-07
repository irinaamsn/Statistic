FROM openjdk:20
LABEL authors="irinaamsn"

WORKDIR /app

COPY build/libs/statistic-0.0.1-SNAPSHOT.jar statistic.jar

CMD ["java", "-jar", "statistic.jar"]