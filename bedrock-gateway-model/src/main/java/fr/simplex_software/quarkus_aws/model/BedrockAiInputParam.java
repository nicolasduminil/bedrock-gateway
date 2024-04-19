package fr.simplex_software.quarkus_aws.model;

public class BedrockAiInputParam
{
  private String prompt;
  private float temperature;
  private float topP;
  private int maxTokens;
  private String response;

  public BedrockAiInputParam()
  {
  }

  public BedrockAiInputParam(String prompt, float temperature, float topP, int maxTokens)
  {
    this.prompt = prompt;
    this.temperature = temperature;
    this.topP = topP;
    this.maxTokens = maxTokens;
  }

  public String getPrompt()
  {
    return prompt;
  }

  public void setPrompt(String prompt)
  {
    this.prompt = prompt;
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

  public int getMaxTokens()
  {
    return maxTokens;
  }

  public void setMaxTokens(int maxTokens)
  {
    this.maxTokens = maxTokens;
  }

  public String getResponse()
  {
    return response;
  }

  public void setResponse(String response)
  {
    this.response = response;
  }
}
