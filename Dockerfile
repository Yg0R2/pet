FROM nginx:alpine

RUN apk add openjdk11;

RUN rm -f /etc/nginx/conf.d/default.conf
COPY ./nginx/conf.d/ /etc/nginx/conf.d/.

WORKDIR /pet

COPY BOOT-INF/lib ./lib
COPY META-INF ./META-INF
COPY BOOT-INF/classes .
COPY ui ./static

EXPOSE 80
EXPOSE 443

ENTRYPOINT ["sh", "-c", "nginx && java -cp .:./lib/* com.yg0r2.pet.PetApplication"]
