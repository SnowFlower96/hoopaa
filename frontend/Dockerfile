FROM node:lts-alpine as build-stage

WORKDIR /app

COPY package*.json ./

RUN npm install --save --legacy-peer-deps

COPY . .

RUN npm run build

FROM nginx:stable-alpine as production-stage
COPY --from=build-stage /app/dist /usr/share/nginx/html
COPY ssl /etc/nginx/ssl
RUN mv /etc/nginx/ssl/default.conf /etc/nginx/conf.d
RUN chmod 600 /etc/nginx/ssl/fullchain.pem /etc/nginx/ssl/privkey.pem
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
