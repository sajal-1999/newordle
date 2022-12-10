FROM node:19-alpine3.16 as BUILD

WORKDIR /app

COPY package.json .

RUN npm install

COPY . ./

RUN npm run build

FROM nginx:1.23.2-alpine

COPY --from=BUILD /app/build /usr/share/nginx/html/

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]

#ENTRYPOINT [ "npm", "start" ]