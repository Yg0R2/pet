FROM openjdk:12-alpine

ARG EXPLODED_JAR_DIR=build/exploded

WORKDIR /pet

COPY ${EXPLODED_JAR_DIR}/BOOT-INF/lib ./lib
COPY ${EXPLODED_JAR_DIR}/META-INF ./META-INF
COPY ${EXPLODED_JAR_DIR}/BOOT-INF/classes .

EXPOSE 8080
ENTRYPOINT ["java", "-cp", ".:./lib/*", "com.yg0r2.pet.PetApplication"]
