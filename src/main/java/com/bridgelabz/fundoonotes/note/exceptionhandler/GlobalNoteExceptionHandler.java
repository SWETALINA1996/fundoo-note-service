package com.bridgelabz.fundoonotes.note.exceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.fundoonotes.note.exceptions.CreationException;
import com.bridgelabz.fundoonotes.note.exceptions.DateNotFoundException;
import com.bridgelabz.fundoonotes.note.exceptions.LabelCreationException;
import com.bridgelabz.fundoonotes.note.exceptions.LabelNotfoundException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteNotTrashedException;
import com.bridgelabz.fundoonotes.note.exceptions.RemainderSetException;
import com.bridgelabz.fundoonotes.note.exceptions.UnAuthorisedAccess;
import com.bridgelabz.fundoonotes.note.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonotes.note.models.Response;

@ControllerAdvice
public class GlobalNoteExceptionHandler {

	public static final Logger logger = LoggerFactory.getLogger(GlobalNoteExceptionHandler.class);

	@ExceptionHandler(CreationException.class)
	public ResponseEntity<Response> handleRegistrationException(CreationException e) {

		logger.error(e.getMessage());

		Response response = new Response();
		response.setMessage(e.getMessage());
		response.setStatus(-10);
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoteNotFoundException.class)
	public ResponseEntity<Response> handleLoginException(NoteNotFoundException e) {

		logger.error(e.getMessage());

		Response response = new Response();
		response.setMessage(e.getMessage());
		response.setStatus(-11);
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UnAuthorisedAccess.class)
	public ResponseEntity<Response> handleActivationException(UnAuthorisedAccess e) {

		logger.error(e.getMessage());

		Response response = new Response();
		response.setMessage(e.getMessage());
		response.setStatus(-12);
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LabelNotfoundException.class)
	public ResponseEntity<Response> labelNotFoundException(LabelNotfoundException e) {

		logger.error(e.getMessage());

		Response response = new Response();
		response.setMessage(e.getMessage());
		response.setStatus(-13);
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Response> userNotFoundException(UserNotFoundException e) {

		logger.error(e.getMessage());

		Response response = new Response();
		response.setMessage(e.getMessage());
		response.setStatus(-14);
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DateNotFoundException.class)
	public ResponseEntity<Response> dateNotFoundException(DateNotFoundException e) {

		logger.error(e.getMessage());

		Response response = new Response();
		response.setMessage(e.getMessage());
		response.setStatus(-15);
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LabelCreationException.class)
	public ResponseEntity<Response> labelCreationException(LabelCreationException e) {

		logger.error(e.getMessage());

		Response response = new Response();
		response.setMessage(e.getMessage());
		response.setStatus(-16);
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RemainderSetException.class)
	public ResponseEntity<Response> reminderException(RemainderSetException e) {

		logger.error(e.getMessage());

		Response response = new Response();
		response.setMessage(e.getMessage());
		response.setStatus(-17);
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoteNotTrashedException.class)
	public ResponseEntity<Response> noteNotTrashedException(NoteNotTrashedException e) {

		logger.error(e.getMessage());

		Response response = new Response();
		response.setMessage(e.getMessage());
		response.setStatus(-18);
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleGenericExceptions(Exception e) {

		logger.error(e.toString());

		Response response = new Response();
		response.setMessage("Something went wrong");
		response.setStatus(-1);
		return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
