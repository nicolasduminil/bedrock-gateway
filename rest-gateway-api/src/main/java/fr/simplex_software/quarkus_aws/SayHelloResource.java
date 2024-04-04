package fr.simplex_software.quarkus_aws;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/hello")
public class SayHelloResource
{
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes(MediaType.TEXT_PLAIN)
  public String hello()
  {
    return "hello jaxrs";
  }
}
