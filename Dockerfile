FROM debian:latest

#需要下载jdk，将eureka中service-discovery打包的jar包改为eureka.jar
COPY jdk-8u101-linux-x64.tar.gz /opt
COPY eureka.jar /opt

RUN mkdir /opt/jdk8  \
    && tar -zxf /opt/jdk-8u101-linux-x64.tar.gz -C /opt/jdk8 --strip-components 1 \
    && rm -rf /opt/*.tar.gz

ENV JAVA_HOME=/opt/jdk8
ENV PATH=$JAVA_HOME/bin:$PATH

EXPOSE 8761

ENTRYPOINT java -jar /opt/eureka.jar
