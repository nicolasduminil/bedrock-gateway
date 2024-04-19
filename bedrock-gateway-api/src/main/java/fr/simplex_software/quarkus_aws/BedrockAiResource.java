package fr.simplex_software.quarkus_aws;

import fr.simplex_software.quarkus_aws.model.*;
import jakarta.json.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.*;
import org.jboss.resteasy.annotations.providers.multipart.*;
import org.slf4j.*;
import software.amazon.awssdk.core.*;
import software.amazon.awssdk.regions.*;
import software.amazon.awssdk.services.bedrockruntime.*;
import software.amazon.awssdk.services.bedrockruntime.model.*;

import java.io.*;
import java.util.concurrent.*;

@Path("/bedrock")
public class BedrockAiResource
{
  private static final String TITAN_MODEL_ID = "amazon.titan-text-express-v1";
  private static final String MISTRAL_MODEL_ID = "mistral.mistral-7b-instruct-v0:2";
  private static final String MISTRAL_FMT = "<s>[INST] %s [/INST]";

  private final BedrockRuntimeAsyncClient client = BedrockRuntimeAsyncClient.builder().region(Region.EU_WEST_3).build();

  private static Logger log = LoggerFactory.getLogger(BedrockAiResource.class);

  @POST
  @Path("mistral")
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public String withMistral(@MultipartForm InputStream file) throws Exception
  {
    return invoke(MISTRAL_MODEL_ID, getMistralPayload(String.format(MISTRAL_FMT, SdkBytes.fromInputStream(file)
      .asUtf8String()), 0.5f, 0.9f, 200)).getJsonArray("outputs").get(0).asJsonObject().getString("text");
  }

  @POST
  @Path("titan")
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public String withTitan(@MultipartForm InputStream file) throws Exception
  {
    return invoke(TITAN_MODEL_ID, getTitanPayload(SdkBytes.fromInputStream(file).asUtf8String(), .0f, 0.9f,
      2048)).getJsonArray("results").get(0).asJsonObject().getString("outputText");
  }

  @POST
  @Path("mistral2")
  public Response callMistral (BedrockAiInputParam bedrockAiInputParam) throws Exception
  {
    return Response.accepted(invoke(MISTRAL_MODEL_ID, getMistralPayload(bedrockAiInputParam.getPrompt(),
      bedrockAiInputParam.getTemperature(), bedrockAiInputParam.getTopP(), bedrockAiInputParam.getMaxTokens()))
      .getJsonArray("outputs").get(0).asJsonObject().getString("text")).build();
  }

  @POST
  @Path("titan2")
  public Response callTitan (BedrockAiInputParam bedrockAiInputParam) throws Exception
  {
    return Response.accepted(invoke(TITAN_MODEL_ID, getTitanPayload(bedrockAiInputParam.getPrompt(),
      bedrockAiInputParam.getTemperature(), bedrockAiInputParam.getTopP(), bedrockAiInputParam.getMaxTokens()))
      .getJsonArray("results").get(0).asJsonObject().getString("outputText")).build();
  }

  private String getMistralPayload (String prompt, Float temperature, Float topp, Integer length)
  {
    return Json.createObjectBuilder().add("prompt", prompt).add("max_tokens", length)
      .add("temperature", temperature).add("top_p", topp).build().toString();
  }

  private String getTitanPayload (String prompt, Float temperature, Float topp, Integer length)
  {
    return Json.createObjectBuilder().add("inputText", prompt).add("textGenerationConfig", Json.createObjectBuilder()
      .add("maxTokenCount", length).add("temperature", temperature).add("topP", topp)).build().toString();
  }

  private JsonObject invoke(String modelId, String payload) throws Exception
  {
    InvokeModelRequest request = InvokeModelRequest.builder()
      .body(SdkBytes.fromUtf8String(payload))
      .modelId(modelId)
      .contentType("application/json")
      .accept("application/json")
      .build();
    CompletableFuture<InvokeModelResponse> completableFuture = client.invokeModel(request)
      .whenComplete((response, exception) ->
      {
        if (exception != null)
          log.error("### BedrockAiResource.inoke(): Model invocation failed: {}", exception);
      });
    try (JsonReader jsonReader = Json.createReader(new StringReader(completableFuture.get().body().asUtf8String())))
    {
      return jsonReader.readObject();
    }
  }
}
