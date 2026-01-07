# 项目部署指南

## 1. 环境要求

- **操作系统**: Windows 10/11, Linux, macOS
- **Docker**: 20.10.0+  (推荐使用最新稳定版)
- **Docker Compose**: 1.29.0+  (推荐使用最新稳定版)
- **内存**: 至少 8GB RAM
- **CPU**: 至少 4核 CPU
- **磁盘空间**: 至少 50GB 可用空间

## 2. 部署前准备

### 2.1 安装Docker和Docker Compose

#### Windows
1. 下载并安装 Docker Desktop for Windows: https://www.docker.com/products/docker-desktop
2. 启动Docker Desktop，确保Docker和Docker Compose服务正常运行

#### Linux
1. 参考官方文档安装Docker: https://docs.docker.com/engine/install/
2. 参考官方文档安装Docker Compose: https://docs.docker.com/compose/install/

#### macOS
1. 下载并安装 Docker Desktop for Mac: https://www.docker.com/products/docker-desktop
2. 启动Docker Desktop，确保Docker和Docker Compose服务正常运行

### 2.2 配置Docker镜像加速

由于网络原因，建议配置Docker国内镜像源以提高镜像拉取速度。

#### Windows/macOS
在Docker Desktop中配置：
1. 打开Docker Desktop设置
2. 选择"Docker Engine"选项卡
3. 在配置文件中添加以下内容：

```json
{
  "registry-mirrors": [
    "https://docker.mirrors.ustc.edu.cn",
    "https://hub-mirror.c.163.com",
    "https://mirror.baidubce.com"
  ]
}
```

4. 点击"Apply & Restart"保存并重启Docker

#### Linux
在`/etc/docker/daemon.json`文件中添加镜像源配置：

```bash
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": [
    "https://docker.mirrors.ustc.edu.cn",
    "https://hub-mirror.c.163.com",
    "https://mirror.baidubce.com"
  ]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```

## 3. 项目结构

```
hao-topic-master/
├── springcloud-brushtopic-api/  # 后端微服务代码
│   ├── topic-api/              # API模块
│   ├── topic-client/           # 客户端模块
│   ├── topic-common/           # 通用模块
│   ├── topic-gateway/          # 网关模块
│   ├── topic-model/            # 模型模块
│   ├── topic-security/         # 安全模块
│   ├── pom.xml                 # Maven父项目
│   └── Dockerfile              # 后端Dockerfile
├── uni-app-vue3-brushtopic/    # 前端代码
│   ├── api/                    # API接口
│   ├── components/             # 组件
│   ├── pages/                  # 页面
│   ├── static/                 # 静态资源
│   ├── App.vue                 # 入口组件
│   ├── main.js                 # 入口文件
│   ├── nginx.conf              # Nginx配置
│   ├── package.json            # 依赖配置
│   └── Dockerfile              # 前端Dockerfile
├── sql/                        # 数据库脚本
│   └── topic.sql               # 初始化SQL
└── docker-compose.yml          # Docker Compose配置
```

## 4. 构建和运行

### 4.1 构建后端微服务

1. 进入后端代码目录：

```bash
cd hao-topic-master/springcloud-brushtopic-api
```

2. 使用Maven构建所有后端服务：

```bash
mvn clean install -DskipTests
```

### 4.2 构建和运行所有服务

使用Docker Compose构建和运行所有服务：

```bash
cd hao-topic-master
docker-compose up -d --build
```

**说明：**
- `-d`: 后台模式运行容器
- `--build`: 构建镜像

## 5. 服务访问

### 5.1 服务列表

| 服务名称 | 访问地址 | 说明 |
|---------|---------|------|
| 前端应用 | http://localhost | 主页面 |
| MySQL数据库 | localhost:3306 | 数据库服务 |
| Redis缓存 | localhost:6379 | 缓存服务 |
| RabbitMQ | localhost:15672 | 消息队列管理界面 |
| Nacos | localhost:8848/nacos | 服务注册中心 |
| Web网关 | localhost:8080 | API网关 |
| 题目服务 | localhost:8081 | 题目相关接口 |
| AI服务 | localhost:8082 | AI相关接口 |
| 系统服务 | localhost:8083 | 系统相关接口 |

