package com.bridgelabz.fundoonotes.note.controllers;


import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.note.exceptions.CreationException;
import com.bridgelabz.fundoonotes.note.exceptions.DateNotFoundException;
import com.bridgelabz.fundoonotes.note.exceptions.LinkNotFoundException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteNotTrashedException;
import com.bridgelabz.fundoonotes.note.exceptions.RemainderSetException;
import com.bridgelabz.fundoonotes.note.exceptions.UnAuthorisedAccess;
import com.bridgelabz.fundoonotes.note.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonotes.note.models.CreateNoteDTO;
import com.bridgelabz.fundoonotes.note.models.Note;
import com.bridgelabz.fundoonotes.note.models.Response;
import com.bridgelabz.fundoonotes.note.models.UpdateNoteDTO;
import com.bridgelabz.fundoonotes.note.models.ViewNoteDTO;
import com.bridgelabz.fundoonotes.note.services.NoteService;

@RestController
@RequestMapping("/notes")
public class NoteController {
	
	@Autowired
	private NoteService noteService;
	
	/************************************************************************************************************************************************************
	 * @param createNote
	 * @param token
	 * @return
	 * @throws CreationException
	 * @throws LinkNotFoundException 
	 ************************************************************************************************************************************************************/
	
	@RequestMapping(value = "/create-note", method = RequestMethod.POST)
	public ResponseEntity<ViewNoteDTO> create(@RequestBody CreateNoteDTO createNoteDto , HttpServletRequest req) throws CreationException, LinkNotFoundException{
		
		String userId = req.getHeader("userId");
		ViewNoteDTO viewNote = noteService.createNote(createNoteDto , userId);
		
		return new ResponseEntity<>(viewNote, HttpStatus.OK);
	}
	
	/*************************************************************************************************************************************************************
	 * @param updateNote
	 * @param token
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UserNotFoundException
	 * @throws UnAuthorisedAccess 
	 *************************************************************************************************************************************************************/
	
