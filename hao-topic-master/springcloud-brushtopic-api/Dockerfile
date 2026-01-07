# 使用Eclipse Temurin JDK 17作为基础镜像
FROM eclipse-temurin:17-jdk

# 安装curl用于健康检查
RUN apt-get update && apt-get install -y curl

# 设置工作目录
WORKDIR /app

# 复制构建好的jar文件到容器中
COPY ./target/*.jar app.jar

# 设置环境变量
ENV SPRING_PROFILES_ACTIVE=dev
ENV JAVA_OPTS="-Xms512m -Xmx1024m"
ENV SPRING_CLOUD_NACOS_DISCOVERY_SERVER-ADDR=nacos:8848
ENV SPRING_CLOUD_NACOS_CONFIG_SERVER-ADDR=nacos:8848
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/brushtopic?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false
ENV SPRING_DATASOURCE_USERNAME=brushtopic
ENV SPRING_DATASOURCE_PASSWORD=password
ENV SPRING_REDIS_HOST=redis
ENV SPRING_REDIS_PORT=6379
ENV SPRING_RABBITMQ_HOST=rabbitmq
ENV SPRING_RABBITMQ_PORT=5672
ENV SPRING_RABBITMQ_USERNAME=guest
ENV SPRING_RABBITMQ_PASSWORD=guest

# 暴露端口
EXPOSE 8080

# 健康检查
HEALTHCHECK --interval=30s --timeout=30s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]