package com.smarthi.sample.rest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.Callable;

public class ResponsePostCallable implements Callable<Response> {

  private WebTarget webTarget;
  private Sample sample;

  public ResponsePostCallable(WebTarget target, Sample sample) {
    this.webTarget = target;
    this.sample = sample;
  }

  @Override
  public Response call() throws Exception {
    Entity<Sample> entity = Entity.entity(sample, MediaType.APPLICATION_JSON_TYPE);
    Response response = webTarget.path("myresource").request().post(entity);
    System.out.println("Response from create  = " + response.getStatusInfo().getStatusCode());
    return response;
  }
}
