package fr.simplex_software.quarkus_aws.tests;

import io.quarkus.test.junit.*;
import io.restassured.*;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;

@QuarkusIntegrationTest
public class SayHelloResourceIT
{
  @Test
  public void testJaxrs()
  {
    RestAssured.given()
      .header("Content-type", "text/plain")
      .when().get("/hello").then()
      .body(equalTo("hello jaxrs"));
  }
}
