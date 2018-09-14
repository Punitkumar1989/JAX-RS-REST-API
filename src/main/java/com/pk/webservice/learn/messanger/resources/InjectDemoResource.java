package com.pk.webservice.learn.messanger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {

	@GET
	@Path("annotations")
	public String getParamUsingAnnotation(@MatrixParam("param") String param,
											@HeaderParam("headerParam") int headerParam,
											@CookieParam("name") String name) {
		return "Matrix Param is - "+ param + " and Header Param is - " + headerParam +" and Cookie Param is - "+ name;
	}
	
	@GET
	@Path("context")
	public String getParamUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders headers) {
		return "Path : "+uriInfo.getAbsolutePath().toString()+" Cookie : "+ headers.getCookies().toString();
	}
	
}
