FROM nginx:alpine

RUN apk add openjdk11;

RUN rm -f /etc/nginx/conf.d/default.conf
COPY nginx /etc/nginx/.

WORKDIR /pet

ADD docker-entrypoint.sh ./docker-entrypoint.sh
RUN chmod a+x docker-entrypoint.sh

COPY BOOT-INF/lib ./lib
COPY META-INF ./META-INF
COPY BOOT-INF/classes .
COPY ui ./static

EXPOSE 80
EXPOSE 443

ENTRYPOINT ["/pet/docker-entrypoint.sh"]
