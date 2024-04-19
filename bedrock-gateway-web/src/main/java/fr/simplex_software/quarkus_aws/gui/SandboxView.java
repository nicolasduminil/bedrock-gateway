package fr.simplex_software.quarkus_aws.gui;

import fr.simplex_software.quarkus_aws.service.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;

@Named
@RequestScoped
public class SandboxView
{
  private float temperature;
  private float topP;
  private int responseLength;
  private String response;
  private String prompt;

  @Inject
  BedrockAiService bedrockAiService;

  public SandboxView()
  {
  }

  public SandboxView(float temperature, float topP, int responseLength)
  {
    this.temperature = temperature;
    this.topP = topP;
    this.responseLength = responseLength;
  }

  public float getTemperature()
  {
    return temperature;
  }

  public void setTemperature(float temperature)
  {
    this.temperature = temperature;
  }

  public float getTopP()
  {
    return topP;
  }

  public void setTopP(float topP)
  {
    this.topP = topP;
  }

  public int getResponseLength()
  {
    return responseLength;
  }

  public void setResponseLength(int responseLength)
  {
    this.responseLength = responseLength;
  }

  public void runTitan()
  {
     setResponse(bedrockAiService.titanFm(prompt, temperature, topP, responseLength));
  }

  public void runMistral()
  {
    setResponse(bedrockAiService.mistralFm(prompt, temperature, topP, responseLength));
  }

  public String getResponse()
  {
    return response;
  }

  public void setResponse (String resposne)
  {
    this.response = resposne;
  }

  public String getPrompt()
  {
    return prompt;
  }

  public void setPrompt(String prompt)
  {
    this.prompt = prompt;
  }
}
