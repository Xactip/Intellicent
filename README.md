## Network
* `docker network create intellicent-network`

## Keycloak containers:
### keycloak-postgres
* `docker run -d --name keycloak-postgres --network intellicent-network -p 5432:5432 -v keycloak-postgres-data:/var/lib/postgresql/data -e POSTGRES_DB=keycloak_db -e POSTGRES_USER=keycloak_user -e POSTGRES_PASSWORD=keycloak_pass postgres:15.2`
### keycloak-app:
(note: on prod SSL must be enabled) 
* `docker run -d --name keycloak-app --network intellicent-network -p 8099:8099 -e KC_HTTP_PORT=8099 -e KC_HOSTNAME_STRICT=false -e KC_HOSTNAME_STRICT_HTTPS=false -e KC_HTTP_ENABLED=true -e KC_DB=postgres -e KC_DB_URL=jdbc:postgresql://keycloak-postgres/keycloak_db -e KC_DB_URL_PORT=5432 -e KC_DB_USERNAME=keycloak_user -e KC_DB_PASSWORD=keycloak_pass -e KC_DB_SCHEMA=public -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:21.0 start`

## Mongo containers:
### knowledgebase-mongo
* `docker run -d --name knowledgebase-mongo --network intellicent-network -p 27017:27017 -v knowledgebase-mongo-data:/data/db -v knowledgebase-mongo-config:/data/configdb mongo:5.0.15 -port 27017`
### usermanagement-mongo
* `docker run -d --name usermanagement-mongo --network intellicent-network -p 27018:27018 -v usermanagement-mongo-data:/data/db -v usermanagement-mongo-config:/data/configdb mongo:5.0.15 -port 27018`

## Spring Boot App Containers:
### knowledgebase-app:
* `docker build --force-rm -t knowledgebase-app:1.0 .`
* `docker run -d --network intellicent-network -p 8080:8080 --name knowledgebase-app knowledgebase-app:1.0`
### usermanagement-app:
* `docker build --force-rm -t usermanagement-app:1.0 .`
* `docker run -d --network intellicent-network -p 8081:8081 --name usermanagement-app usermanagement-app:1.0`