### 5.2 登录信息

- **RabbitMQ**: 用户名 `guest`，密码 `guest`
- **Nacos**: 用户名 `nacos`，密码 `nacos`

## 6. 部署验证

### 6.1 检查容器状态

```bash
docker-compose ps
```

所有服务的状态应为 `Up`。

### 6.2 检查服务健康

```bash
docker-compose logs -f
```

查看所有服务的日志，确保没有错误信息。

### 6.3 验证前端访问

打开浏览器访问 http://localhost，确保页面正常加载。

### 6.4 验证后端API

使用curl或Postman验证后端API是否正常工作：

```bash
curl http://localhost/api/topic/getTopicList
```

## 7. 常见问题解决

### 7.1 Docker镜像拉取失败

**问题**: 拉取镜像时出现"EOF"或"connection reset by peer"错误

**解决方法**:
1. 检查网络连接
2. 确保已配置正确的Docker镜像加速源
3. 重试拉取命令

### 7.2 容器启动失败

**问题**: 容器启动后立即退出，状态显示为`Exited`

**解决方法**:
1. 查看容器日志：`docker-compose logs <service-name>`
2. 根据日志中的错误信息进行排查
3. 常见错误包括：端口被占用、依赖服务未启动、环境变量配置错误等

### 7.3 服务间通信失败

**问题**: 服务之间无法正常通信

**解决方法**:
1. 确保所有服务都在同一个Docker网络中
2. 检查服务名称和端口配置是否正确
3. 查看网络配置：`docker network inspect brushtopic-network`

### 7.4 数据库连接失败

**问题**: 后端服务无法连接到数据库

**解决方法**:
1. 确保MySQL服务已正常启动
2. 检查数据库连接配置是否正确
3. 查看数据库日志：`docker-compose logs mysql`

## 8. 管理命令

### 8.1 启动所有服务

```bash
docker-compose up -d
```

### 8.2 停止所有服务

```bash
docker-compose down
```

### 8.3 重启所有服务

```bash
docker-compose restart
```

### 8.4 查看服务日志

```bash
# 查看所有服务日志
docker-compose logs -f

# 查看特定服务日志
docker-compose logs -f <service-name>
```

### 8.5 查看容器状态

```bash
docker-compose ps
```

### 8.6 进入容器内部

```bash
docker-compose exec <service-name> bash
```

## 9. 数据备份与恢复

### 9.1 备份MySQL数据

```bash
docker-compose exec mysql mysqldump -u root -proot brushtopic > backup.sql
```

### 9.2 恢复MySQL数据

```bash
docker-compose exec -i mysql mysql -u root -proot brushtopic < backup.sql
```

### 9.3 备份Redis数据

```bash
docker-compose exec redis redis-cli SAVE
docker cp $(docker-compose ps -q redis):/data/dump.rdb ./redis-backup.rdb
```

### 9.4 恢复Redis数据

```bash
docker cp ./redis-backup.rdb $(docker-compose ps -q redis):/data/dump.rdb
docker-compose restart redis
```

## 10. 性能优化建议

1. **增加资源限制**：根据实际需求为每个服务配置适当的CPU和内存限制
2. **使用更高效的存储驱动**：在生产环境中使用`overlay2`存储驱动
3. **启用日志轮转**：配置Docker日志轮转，避免日志文件过大
4. **使用持久化存储**：为关键服务配置适当的持久化存储
5. **监控服务状态**：使用Docker监控工具如Portainer或Prometheus监控服务状态

## 11. 安全建议

1. **修改默认密码**：在生产环境中修改所有服务的默认密码
2. **限制容器特权**：不要以特权模式运行容器
3. **使用私有镜像仓库**：在生产环境中使用私有Docker镜像仓库
4. **网络隔离**：为不同的服务配置适当的网络隔离
5. **定期更新镜像**：定期更新Docker镜像以修复安全漏洞