package com.smarthi.sample.rest;

import org.glassfish.grizzly.http.server.HttpServer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RestTest {

  private static final int MAX_THREADS_IN_POOL = 10;

  private HttpServer server;
  private WebTarget target;
  private ExecutorService executorService;

  public static void main(String[] args) throws Exception {
    RestTest restTest = new RestTest();
    restTest.init();
    restTest.testPost();
    restTest.stop();
  }

  private void init() {
    // start the server
    server = Main.startServer();
    // create the client
    Client c = ClientBuilder.newClient();
    target = c.target(Main.BASE_URI);
    executorService = Executors.newFixedThreadPool(MAX_THREADS_IN_POOL);
  }

  public void testPost() throws InterruptedException {
    List<ResponseCallable> responseList = new ArrayList<>();
    for (int i = 0; i < MAX_THREADS_IN_POOL; i++) {
      responseList.add(new ResponseCallable(target, new Sample(i, "Suneel " + i, "Delhi")));
    }
    long startTime = System.nanoTime();
    this.executorService.invokeAll(responseList);
    long stopTime = System.nanoTime();
    System.out.println((stopTime - startTime) / 1.0e9);

    GenericType<List<Sample>> list = new GenericType<List<Sample>>() {
    };

    startTime = System.nanoTime();
    List<Sample> sampleList = target.path("myresource/sampleList").request().accept(MediaType.APPLICATION_JSON_TYPE).get(list);
    System.out.println(sampleList.toString());
    stopTime = System.nanoTime();
    System.out.println((stopTime - startTime) / 1.0e9);

    GenericType<Sample> sample = new GenericType<Sample>() { };
    startTime = System.nanoTime();
    Sample responseMsg = target.path("myresource/sample").path("6").request().accept(MediaType.APPLICATION_JSON_TYPE).get(sample);
    System.out.println(responseMsg.toString());
    stopTime = System.nanoTime();
    System.out.println((stopTime - startTime) / 1.0e9);
  }

  public void stop() {
    try {
      this.executorService.shutdown();
      if (!this.executorService.awaitTermination(60, TimeUnit.SECONDS)) {
        System.out.println("Threadpool timed out on await termination - jobs still running!");
      }
    } catch (InterruptedException e) {
      // do nothing
    }
    server.shutdown();
  }

}
