FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
COPY *.jar AuthenticationMS.jar
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
RUN addgroup -S spring && adduser -S appuser -G spring
USER appuser
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar AuthenticationMS.jar" ]