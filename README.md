# AI智能刷题项目

### 项目介绍 🔥

- 此项目是基于**SpringCloud微服务 + Vue3+ts+uniapp**开发的AI智能刷题项目
- 项目包含后台，h5，后端2个网关，4个服务
- 项目功能多，AI功能亮点多极其有创意
- 后端架构设计规范前端页面简洁美观有创意


### 项目亮点 ⚡

- 使用**Redis**刷题次数排行榜提升响应速度
- 使用**Redis**实现分布式锁防止消息重复消费
- 使用**Rabbitmq**异步生成AI答案提升响应速度
- 使用**Rabbitmq**异步通过AI服务审核会员题目无需人工
- 使用**Minio**存储专题图片存储免费好用
- 使用**SpringTask**实现每日刷题题目动态推荐用户专属题目提高用户留存率
- 使用**阿里云百炼TTS**语音合成技术朗读面试题内容提升用户刷题兴趣
- 使用**SpringAi**接入阿里云百炼api接口进行对话有免费额度
- 使用**Echarts**仿力扣分类气泡图展示题目分类和数量有创意
- 使用**Echarts**仿gitee日历热点图展示用户每日刷题次数提高用户刷题率
- 使用**Security**严格控制角色权限保障会员权益


### 项目收获 🔨

- 收获**Redis**缓存
- 收获**Rabbitmq**消息队列
- 收获**SpringTask**定时任务
- 收获**SpringCloud**微服务设计
- 收获**Nacos**配置中心与注册中心
- 收获**SpringAi**与AI对话
- 收获**SpringSecurity**权限管理
- 收获**OpenFeign**远程调用
- 收获**MybatisPlus**操作数据库
- 收获**Gateway**路由转发
- 收获**Vue3**前端框架
- 收获**AntDv**UI框架
- 收获**Uniapp**多端开发技术
- 收获**Echats**图表设计
- 收获**Uv-ui**手机端UI框架
- 收获**Tts**语音合成技术的使用

### 运行环境 🗄️

h5端： Vue3 + Uv-ui + Uniapp + Zero-markdown-view...

后台： Vue3 + AntDv + Echarts + Ts + Md-editor-v3....

后端： JDK17 + SpringBoot3.2 + SpringCloud2023 + Nacos + Gateway + OpenFeign + SpringTask +  Mysql + Redis + RabbitMQ + Minio + Mybatis-Plus....

##### 前端

**以下需要版本要求其他技术版本新一点就可以**

| 名称 | 环境 |
| ---- | ---- |
| Npm  | 10+  |
| Vue3 | 3+   |
| Node | 18+  |

##### 后端

**以下需要版本要求其他技术版本新一点就可以**

| 名称          | 环境  |
| ------------- | ----- |
| MySQL         | 8.0.13+ |
| JDK           | 17    |
| SpringBoot    | 3.2   |
| SpringCloud   | 2023  |
| SpringAi      |1.0.0-M6|

##### 后台
**大屏**

![输入图片说明](%E5%9B%BE%E7%89%87/eaff5b1d217844249a763fb66bc689c9.png) 

**管理员首页** 

![输入图片说明](图片/Snipaste_2025-05-08_18-37-22.png)
![输入图片说明](图片/Snipaste_2025-05-08_18-37-38.png)

**用户首页** 

![输入图片说明](图片/Snipaste_2025-05-08_18-39-27.png)

**AI刷题页面** 

![输入图片说明](图片/Snipaste_2025-05-08_18-40-11.png)
![输入图片说明](图片/Snipaste_2025-05-08_18-40-26.png)

**项目结构**

![输入图片说明](图片/Snipaste_2025-05-08_18-43-00.png)


### 项目启动 🚀

##### h5
**uni-app-vue3-brushtopic**文件夹
```
npm install
用HBuilder启动
```

##### 后台
**vue3-ts-brushtopic-admin**文件夹
```
npm install
npm run dev
```

##### 后端
**springcloud-brushtopic-api**文件夹

- 打开项目 配置jdk17 加载maven依赖
- 安装mysql redis mq nacos minio
- 安装完nacos后需要将配置文件文件夹中的配置文件导入到nacos中
- 打开common-config配置文件需要配置自己的
- 打开common-minio配置文件需要配置自己的
- 打开service-ai配置文件配置自己的阿里云百炼sk
- 安装完minio后需要创建topic桶并开放public权限
- 将sql文件夹中的sql放入到你的数据库中启动！

### Docker 部署 🐳

#### 环境要求
- Docker 20.10+ 
- Docker Compose 1.29+

#### 部署准备
1. 确保 Docker 服务已启动
2. 配置 Docker 国内镜像源（可选，加速镜像拉取）

#### 构建与启动

##### 1. 构建镜像
在项目根目录执行：
```bash
# 构建所有服务镜像
docker-compose up -d --build

# 或单独构建前端镜像
docker-compose build frontend
```

##### 2. 启动服务
```bash
# 后台启动所有服务
docker-compose up -d

# 查看服务状态
docker-compose ps

# 查看日志
docker-compose logs -f
```

##### 3. 访问服务
- **H5 前端**：http://localhost:80
- **后台管理**：http://localhost:9991
- **API 网关**：http://localhost:9993

#### 容器管理

##### 停止服务
```bash
docker-compose down
```

##### 重启服务
```bash
docker-compose restart
```

##### 查看容器日志
```bash
# 查看所有容器日志
docker-compose logs -f

# 查看特定容器日志
docker-compose logs -f frontend
```

#### 数据持久化
- MySQL 数据：挂载到 `./mysql/data` 目录
- Redis 数据：挂载到 `./redis/data` 目录
- Nacos 配置：挂载到 `./nacos/data` 目录

#### 环境变量配置
主要环境变量在 `docker-compose.yml` 文件中配置，包括：
- 数据库连接信息
- Redis 配置
- Nacos 配置
- 服务端口映射

#### 健康检查
所有服务均配置了健康检查机制，确保服务正常运行：
- 前端服务：通过 Nginx 端口检查
- 后端服务：通过服务端口检查
- 数据库服务：通过连接检查

#### 常见问题

##### 1. 镜像拉取失败
```bash
# 配置国内镜像源
docker pull docker.mirrors.ustc.edu.cn/library/nginx:1.20-alpine
```

##### 2. 端口冲突
修改 `docker-compose.yml` 中的端口映射：
```yaml
ports:
  - "8080:80"  # 将8080改为其他空闲端口
```

##### 3. 服务启动失败
查看日志定位问题：
```bash
docker-compose logs -f [服务名]
```


### 项目问题 🧩

**待反馈** 






