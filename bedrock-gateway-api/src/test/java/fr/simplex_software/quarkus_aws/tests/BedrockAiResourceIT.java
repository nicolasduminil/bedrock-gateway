package fr.simplex_software.quarkus_aws.tests;

import fr.simplex_software.quarkus_aws.model.*;
import io.quarkus.test.junit.*;
import io.restassured.*;
import jakarta.ws.rs.core.*;
import org.apache.http.*;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.assertj.core.api.Assertions.*;

@QuarkusIntegrationTest
public class BedrockAiResourceIT
{
  @Test
  public void testWithTitan()
  {
    String resp = RestAssured.given()
      .header("Content-type", MediaType.MULTIPART_FORM_DATA)
      .multiPart("file", new File(getClass().getResource("/test-with-titan.txt").getFile()))
      .when().post("/bedrock/titan").then().statusCode(HttpStatus.SC_OK).extract().body().asString();
    assertThat (resp).contains("Michel");
    assertThat(resp).contains("Nicolas");
    assertThat(resp).contains("Benoit");
    assertThat(resp).contains("James");
  }

  @Test
  public void testWithMistral()
  {
    String resp = RestAssured.given()
      .header("Content-type", MediaType.MULTIPART_FORM_DATA)
      .multiPart("file", new File(getClass().getResource("/test-with-mistral.txt").getFile()))
      .when().post("/bedrock/mistral").then().statusCode(HttpStatus.SC_OK).extract().body().asString();
    assertThat(resp).contains("find .");
  }

  @Test
  public void testWithTitan2()
  {
    RestAssured.given()
      .header("Content-type", MediaType.APPLICATION_JSON)
      .and().body(new BedrockAiInputParam("How are you ?", 0.0f, 0.5f, 200))
      .when().post("/bedrock/titan2").then().statusCode(HttpStatus.SC_ACCEPTED);
  }

  @Test
  public void testWithMistral2()
  {
    RestAssured.given()
      .header("Content-type", MediaType.APPLICATION_JSON)
      .and().body(new BedrockAiInputParam("How are you ?", 0.0f, 0.5f, 200))
      .when().post("/bedrock/mistral2").then().statusCode(HttpStatus.SC_ACCEPTED);
  }
}
