package com.bridgelabz.fundoonotes.note.services;

import java.util.List;

import com.bridgelabz.fundoonotes.note.exceptions.CreationException;
import com.bridgelabz.fundoonotes.note.exceptions.InvalidLabelException;
import com.bridgelabz.fundoonotes.note.exceptions.LabelCreationException;
import com.bridgelabz.fundoonotes.note.exceptions.LabelNotfoundException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.note.exceptions.UnAuthorisedAccess;
import com.bridgelabz.fundoonotes.note.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonotes.note.models.Label;
import com.bridgelabz.fundoonotes.note.models.LabelDTO;
import com.bridgelabz.fundoonotes.note.models.LabelViewDTO;
import com.bridgelabz.fundoonotes.note.models.ViewNoteDTO;

public interface LabelService {

/**
 * @param labeldto
 * @param userId
 * @return
 * @throws CreationException
 * @throws UserNotFoundException
 * @throws LabelCreationException
 */
public LabelViewDTO createLabel(LabelDTO labeldto , String userId) throws CreationException, UserNotFoundException, LabelCreationException;
	
	/**
	 * @param userId
	 * @param noteId
	 * @param labelName
	 * @throws CreationException
	 * @throws UserNotFoundException
	 * @throws NoteNotFoundException
	 * @throws UnAuthorisedAccess 
	 * @throws LabelNotfoundException 
	 * @throws InvalidLabelException 
	 */
	public void addLabel(String userId , String noteId , String labelName) throws CreationException, UserNotFoundException, NoteNotFoundException, UnAuthorisedAccess, LabelNotfoundException, InvalidLabelException;
	
	/**
	 * @param labelName
	 * @param userId
	 * @throws UserNotFoundException
	 * @throws LabelNotfoundException
	 */
	public void removeLabel(String labelName , String userId) throws UserNotFoundException, LabelNotfoundException;
	
	/**
	 * @param userId
	 * @param noteId
	 * @param labelName
	 * @throws UserNotFoundException 
	 * @throws NoteNotFoundException 
	 * @throws LabelNotfoundException 
	 * @throws InvalidLabelException 
	 */
	public void removeLabelFromNote(String userId , String noteId , String labelName) throws UserNotFoundException, NoteNotFoundException, LabelNotfoundException, InvalidLabelException;
	
	/**
	 * @param userId
	 * @param labelName
	 * @param renameLabel
	 * @throws LabelNotfoundException
	 * @throws UserNotFoundException
	 */
	public void updateLabel(String userId , String labelName , String renameLabel) throws LabelNotfoundException, UserNotFoundException;
	
	/**
	 * @param userId
	 * @return
	 * @throws UserNotFoundException
	 */
	public List<LabelDTO> readLabels(String userId) throws UserNotFoundException;
	
	/**
	 * @param userId
	 * @param noteId
	 * @return
	 * @throws UserNotFoundException 
	 * @throws NoteNotFoundException 
	 */
	
	public List<LabelDTO> readNoteLabels(String userId , String noteId) throws UserNotFoundException, NoteNotFoundException;

	/**
	 * @param userId
	 * @param sortOrder
	 * @return
	 * @throws NoteNotFoundException 
	 */
	public List<LabelDTO> sortByTitle(String userId , String sortOrder) throws NoteNotFoundException;

	/**
	 * @param userId
	 * @param sortOrder
	 * @return
	 * @throws NoteNotFoundException
	 */
	public List<LabelDTO> sortByDate(String userId, String sortOrder) throws NoteNotFoundException;
}
