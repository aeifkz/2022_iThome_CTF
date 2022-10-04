FROM centos:7

RUN mkdir /app && yum install polkit-0.112-26.el7 -y
ADD tomcat /app/tomcat
ADD Spring4Shell.war /app/tomcat/webapps
ADD jdk-16.0.1  /app/jdk-16.0.1
ENV JAVA_HOME=/app/jdk-16.0.1
ENV PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/app/jdk-16.0.1/bin
ENV CATALINA_HOME=/app/tomcat
EXPOSE 8080

RUN adduser aeifkz
RUN chown -R aeifkz:aeifkz /app
USER aeifkz

CMD ["/app/tomcat/bin/catalina.sh","run"]
