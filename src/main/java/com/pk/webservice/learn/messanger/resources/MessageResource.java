package com.pk.webservice.learn.messanger.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.pk.webservice.learn.messanger.model.Message;
import com.pk.webservice.learn.messanger.resources.beans.MessageFilterBean;
import com.pk.webservice.learn.messanger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(value = {MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
public class MessageResource {
	
	MessageService messageService = new MessageService();
	
	@GET
	public List<Message> getAllMessages(@BeanParam MessageFilterBean messageFilterBean) {
//		if(messageFilterBean.getYear()>0) {
//			return messageService.getMessageForYear(messageFilterBean.getYear());
//		}
//		if(messageFilterBean.getStart() >= 0 && messageFilterBean.getSize() >= 0) {
//			return messageService.getMessageForPagination(messageFilterBean.getStart(), messageFilterBean.getSize());
//		}
		return messageService.getAllMessages();
	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		Message message = messageService.getMessage(id);
		message.addLink(getUriPathForSelf(uriInfo, message), "self");
		message.addLink(getUriPathForProfile(uriInfo, message), "profile");
		message.addLink(getUriPathForComments(uriInfo, message), "comment");
		return message;
	}

	private String getUriPathForComments(UriInfo uriInfo, Message message) {
		URI uri= uriInfo.getBaseUriBuilder()			//"http://localhost:8085/messanger/webapi/"
				.path(MessageResource.class)			//   /profiles
				.path(MessageResource.class, "getComments")				//  /Punit
				.resolveTemplate("messageId", message.getId())
				.build();
		return uri.toString();
	}

	private String getUriPathForProfile(UriInfo uriInfo, Message message) {
		URI uri= uriInfo.getBaseUriBuilder()			//"http://localhost:8085/messanger/webapi/"
				.path(ProfileResource.class)			//   /profiles
				.path(message.getAuthor())				//  /Punit
				.build();
		return uri.toString();
	}

	private String getUriPathForSelf(UriInfo uriInfo, Message message) {
		String selfPath = uriInfo.getBaseUriBuilder()    //"http://localhost:8085/messanger/webapi/"
				.path(MessageResource.class)			//   /messages
				.path(Long.toString(message.getId()))		//    /2
				.build()
				.toString();
		return selfPath;
	}
	
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException  {
		Message newMessage = messageService.addMessage(message);
//		return Response.status(Status.CREATED)
//						.entity(newMessage)
//						.build();
		
		return Response.created(new URI(uriInfo.getAbsolutePath().toString()+newMessage.getId()))
				.entity(newMessage)
				.build();
		//return messageService.addMessage(message);
	}
	@PUT
	public Message updateMessage(Message message) {
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId")long id) {
		messageService.removeMessage(id);
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getComments() {
		return new CommentResource();
	}
	
	
}
