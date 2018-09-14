package com.pk.webservice.learn.messanger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.pk.webservice.learn.messanger.database.DatabaseClass;
import com.pk.webservice.learn.messanger.model.Comment;
import com.pk.webservice.learn.messanger.model.ErrorMessage;
import com.pk.webservice.learn.messanger.model.Message;

public class CommentService {
	private Map<Long, Message> messages = DatabaseClass.getMessages();

//	public CommentService() {
//		messages.put(1L, new Message(1L, "Hellow World", "Punit"));
//		messages.put(2L, new Message(1L, "Hellow Jersey", "Punit"));
//		messages.put(1L, new Message(1L, "Hellow World", "Punit"));
//		messages.put(2L, new Message(1L, "Hellow Jersey", "Punit"));
//	}

	public List<Comment> getAllComments(long messageId) {
		Map <Long,Comment> comment = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comment.values());
	}

	public Comment getComment(long messageId, long commentId) {
		Message message = messages.get(messageId);
		ErrorMessage errorMessage = new ErrorMessage("Not Found", 404, " HTTP/1.1 documentation");
		Response response = Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
		if(message == null) {
			throw new WebApplicationException(response);
		}
		Comment comment = messages.get(messageId).getComments().get(commentId);
		if(comment == null) {
			throw new NotFoundException(response);
		}
		return comment;
	}

	public Comment addComment(long messageId,Comment comment) {
		Map <Long, Comment> comments= messages.get(messageId).getComments();
		comment.setId(messages.size() + 1);
		comments.put(comment.getId(), comment);
		return comment;
	}

	public Comment updateComment(long messageId,Comment comment) {
		Map <Long, Comment> comments= messages.get(messageId).getComments();
		if (comment.getId() <= 0) {
			return null;
		}
		comments.put(comment.getId(), comment);
		return comment;
	}

	public Comment removeComment(long messageId, long commentId) {
		Map <Long, Comment> comments= messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
}
