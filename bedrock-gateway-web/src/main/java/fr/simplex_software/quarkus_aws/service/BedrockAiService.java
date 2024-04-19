package fr.simplex_software.quarkus_aws.service;

import fr.simplex_software.quarkus_aws.model.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import org.eclipse.microprofile.rest.client.inject.*;

@ApplicationScoped
public class BedrockAiService
{
  @Inject
  @RestClient
  BedrockAiEndpoint bedrockAiEndpoint;

  public String mistralFm (String prompt, float temperature, float topP, int length)
  {
    return bedrockAiEndpoint.callMistralFm(new BedrockAiInputParam(prompt, temperature, topP, length)).readEntity(String.class);
  }

  public String titanFm (String prompt, float temperature, float topP, int length)
  {
    return bedrockAiEndpoint.callTitanFm(new BedrockAiInputParam(prompt, temperature, topP, length)).readEntity(String.class);
  }
}
