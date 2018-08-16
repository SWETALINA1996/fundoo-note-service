package com.bridgelabz.fundoonotes.note.exceptions;

public class NoteNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public NoteNotFoundException(String message) {
		super(message);
	}
}
