package com.bridgelabz.fundoonotes.note.services;

import java.text.ParseException;
import java.util.List;

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
import com.bridgelabz.fundoonotes.note.models.UpdateNoteDTO;
import com.bridgelabz.fundoonotes.note.models.ViewNoteDTO;

public interface NoteService {

	/**
	 * @param createNote
	 * @param userID
	 * @return
	 * @throws CreationException
	 * @throws LinkNotFoundException 
	 */
	public ViewNoteDTO createNote(CreateNoteDTO createNote , String userID) throws CreationException, LinkNotFoundException;
	
	/**
	 * @param updateNote
	 * @param userID
	 * @throws NoteNotFoundException
	 * @throws UserNotFoundException
	 * @throws UnAuthorisedAccess 
	 */
	public void updateNote(UpdateNoteDTO updateNote , String userID) throws NoteNotFoundException, UserNotFoundException, UnAuthorisedAccess;
	
	/**
	 * @param noteId
	 * @param userId
	 * @param restore
	 * @throws NoteNotFoundException
	 * @throws UserNotFoundException
	 * @throws UnAuthorisedAccess 
	 */
	public void trashNoteAndRestore(String noteId , String userId , boolean restore) throws NoteNotFoundException, UserNotFoundException, UnAuthorisedAccess;
	
	/**
	 * @param userId
	 * @throws UserNotFoundException 
	 */
	public void emptyTrash(String userId) throws UserNotFoundException;
	
	/**
	 * @param userId
	 * @return
	 * @throws UserNotFoundException 
	 */
	public List<Note> viewTrash(String userId) throws UserNotFoundException;
	
	/**
	 * @param noteID
	 * @param userID
	 * @throws UserNotFoundException
	 * @throws NoteNotTrashedException
	 * @throws NoteNotFoundException
	 * @throws UnAuthorisedAccess 
	 */
	public void deleteNote(String noteID , String userID) throws UserNotFoundException, NoteNotTrashedException, NoteNotFoundException, UnAuthorisedAccess;
	
	/**
	 * @param token
	 * @return
	 * @throws UserNotFoundException
	 */
	public List<Note> readNotes(String token) throws UserNotFoundException;
	
	/**
	 * @param noteId
	 * @param userId
	 * @param remaindDate
	 * @throws NoteNotFoundException
	 * @throws UserNotFoundException
	 * @throws DateNotFoundException
	 * @throws RemainderSetException
	 * @throws ParseException
	 * @throws UnAuthorisedAccess 
	 */
	public void addReminder(String noteId , String userId , String remaindDate) throws NoteNotFoundException, UserNotFoundException, DateNotFoundException, RemainderSetException, ParseException, UnAuthorisedAccess;
	
	/**
	 * @param noteId
	 * @param userId
	 * @throws NoteNotFoundException
	 * @throws UserNotFoundException
	 * @throws UnAuthorisedAccess 
	 */
	public void removeReminder(String noteId , String userId) throws NoteNotFoundException, UserNotFoundException, UnAuthorisedAccess;
	
	/**
	 * @param noteId
	 * @param userId
	 * @throws NoteNotFoundException
	 * @throws UserNotFoundException
	 * @throws UnAuthorisedAccess
	 */
	public void pinNote(String noteId , String userId , boolean pin) throws NoteNotFoundException, UserNotFoundException, UnAuthorisedAccess;
	
	/**
	 * @param noteId
	 * @param userId
	 * @throws NoteNotFoundException
	 * @throws UserNotFoundException
	 * @throws UnAuthorisedAccess
	 */
//	public void unPinNote(String noteId , String userId) throws NoteNotFoundException, UserNotFoundException, UnAuthorisedAccess;

	/**
	 * @param noteId
	 * @param userId
	 * @throws NoteNotFoundException
	 * @throws UserNotFoundException
	 * @throws UnAuthorisedAccess
	 */
	public void archiveNote(String noteId , String userId , boolean archive) throws NoteNotFoundException, UserNotFoundException, UnAuthorisedAccess;

	/**
	 * @param noteId
	 * @param userId
	 * @throws NoteNotFoundException
	 * @throws UserNotFoundException
	 * @throws UnAuthorisedAccess
	 */
	//public void unArchive(String noteId , String userId) throws NoteNotFoundException, UserNotFoundException, UnAuthorisedAccess;

	/**
	 * @param userId
	 * @return
	 * @throws UserNotFoundException
	 */
	public List<Note> readArchiveNotes(String userId) throws UserNotFoundException;
	
	/**
	 * @param userId
	 * @param noteId
	 * @param color
	 * @throws UserNotFoundException 
	 * @throws NoteNotFoundException 
	 * @throws UnAuthorisedAccess 
	 */
	public void addColor(String userId , String noteId , String color) throws NoteNotFoundException, UserNotFoundException, UnAuthorisedAccess;

	/**
	 * @param userId
	 * @param sortOrder
	 * @return
	 * @throws NoteNotFoundException 
	 */
	public List<ViewNoteDTO> sortByTitle(String userId , String sortOrder) throws NoteNotFoundException;
	
	/**
	 * @param userId
	 * @param sortOrder
	 * @return
	 * @throws NoteNotFoundException
	 */
	public List<ViewNoteDTO> sortByDate(String userId , String sortOrder) throws NoteNotFoundException;
}
	