	@RequestMapping(value = "/update-note", method = RequestMethod.PUT)
	public ResponseEntity<Response> update(@RequestBody UpdateNoteDTO updateNote , HttpServletRequest req) throws NoteNotFoundException, UserNotFoundException, UnAuthorisedAccess{
		
		String userId = req.getHeader("userId");
		noteService.updateNote(updateNote, userId);
		Response dto = new Response();
		dto.setMessage("Successfully updated Note..");
		dto.setStatus(10);
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	/*************************************************************************************************************************************
	 * @param noteId
	 * @param token
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UserNotFoundException
	 * @throws UnAuthorisedAccess 
	 ************************************************************************************************************************************/
	@PutMapping(value = "/trash-and-restore-note/{noteId}/{trashorrestore}")
	public ResponseEntity<Response> trashNote(@PathVariable String noteId , @PathVariable boolean trashorrestore , HttpServletRequest req) throws NoteNotFoundException, UserNotFoundException, UnAuthorisedAccess {
		
		String userId = req.getHeader("userId");
		noteService.trashNoteAndRestore(noteId, userId , trashorrestore);
		
		Response dto = new Response();
		dto.setMessage("Successful");
		dto.setStatus(10);
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	/**
	 * @param req
	 * @param token
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UserNotFoundException
	 */
	@PutMapping(value = "/empty-trash")
	public ResponseEntity<Response> trashNote(HttpServletRequest req) throws UserNotFoundException {
		
		String userId = req.getHeader("userId");
		noteService.emptyTrash(userId);
		
		Response dto = new Response();
		dto.setMessage("Successfully trashed all notes");
		dto.setStatus(20);
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	/**
	 * @param req
	 * @param token
	 * @return
	 * @throws CreationException
	 * @throws UserNotFoundException 
	 */
	
	@RequestMapping(value = "/view-trash", method = RequestMethod.POST)
	public ResponseEntity<List<Note>> viewTrash(HttpServletRequest req ) throws CreationException, UserNotFoundException{
		
		String userId = req.getHeader("userId");
		List<Note> note = noteService.viewTrash(userId);
		
		return new ResponseEntity<>(note, HttpStatus.OK);
	}
	/***************************************************************************************************************************
	 * @param noteId
	 * @param token
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UserNotFoundException
	 * @throws NoteNotTrashedException 
	 * @throws UnAuthorisedAccess 
	 ********************************************************************************************************************************/
	
	@RequestMapping(value = "/deleteNote/{noteId}" , method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteNote(@PathVariable String noteId , HttpServletRequest req) throws NoteNotFoundException, UserNotFoundException, NoteNotTrashedException, UnAuthorisedAccess {
		
		String userId = req.getHeader("userId");
		noteService.deleteNote(noteId, userId);
		
		Response dto = new Response();
		dto.setMessage("Successfully deleted Note..");
		dto.setStatus(10);
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	/****************************************************************************************************************
	 * @param token
	 * @return
	 * @throws UserNotFoundException
	 **************************************************************************************************************/
	@RequestMapping(value = "/get-all-notes", method = RequestMethod.GET)
	public ResponseEntity<List<Note>> viewAll( HttpServletRequest req) throws UserNotFoundException{
		
		String userId = req.getHeader("userId");
		List<Note> viewNote = noteService.readNotes(userId);
		System.out.println(viewNote);
		return new ResponseEntity<>(viewNote, HttpStatus.OK);
	}
	
	/*************************************************************************************************************************************************************
	 * @param noteId
	 * @param remindDate
	 * @param req
	 * @return
	 * @throws UserNotFoundException
	 * @throws NoteNotFoundException
	 * @throws DateNotFoundException
	 * @throws RemainderSetException
	 * @throws ParseException 
	 * @throws UnAuthorisedAccess 
	 ***********************************************************************************************************************************************************/
	
	@PutMapping(value = "/reminder/{noteId}/{remindDate}")
	public ResponseEntity<Response> setReminder(@PathVariable String noteId, @PathVariable String remindDate, HttpServletRequest req) throws UserNotFoundException, NoteNotFoundException, DateNotFoundException, RemainderSetException, ParseException, UnAuthorisedAccess{
		
		String userId = req.getHeader("userId");
		noteService.addReminder(noteId, userId, remindDate);
		Response dto = new Response();
		dto.setMessage("Successfully added to reminder");
		dto.setStatus(11);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	/******************************************************************************************************************************************************
	 * @param noteId
	 * @param remindDate
	 * @param req
	 * @return
	 * @throws UserNotFoundException
	 * @throws NoteNotFoundException
	 * @throws DateNotFoundException
	 * @throws RemainderSetException
	 * @throws UnAuthorisedAccess 
	 ******************************************************************************************************************************************************/
	
	@PutMapping(value = "/delete-reminder/{noteId}")
	public ResponseEntity<Response> deleteReminder(@PathVariable String noteId, HttpServletRequest req) throws UserNotFoundException, NoteNotFoundException, DateNotFoundException, RemainderSetException, UnAuthorisedAccess{
		
		String userId = req.getHeader("userId");
		noteService.removeReminder(noteId, userId);
		Response dto = new Response();
		dto.setMessage("Successfully removed from reminder");
		dto.setStatus(12);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	/*************************************************************************************************************************************************
	 * @param noteId
	 * @param req
	 * @param token
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UserNotFoundException
	 * @throws UnAuthorisedAccess
	 **************************************************************************************************************************************************/
	@PutMapping(value = "/pin-note/noteId/{noteId}/pin/{pin}")
	public ResponseEntity<Response> pinNote(@PathVariable String noteId , @PathVariable boolean pin , HttpServletRequest req) throws NoteNotFoundException, UserNotFoundException, UnAuthorisedAccess {
		
		String userId = req.getHeader("userId");
		noteService.pinNote(noteId, userId , pin);
		
		Response dto = new Response();
		dto.setMessage("Successfully pinned Note..");
		dto.setStatus(13);
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	/**
	 * @param noteId
	 * @param color
	 * @param req
	 * @param token
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UserNotFoundException
	 * @throws UnAuthorisedAccess
	 */
	@PutMapping(value = "/add-color/{noteId}/{color}")
	public ResponseEntity<Response> colorNote(@PathVariable String noteId , @PathVariable String color , HttpServletRequest req) throws NoteNotFoundException, UserNotFoundException, UnAuthorisedAccess {
		
		String userId = req.getHeader("userId");
		noteService.addColor(userId, noteId, color);
		
		Response dto = new Response();
		dto.setMessage("Successfully added color to Note..");
		dto.setStatus(16);
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	/******************************************************************************************************************************************************************
	 * @param noteId
	 * @param req
	 * @param token
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UserNotFoundException
	 * @throws UnAuthorisedAccess
	 *********************************************************************************************************************************************************************/
	@RequestMapping(value = "/archive-note/{noteId}/{archive}" , method = RequestMethod.PUT)
	public ResponseEntity<Response> archiveNote(@PathVariable String noteId , @PathVariable boolean archive , HttpServletRequest req) throws NoteNotFoundException, UserNotFoundException, UnAuthorisedAccess {
		
		String userId = req.getHeader("userId");
		noteService.archiveNote(noteId, userId , archive);
		
		Response dto = new Response();
		dto.setMessage("Successfully archived Note..");
		dto.setStatus(15);
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	/****************************************************************************************************************************
	 * @param req
	 * @param token
	 * @return
	 * @throws UserNotFoundException
	 **************************************************************************************************************************/
	@GetMapping(value = "/get-archive-notes")
	public ResponseEntity<List<Note>> viewArchiveNotes( HttpServletRequest req) throws UserNotFoundException{
		
		String userId = req.getHeader("userId");
		List<Note> viewNote = noteService.readArchiveNotes(userId);
		
		return new ResponseEntity<>(viewNote, HttpStatus.OK);
	}
	
	/**
	 * @param req
	 * @param sortOrder
	 * @return
	 * @throws NoteNotFoundException 
	 */
	@GetMapping(value = "/sort-notes-by-title")
	public ResponseEntity<List<ViewNoteDTO>> sortByTitle(HttpServletRequest req , @RequestParam String sortOrder) throws NoteNotFoundException{
		
		String userId = req.getHeader("userId");
		List<ViewNoteDTO> sortedNote = noteService.sortByTitle(userId , sortOrder);
		
		return new ResponseEntity<>(sortedNote , HttpStatus.OK);
	}
	
	/**
	 * @param req
	 * @param sortOrder
	 * @return
	 * @throws NoteNotFoundException
	 */
	@GetMapping(value = "/sort-notes-by-date")
	public ResponseEntity<List<ViewNoteDTO>> sortByDate(HttpServletRequest req , @RequestParam String sortOrder) throws NoteNotFoundException{
		
		String userId = req.getHeader("userId");
		List<ViewNoteDTO> sortedNote = noteService.sortByDate(userId , sortOrder);
		
		return new ResponseEntity<>(sortedNote , HttpStatus.OK);
	}
	
}
