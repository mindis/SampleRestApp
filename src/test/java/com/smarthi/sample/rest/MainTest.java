package com.smarthi.sample.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MainTest {

  private HttpServer server;
  private WebTarget target;

  @Before
  public void setUp() throws Exception {
    // start the server
    server = Main.startServer();
    // create the client
    Client c = ClientBuilder.newClient();
    target = c.target(Main.BASE_URI);
  }

  @After
  public void tearDown() throws Exception {
    server.shutdown();
  }

  @Test
  public void testMyResource() {
    Sample responseMsg = target.path("myresource/sample").request().accept(MediaType.APPLICATION_JSON_TYPE).get(Sample.class);
    assertEquals(responseMsg.getName(), "Suneel");
  }

  @Test
  public void testSamplesList() {
    GenericType<List<Sample>> list = new GenericType<List<Sample>>() {
    };
    List<Sample> sampleList = target.path("myresource/sampleList").request().accept(MediaType.APPLICATION_JSON_TYPE).get(list);
    assertEquals(sampleList.size(), 2);
  }

  @Test
  public void testSayHello() {
    String responseMsg = target.path("myresource/hello").request().get(String.class);
    assertEquals("Hello", responseMsg);
  }
}
