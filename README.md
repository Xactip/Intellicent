# App deployment notes
###### _All commands written related to the root app folder:_`cd <path_to_root_folder>/Intellicent`

- [1. Run in Docker manually](#1-how-to-run-in-docker-manually)
  - [1.1. Initial steps](#11-initial-steps)
  - [1.2. Keycloak containers](#12-keycloak-containers)
  - [1.3. Mongo containers](#13-mongo-containers)
  - [1.4. Spring Boot App Containers](#14-spring-boot-app-containers)
- [2. Deploy in Kubernetes (using Minikube)](#2-deploy-in-kubernetes-using-minikube)
  - [2.1. Initial steps](#21-initial-steps)
  - [2.2. Deploy service](#22-deploy-service)
  - [2.3. Debugging commands](#23-debugging-commands)
  - [2.4. Cleansing](#24-cleansing)

----------------------------------

## 1. How to run in Docker manually

### 1.1. Initial steps
* Install docker
* Register in [Docker Hub](https://hub.docker.com/)
* Create network: `docker network create intellicent-network`

### 1.2. Keycloak containers
#### 1.2.1. keycloak-postgres
* `docker run -d --name keycloak-postgres --network intellicent-network -p 5432:5432 -v keycloak-postgres-data:/var/lib/postgresql/data -e POSTGRES_DB=keycloak_db -e POSTGRES_USER=keycloak_user -e POSTGRES_PASSWORD=keycloak_pass postgres:15.2`
#### 1.2.2. keycloak-app
###### _on prod SSL must be enabled_
* `docker run -d --name keycloak-app --network intellicent-network -p 8099:8099 -e KC_HTTP_PORT=8099 -e KC_HOSTNAME_STRICT=false -e KC_HOSTNAME_STRICT_HTTPS=false -e KC_HTTP_ENABLED=true -e KC_DB=postgres -e KC_DB_URL=jdbc:postgresql://keycloak-postgres/keycloak_db -e KC_DB_URL_PORT=5432 -e KC_DB_USERNAME=keycloak_user -e KC_DB_PASSWORD=keycloak_pass -e KC_DB_SCHEMA=public -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:21.0 start`

### 1.3. Mongo containers
#### 1.3.1. knowledgebase-mongo
* `docker run -d --name knowledgebase-mongo --network intellicent-network -p 27017:27017 -v knowledgebase-mongo-data:/data/db -v knowledgebase-mongo-config:/data/configdb mongo:5.0.15 -port 27017`
#### 1.3.2. usermanagement-mongo
* `docker run -d --name usermanagement-mongo --network intellicent-network -p 27018:27018 -v usermanagement-mongo-data:/data/db -v usermanagement-mongo-config:/data/configdb mongo:5.0.15 -port 27018`

### 1.4. Spring Boot App Containers
#### 1.4.1. intellicent-knowledgebase-app
* `docker build --force-rm -t intellicent-knowledgebase-app:1.0 .\src\knowledgebaseservice\ `
* `docker push xactip/intellicent-knowledgebase-app:1.0`
* `docker run -d --network intellicent-network -p 8080:8080 --name knowledgebase-app -e MONGO_HOST=knowledgebase-mongo:27017 -e KC_HOST=keycloak-app:8099 xactip/intellicent-knowledgebase-app:1.0`
#### 1.4.2. usermanagement-app
* `docker build --force-rm -t intellicent-usermanagement-app:1.0 .\src\usermanagementservice\ `
* `docker push xactip/intellicent-usermanagement-app:1.0`
* `docker run -d --network intellicent-network -p 8081:8081 --name usermanagement-app -e MONGO_HOST=usermanagement-mongo:27018 -e KC_HOST=keycloak-app:8099 xactip/intellicent-usermanagement-app:1.0`

## 2. Deploy in Kubernetes (using Minikube)

### 2.1. Initial steps
* Install kubectl: _{link}_
* Install minikube: _{link}_
* Start Minikube:  `minikube start --driver=docker`
* Push spring-boot-app containers to the [Docker Hub](https://hub.docker.com/) _(1.4. section)_

### 2.2. Deploy service:
#### 2.2.1. Apply kubernetes yaml files:
* `kubectl apply -f kube`
#### 2.2.2. Expose services:
* Create a route to all services deployed with type LoadBalancer: `minikube tunnel`  
**OR**  
* Get a URL to connect to a service `minikube service <service_name> --url`

### 2.3. Debugging commands:
* See minikube summary status:  
`minikube status`
* Get list of kubernetes resources:  
`kubectl get <pods/deployments/services/PersistentVolumeClaims/etc>`
* List kubernetes pod logs:  
`kubectl logs -f <pod_name>`
* Get kubernetes pod deployment details:  
`kubectl describe pod <pod_name>`

### 2.4. Cleansing:
* Delete specific resource by type and optionally by resource name:  
`kubectl delete <pods/deployments/services/PersistentVolumeClaims/etc> <optional_resource_name>`
* delete kubernetes node with everything associated in minikube:  
`minikube delete --all --purge`






