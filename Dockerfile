FROM tomcat:8.5

RUN ["rm", "-rf", "/usr/local/tomcat/webapps/ROOT"]
ADD target/spring-rest-docker.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
