package fr.eni.projet_encheres.exception;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> messages;
	
	public BusinessException() {
		super();
		this.messages = new ArrayList<String>();
	}
	
	public void add(String message) {
		System.out.println("J'enregistre le message" + message);
		this.messages.add(message);
	}

	public List<String> getMessages() {
		System.out.println("Le nombre d'éléments est : " + messages.size());
		System.out.println();
		return messages;
	}


	
}
