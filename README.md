### Keycloak container:
* **keycloak:** `TBD`

### Mongo containers:
* **knowledgebase-mongo:** `docker run -d -p 27017:27017 --name knowledgebase-mongo -v knowledgebase-mongo-data:/data/db -v knowledgebase-mongo-config:/data/configdb mongo:5.0.15`
* **usermanagement-mongo:** `docker run -d -p 27018:27017 --name usermanagement-mongo -v usermanagement-mongo-data:/data/db -v usermanagement-mongo-config:/data/configdb mongo:5.0.15`
