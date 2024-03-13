cd ..
export JAVA_HOME='/home/ilnur/.jdks/corretto-17.0.9'
./mvnw clean package
scp target/stub-service-0.0.1-SNAPSHOT.jar ubuntu@kz:/home/ubuntu
ssh kz << EOF
sudo systemctl restart stub-server
EOF