FROM amazoncorretto:17-alpine as corretto-deps

COPY ./target/stub-service-0.0.1-SNAPSHOT.jar /app/

RUN unzip /app/stub-service-0.0.1-SNAPSHOT.jar -d temp &&  \
    jdeps  \
      --print-module-deps \
      --ignore-missing-deps \
      --recursive \
      --multi-release 17 \
      --class-path="./temp/BOOT-INF/lib/*" \
      --module-path="./temp/BOOT-INF/lib/*" \
      /app/stub-service-0.0.1-SNAPSHOT.jar > /modules.txt

FROM amazoncorretto:17-alpine as corretto-jdk

COPY --from=corretto-deps /modules.txt /modules.txt

# hadolint ignore=DL3018,SC2046
RUN apk add --no-cache binutils && \
    jlink \
     --verbose \
     --add-modules "$(cat /modules.txt),jdk.crypto.ec,jdk.crypto.cryptoki" \
     --strip-debug \
     --no-man-pages \
     --no-header-files \
     --compress=2 \
     --output /jre

# hadolint ignore=DL3007
FROM alpine:latest
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

COPY --from=corretto-jdk /jre $JAVA_HOME

EXPOSE 8080
COPY ./target/stub-service-0.0.1-SNAPSHOT.jar /app/
WORKDIR /app

CMD ["java", "-jar", "stub-service-0.0.1-SNAPSHOT.jar"]