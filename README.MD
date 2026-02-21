# task-management-hexagonal

Demo for hexagonal architecture

## Shell

```sh
nix-shell --run bash ./app.nix

mvn -N wrapper:wrapper -Dmaven=3.9.11

# intelliJ cli arguments example
--spring.profiles.active=dev,repository-elasticsearch
--spring.profiles.active=dev,repository-memory
--spring.profiles.active=dev,repository-mysql

# run with memory data store
./mvnw -f ./task-management-backend/pom.xml -pl entrypoint spring-boot:run \
  -Dspring-boot.run.profiles=dev,repository-memory

# run with elasticsearch data store
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
  -e "xpack.security.enabled=true" \
  -e "ELASTIC_PASSWORD=MyStrongPass123!" \
  -e "ES_JAVA_OPTS=-Xms2g -Xmx2g" \
  docker.elastic.co/elasticsearch/elasticsearch:9.2.3

# check if elasticsearch works
curl -u elastic:MyStrongPass123! -X GET "http://localhost:9200"

# setup mysql for development
# exactly this config is used for test containers: com.iv127.task.management.backend.entrypoint.config.MySqlTestContainer
docker pull mysql:9.6.0
docker run --rm -d \
  --name mysql \
  -e MYSQL_ROOT_PASSWORD=secret \
  -v $(echo "$PROJECT_ROOT")/init.sql:/docker-entrypoint-initdb.d/init.sql \
  -p 3306:3306 \
  mysql:9.6.0

docker exec -it mysql bash
mysql -h localhost -P 3306 --protocol=tcp -u root -p'secret' -D demo1
```

## Property: `spring.graphql.graphiql.enabled=true`

- path should be same as folder name where graphqls is kept
- `http://localhost:8080/graphiql?path=/graphql`
- <img src="./_forReadme/image.png" />

## Observation

- Graphql doesn't support multipart file upload, to achieve this define a custom REST endpoint
- (**To remember**) Maven dependency scopes
  - compile(default) - packaged in artifact, available in compile,runtime and test
  - provided - (NOT) packaged in artifact, available in compile,test, (NOT)runtime
  - runtime - packaged in artifact, available in test, runtime, (NOT)compile
  - test - (NOT) packaged in artifact, available in test-compile,test-runtime
  - system - reference to .jar in local filesystem
  - import - Centralized version management, works in combination with `<dependencyManagement>`

## Todo

- Add custom starter with soft default config
- Add tests for each repository using test containers
- Add tests for rest layer

## References

- <https://stackoverflow.com/questions/38133808/spring-multiple-profiles-active>
- <https://github.com/TejasRawat/task-management-service>
- <https://maven.apache.org/plugins/maven-pmd-plugin/examples/multi-module-config.html>
- <https://howtodoinjava.com/spring-data/elasticsearch-with-spring-boot-data/>
