package com.pk.webservice.learn.messanger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.pk.webservice.learn.exception.DataNotFoundException;
import com.pk.webservice.learn.messanger.database.DatabaseClass;
import com.pk.webservice.learn.messanger.model.Message;

public class MessageService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();

	public MessageService() {
		messages.put(1L, new Message(1L, "Hellow World", "Punit"));
		messages.put(2L, new Message(2L, "Hellow Jersey", "Punit"));
	}

	public List<Message> getMessageForYear(int year) {
		List<Message> messagesForYear = new ArrayList<Message>();
		Calendar cal = Calendar.getInstance();
		for (Message message : messages.values()) {
			cal.setTime(message.getCreated());
			if (cal.get(Calendar.YEAR) == year) {
				messagesForYear.add(message);
			}
		}
		return messagesForYear;
	}

	public List<Message> getMessageForPagination(int start, int size) {
		List<Message> messageForPagination = new ArrayList<Message>(messages.values());
		if(messageForPagination.size()<(start+size)) {
			return new ArrayList<Message>();
		}else {
		return messageForPagination.subList(start, start+size);
		}
	}

	public List<Message> getAllMessages() {
		return new ArrayList<Message>(messages.values());
	}

	public Message getMessage(long id) {
		Message message= messages.get(id);
		if(message ==  null) {
			throw new DataNotFoundException("The Message Id "+id+" not found.");
		}
		return message;
	}

	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}

	public Message updateMessage(Message message) {
		if (message.getId() <= 0) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}

	public Message removeMessage(long id) {
		return messages.remove(id);
	}
}
