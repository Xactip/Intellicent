package com.xactip.intellicent.knowledgebaseservice.tc;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

public abstract class TestContainer {

    private static final KeycloakContainer KC_CONTAINER =
            new KeycloakContainer("quay.io/keycloak/keycloak:21.0").withRealmImportFile("keycloak/realm-export.json");
    private static final MongoDBContainer MONGO_CONTAINER =
            new MongoDBContainer("mongo:5.0.15").withExposedPorts(27017);

    @DynamicPropertySource
    private static void containersProperties(DynamicPropertyRegistry registry) {
        KC_CONTAINER.start();
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () -> KC_CONTAINER.getAuthServerUrl() + "realms/Intellicent");
        registry.add("spring.security.oauth2.client.provider.keycloak.issuer-uri", () -> KC_CONTAINER.getAuthServerUrl() + "realms/Intellicent");
        MONGO_CONTAINER.start();
        registry.add("spring.data.mongodb.uri", MONGO_CONTAINER::getReplicaSetUrl);
    }

}
