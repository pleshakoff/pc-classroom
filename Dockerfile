FROM openjdk:8u181-jre-slim
COPY /build/libs/pc-classroom.jar pc-classroom.jar
ENTRYPOINT ["java",  "-jar","/pc-classroom.jar"]
