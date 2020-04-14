FROM openjdk:8
#MAINTAINER Meng Dai "vanish1984@gmail.com"
##RUN mkdir -p /data/app_home/config
##镜像被墙，改为阿里镜像源
#RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories
## 设置时区为上海
#RUN apk add tzdata && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
#    && echo "Asia/Shanghai" > /etc/timezone \
#    && apk del tzdata
#RUN apk add --no-cache tini

# 包与运行
RUN mkdir /data
ADD ./target/mini-admin-0.0.1-SNAPSHOT.jar /data/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-server", "-Xmx512m", "-Xms512m","-Dspring.profiles.active=prod", "/data/app.jar"]
