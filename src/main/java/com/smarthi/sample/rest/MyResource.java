package com.smarthi.sample.rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.carrotsearch.hppc.IntCollection;
import com.carrotsearch.hppc.IntObjectMap;
import com.carrotsearch.hppc.IntObjectOpenHashMap;
import com.carrotsearch.hppc.cursors.IntCursor;
import com.google.common.base.Function;
import com.google.common.collect.Iterators;

// The Java class will be hosted at the URI path "/myresource"
@Path("/myresource")
public class MyResource {

	private static Map<Integer, Sample> sampleMap = new ConcurrentHashMap<>();
	private static IntObjectMap<String> X = new IntObjectOpenHashMap<>();

	@Context
	private ServletContext context;

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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/test")
	public List<Integer> sayTest() {
		X.put(1, "Suneel");
		X.put(2, "Holy Mackerel");
		X.put(3, "Holy Cow");

		final Iterator<Integer> transformed = Iterators.transform(X.keys().iterator(),
				new Function<IntCursor, Integer>() {
					@Override
					public Integer apply(IntCursor input) {
						return input.value;
					}
				});
		List<Integer> list = new ArrayList<>();
		Iterators.addAll(list, transformed);
		return list;
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
