FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /opt/app
ARG JAR_FILE=build/libs/*.war
COPY ${JAR_FILE} otp.war
EXPOSE 587/tcp
ENTRYPOINT ["java","-jar","otp.war"]