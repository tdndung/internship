package com.axonactive.training.sniffer;

import java.net.URI;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Tran Duong Ngoc Dung
 *
 */
@Stateless
@Path("/sniffers")
public class SnifferResource {

	@EJB
	SnifferService snifferService;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response add(Sniffer sniffer) {
		String code = snifferService.add(sniffer);
		return Response.created(URI.create("code=" + code)).build();
	}
	
	@GET 
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Sniffer get(@PathParam("id") Integer id) {
		return this.snifferService.find(id);
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List getAllSniffer() {
		return this.snifferService.getAllSniffer();
	}
	
	@DELETE
	@Path("/{code}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response delete(@PathParam("code") String code) {
		snifferService.delete(code);
		return Response.created(URI.create("code=" + code)).build();
	}
	
	@PUT
	@Consumes ({MediaType.APPLICATION_JSON})
	public Response update(Sniffer sniffer) {
		snifferService.update(sniffer);
		return Response.status(Response.Status.OK).build();
	}
}
