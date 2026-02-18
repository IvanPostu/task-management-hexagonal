# task-management-hexagonal

Demo for hexagonal architecture

## Shell

```sh
nix-shell --run bash ./app.nix

mvn -N wrapper:wrapper -Dmaven=3.9.11

# intelliJ cli arguments example
--spring.profiles.active=dev,repository-elasticsearch 

./mvnw -f ./task-management-backend/pom.xml -pl entrypoint spring-boot:run \
  -Dspring-boot.run.profiles=dev,repository-memory

./mvnw -f ./task-management-backend/pom.xml -pl entrypoint spring-boot:run \
  -Dspring-boot.run.profiles=dev,repository-elasticsearch

./mvnw -f ./task-management-backend/pom.xml -pl build-tools clean install 
./mvnw -f ./task-management-backend/pom.xml verify # also includes test & pmd
./mvnw -f ./task-management-backend/pom.xml site
./mvnw -f ./task-management-backend/pom.xml clean package

# elasticsearch for dev
docker run --rm -d \
  --name elasticsearch \
  -p 9200:9200 \
  -p 9300:9300 \
  -e "discovery.type=single-node" \
  -e "xpack.security.enabled=false" \
  -e "ES_JAVA_OPTS=-Xms2g -Xmx2g" \
  docker.elastic.co/elasticsearch/elasticsearch:9.2.3

curl -X GET "http://localhost:9200"
```

## Property: `spring.graphql.graphiql.enabled=true`

- path should be same as folder name where graphqls is kept
- `http://localhost:8080/graphiql?path=/graphql`
- <img src="./_forReadme/image.png" />

## Observation

- Graphql doesn't support multipart file upload, to achieve this define a custom endpoint

## Todo

- Add custom starter with soft default config

## References

- <https://stackoverflow.com/questions/38133808/spring-multiple-profiles-active>
- <https://github.com/TejasRawat/task-management-service>
- <https://maven.apache.org/plugins/maven-pmd-plugin/examples/multi-module-config.html>
