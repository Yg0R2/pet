#!/bin/sh
set -eu

export BACKEND_URL=${BACKEND_URL:-"https://localhost:8443"}

envsubst '${BACKEND_URL}' < /etc/nginx/conf.d/app.conf.template > /etc/nginx/conf.d/app.conf

nginx && java -cp .:./lib/* com.yg0r2.pet.PetApplication

exec "$@"
