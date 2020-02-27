FROM openjdk:12-alpine

WORKDIR /pet

COPY BOOT-INF/lib ./lib
COPY META-INF ./META-INF
COPY BOOT-INF/classes .
COPY ui ./static

EXPOSE 8080
ENTRYPOINT ["java", "-cp", ".:./lib/*", "com.yg0r2.pet.PetApplication"]
