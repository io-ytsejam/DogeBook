# Stage 1 - Build frontend
FROM node:13 as react-build
WORKDIR /frontend
COPY . ./
RUN npm rebuild node-sass
RUN npm install
RUN npm run build

# Stage 2 - Serve frontend using Nginx
FROM nginx:alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=react-build /frontend/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
