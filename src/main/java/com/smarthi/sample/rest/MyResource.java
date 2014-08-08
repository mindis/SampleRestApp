package com.smarthi.sample.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// The Java class will be hosted at the URI path "/myresource"
@Path("/myresource")
public class MyResource {

  private static Map<Integer, Sample> sampleMap = new HashMap<>();

  @GET
  @Path("/sample/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Sample getIt(@PathParam("id") int id) {
    return sampleMap.get(id);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/sampleList")
  public Collection<Sample> getSamplesList() {
    return sampleMap.values();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createSample(final Sample aSample) {
    sampleMap.put(aSample.getName(), aSample);
    return Response.ok().build();
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/hello")
  public String sayHello() {
    return "Hello";
  }

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/sample/{id}")
  public Response removeSample(@PathParam("id") int id) {
    sampleMap.remove(id);
    System.out.println(sampleMap.toString());
    return Response.ok().build();
  }
}
