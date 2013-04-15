package com.cta.web;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.RestAssured;

public class EmbededServerTest {

    protected static BdManagerEmbededServer embeddedServer = new BdManagerEmbededServer();

    @BeforeClass
    public static void startEmbeddedServer() {
        embeddedServer.execute(new String[] {}, false);
        RestAssured.port = BdManagerEmbededServer.DEFAULT_WEB_PORT;
    }

    @AfterClass
    public static void stopEmbeddedServer() {
        embeddedServer.stop();
    }

    @Test
    public void testEmbedServerStart() {
        String responseAsString = given()
        .expect()
        .response().statusCode(200)
        .when().get("/test/echo/localized?lang=fr").asString();
        
        assertEquals("\"Je suis Vivant !\"", responseAsString);
    }
}
