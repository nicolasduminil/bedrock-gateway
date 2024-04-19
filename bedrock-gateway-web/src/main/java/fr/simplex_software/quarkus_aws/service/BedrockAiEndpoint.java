package fr.simplex_software.quarkus_aws.service;

import fr.simplex_software.quarkus_aws.model.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.rest.client.inject.*;

@RegisterRestClient
@Path("/bedrock")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public interface BedrockAiEndpoint
{
  @POST
  @Path("mistral2")
  Response callMistralFm (BedrockAiInputParam bedrockAiInputParam);
  @POST
  @Path("titan2")
  Response callTitanFm (BedrockAiInputParam bedrockAiInputParam);
}
