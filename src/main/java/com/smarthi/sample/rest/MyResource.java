
package com.smarthi.sample.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

// The Java class will be hosted at the URI path "/myresource"
@Path("/myresource")
public class MyResource {

    @GET
    @Path("/sample")
    @Produces(MediaType.APPLICATION_JSON)
    public Sample getIt() {

      return new Sample("Suneel", "Dulles", "VA");
    }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/sampleList")
  public List<Sample> getSamplesList() {

    Sample sample1 = new Sample("Suneel", "Dulles", "VA");
    Sample sample2 = new Sample("Suneel", "Bombay", "India");
    List<Sample> samplesList = new ArrayList<>();
    samplesList.add(sample1);
    samplesList.add(sample2);

    return samplesList;
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/hello")
  public String sayHello() {
    return "Hello";
  }
}
