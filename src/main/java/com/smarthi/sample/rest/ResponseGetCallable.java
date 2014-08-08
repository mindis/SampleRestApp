package com.smarthi.sample.rest;

import java.util.concurrent.Callable;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

public class ResponseGetCallable implements Callable<Sample> {

  private WebTarget webTarget;
  private int id;

  public ResponseGetCallable(WebTarget target, int id) {
    this.webTarget = target;
    this.id = id;
  }

  @Override
  public Sample call() throws Exception {
    GenericType<Sample> sample = new GenericType<Sample>() {};
    Sample responseMsg = this.webTarget.path("myresource/sample").path(String.valueOf(id)).request().accept(MediaType.APPLICATION_JSON_TYPE).get(sample);
    System.out.println("Sample = " + responseMsg.toString());
    return responseMsg;
  }
}
