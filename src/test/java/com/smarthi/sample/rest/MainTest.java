package com.smarthi.sample.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.common.collect.Iterables;
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
    Sample sample = new Sample(1, "Dulles", "VA");
    long startTime = System.currentTimeMillis();
    System.out.println("Start Time: " + startTime);
    Entity<Sample> entity = Entity.entity(sample, MediaType.APPLICATION_JSON_TYPE);

    Response response = target.path("myresource").request().post(entity);
    long stopTime = System.currentTimeMillis();
    System.out.println("Stop time: " + stopTime);
    System.out.println((stopTime - startTime));
    assertEquals(200, response.getStatus());
  }

  @Test
  public void testMyResource() {
    GenericType<Sample> sample = new GenericType<Sample>() { };
    long startTime = System.currentTimeMillis();
    System.out.println("Start Time: " + startTime);
    Sample responseMsg = target.path("myresource/sample").path("1").request().accept(MediaType.APPLICATION_JSON_TYPE).get(sample);
    long stopTime = System.currentTimeMillis();
    System.out.println("Stop time: " + stopTime);
    System.out.println((stopTime - startTime));
    assertEquals(responseMsg.getName(), 1);
  }

  @Test
  public void testSamplesList() {
    GenericType<List<Sample>> list = new GenericType<List<Sample>>() {
    };
    long startTime = System.currentTimeMillis();
    System.out.println("Start Time: " + startTime);
    List<Sample> sampleList = target.path("myresource/sampleList").request().accept(MediaType.APPLICATION_JSON_TYPE).get(list);
    long stopTime = System.currentTimeMillis();
    System.out.println("Stop time: " + stopTime);
    System.out.println((stopTime - startTime));
    assertEquals(1, sampleList.size());
  }

	@Test
	public void testTest() {
		GenericType<Iterable<Integer>> col = new GenericType<Iterable<Integer>>() {};
		Iterable<Integer> list = target.path("myresource/test").request().accept(MediaType.APPLICATION_JSON_TYPE).get(col);
		System.out.println(list);
		assertEquals(3, Iterables.size(list));
	}

  @Test
  public void testSayHello() {
    String responseMsg = target.path("myresource/hello").request().get(String.class);
    assertEquals("Hello", responseMsg);
  }

  @Test
  public void testSomething() {
    String response = target.path("myresource/what").path("b").queryParam("item", new String[]{"I am","A","good","boy"}).request()
        .accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);
    System.out.println(response);

  }

}
