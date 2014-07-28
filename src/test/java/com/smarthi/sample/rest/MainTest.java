
package com.smarthi.sample.rest;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.header.MediaTypes;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import junit.framework.TestCase;

import javax.ws.rs.core.MediaType;
import java.util.List;


public class MainTest extends TestCase {

  private SelectorThread threadSelector;

  private WebResource webResource;

  public MainTest(String testName) {
    super(testName);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    threadSelector = Main.startServer();

    ClientConfig clientConfig = new DefaultClientConfig();
    clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
    Client c = Client.create(clientConfig);

    webResource = c.resource(Main.BASE_URI);
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();

    threadSelector.stopEndpoint();
  }

  public void testMyResource() {
    Sample responseMsg = webResource.path("myresource/sample").accept(MediaType.APPLICATION_JSON_TYPE).get(Sample.class);
    assertEquals(responseMsg.getName(), "Suneel");
  }

  public void testSamplesList() {
    GenericType<List<Sample>> list = new GenericType<List<Sample>>() {
    };
    List<Sample> sampleList = webResource.path("myresource/sampleList").accept(MediaType.APPLICATION_JSON_TYPE).get(list);
    assertEquals(sampleList.size(), 2);
  }

  public void testSayHello() {
    String responseMsg = webResource.path("myresource/hello").get(String.class);
    assertEquals("Hello", responseMsg);
  }

  /**
   * Test if a WADL document is available at the relative path
   * "application.wadl".
   */
  public void testApplicationWadl() {
    String serviceWadl = webResource.path("application.wadl").
        accept(MediaTypes.WADL).get(String.class);

    assertTrue(serviceWadl.length() > 0);
  }
}
