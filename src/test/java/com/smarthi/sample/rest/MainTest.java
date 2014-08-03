package com.smarthi.sample.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
  public void testCreate() {
    Sample sample = new Sample("Suneel", "Dulles", "VA");
    Entity<Sample> entity = Entity.entity(sample, MediaType.APPLICATION_JSON_TYPE);
    Response response = target.path("myresource").request().post(entity);
    assertEquals(200, response.getStatus());
  }

  @Test
  public void testMyResource() {
    GenericType<Sample> sample = new GenericType<Sample>() { };
    Sample responseMsg = target.path("myresource/sample").path("Suneel").request().accept(MediaType.APPLICATION_JSON_TYPE).get(sample);
    assertEquals(responseMsg.getName(), "Suneel");
  }

  @Test
  public void testSamplesList() {
    GenericType<List<Sample>> list = new GenericType<List<Sample>>() {
    };
    List<Sample> sampleList = target.path("myresource/sampleList").request().accept(MediaType.APPLICATION_JSON_TYPE).get(list);
    assertEquals(1, sampleList.size());
  }

  @Test
  public void testSayHello() {
    String responseMsg = target.path("myresource/hello").request().get(String.class);
    assertEquals("Hello", responseMsg);
  }

}
