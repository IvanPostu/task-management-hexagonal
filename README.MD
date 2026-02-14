# task-management-hexagonal

Demo for hexagonal architecture

## Shell

```sh
nix-shell --run bash ./app.nix

mvn -N wrapper:wrapper -Dmaven=3.9.11

./mvnw -f ./task-management-backend/pom.xml -pl entrypoint spring-boot:run
```
