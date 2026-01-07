# 第一阶段：构建前端应用
FROM node:latest AS build-stage

# 设置工作目录
WORKDIR /app

# 设置 npm 镜像源（加速下载）
RUN npm config set registry https://registry.npmmirror.com

# 复制package.json
COPY package*.json ./
COPY vite.config.js ./

# 安装依赖
RUN npm install

# 复制项目文件
COPY . .

# 创建正确的目录结构
RUN mkdir -p src && \
    cp -r pages src/ && \
    cp -r components src/ && \
    cp -r api src/ && \
    cp -r utils src/ && \
    cp -r styles src/ && \
    cp -r static src/ && \
    cp -r uni_modules src/ && \
    cp uni.promisify.adaptor.js src/

# 构建H5版本
RUN npm run build:h5

# 第二阶段：部署前端应用
FROM nginx:latest AS production-stage

# 复制Nginx配置文件
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 复制构建好的前端资源到Nginx的html目录
COPY --from=build-stage /app/dist/build/h5 /usr/share/nginx/html

# 暴露端口
EXPOSE 80

# 启动Nginx服务
CMD ["nginx", "-g", "daemon off;"]
