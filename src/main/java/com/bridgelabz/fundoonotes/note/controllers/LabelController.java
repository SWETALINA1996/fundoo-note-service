package com.bridgelabz.fundoonotes.note.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.note.exceptions.CreationException;
import com.bridgelabz.fundoonotes.note.exceptions.InvalidLabelException;
import com.bridgelabz.fundoonotes.note.exceptions.LabelCreationException;
import com.bridgelabz.fundoonotes.note.exceptions.LabelNotfoundException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.note.exceptions.UnAuthorisedAccess;
import com.bridgelabz.fundoonotes.note.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonotes.note.models.LabelDTO;
import com.bridgelabz.fundoonotes.note.models.LabelViewDTO;
import com.bridgelabz.fundoonotes.note.models.Response;
import com.bridgelabz.fundoonotes.note.services.LabelService;

@RestController

@RequestMapping("/labels")
public class LabelController {

	@Autowired
	private LabelService labelService;
	
	/********************************************************************************************************************************
	 * @param labelDto
	 * @param req
	 * @param token
	 * @return
	 * @throws CreationException
	 * @throws LabelCreationException 
	 * @throws UserNotFoundException 
	 ********************************************************************************************************************************/
	@RequestMapping(value = "/create-label" , method = RequestMethod.POST)
	public ResponseEntity<LabelViewDTO> createLabel(@RequestBody LabelDTO labelDto , HttpServletRequest req) throws CreationException, UserNotFoundException, LabelCreationException{
		
		String userId = req.getHeader("userId");
		LabelViewDTO viewLabel = labelService.createLabel(labelDto , userId);
		
		return new ResponseEntity<>(viewLabel , HttpStatus.OK);
	}
	
	/*********************************************************************************************************************************
	 * @param labelName
	 * @param req
	 * @param token
	 * @return
	 * @throws CreationException
	 * @throws UserNotFoundException
	 * @throws LabelNotfoundException
	 ************************************************************************************************************************************/
	@RequestMapping(value = "/remove-label/{labelId}" , method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteLabel(@PathVariable String labelId , HttpServletRequest req , @RequestAttribute("token") String token) throws CreationException, UserNotFoundException, LabelNotfoundException{
		
		String userId = req.getHeader("userId");
		labelService.removeLabel(labelId , userId);
		
		Response dto = new Response();
		dto.setMessage("Successfully removed label..");
		dto.setStatus(17);

		return new ResponseEntity<>(dto , HttpStatus.OK);
	}
	
	/*****************************************************************************************************************************************
	 * @param labelName
	 * @param noteId
	 * @param req
	 * @param token
	 * @return5b5c1ae1bebbe961f4c381c6
	 * @throws CreationException
	 * @throws UserNotFoundException
	 * @throws LabelNotfoundException
	 * @throws NoteNotFoundException 
	 * @throws UnAuthorisedAccess 
	 * @throws InvalidLabelException 
	 *****************************************************************************************************************************************/
	@RequestMapping(value = "/add-label/{noteId}/{labelId}" , method = RequestMethod.POST)
	public ResponseEntity<Response> addLabel(@PathVariable String noteId , @PathVariable String labelId , HttpServletRequest req) throws CreationException, UserNotFoundException, LabelNotfoundException, NoteNotFoundException, UnAuthorisedAccess, InvalidLabelException{
		
		String userId = req.getHeader("userId");
		labelService.addLabel(userId , noteId , labelId);
		
		Response dto = new Response();
		dto.setMessage("Successfully added label..");
		dto.setStatus(17);

		return new ResponseEntity<>(dto , HttpStatus.OK);
	}
	
	/*******************************************************************************************************************************************
	 * @param req
	 * @param token
	 * @return
	 * @throws UserNotFoundException
	 ******************************************************************************************************************************************/
	@RequestMapping(value = "/get-all-labels", method = RequestMethod.GET)
	public ResponseEntity<List<LabelDTO>> viewAllLabel( HttpServletRequest req) throws UserNotFoundException{
		
		String userId = req.getHeader("userId");
		List<LabelDTO> viewLabel = labelService.readLabels(userId);
		
		return new ResponseEntity<>(viewLabel , HttpStatus.OK);
	}
	
	/**
	 * @param req
	 * @param token
	 * @param labelName
	 * @return
	 * @throws UserNotFoundException
	 * @throws LabelNotfoundException
	 */
	@RequestMapping(value = "/update-label/{labelId}/{renameLabel}", method = RequestMethod.PUT)
	public ResponseEntity<Response> updateLabel( HttpServletRequest req , @RequestAttribute("token") String token ,@PathVariable String labelId , @PathVariable String renameLabel ) throws UserNotFoundException, LabelNotfoundException{
		
		String userId = req.getHeader("userId");
		labelService.updateLabel(userId, labelId , renameLabel);
		
		Response dto = new Response();
		dto.setMessage("Successfully updated label..");
		dto.setStatus(18);

		return new ResponseEntity<>(dto , HttpStatus.OK);
		
	}
	
	/**
	 * @param req
	 * @param token
	 * @param noteId
	 * @param labelName
	 * @return
	 * @throws UserNotFoundException
	 * @throws LabelNotfoundException
	 * @throws NoteNotFoundException 
	 * @throws InvalidLabelException 
	 */
	@RequestMapping(value = "/remove-label-from-note/{noteId}/{labelName}", method = RequestMethod.PUT)
	public ResponseEntity<Response> removeLabelFromNote( HttpServletRequest req , @RequestAttribute("token") String token ,@PathVariable String noteId , @PathVariable String labelId ) throws UserNotFoundException, LabelNotfoundException, NoteNotFoundException, InvalidLabelException{
		
		String userId = req.getHeader("userId");
		labelService.removeLabelFromNote(userId, noteId, labelId);
		
		Response dto = new Response();
		dto.setMessage("Successfully removed label from note.");
		dto.setStatus(18);

		return new ResponseEntity<>(dto , HttpStatus.OK);
		
	}
	/**
	 * @param req
	 * @param sortOrder
	 * @return
	 * @throws NoteNotFoundException 
	 */
	@GetMapping(value = "/sort-label-by-title")
	public ResponseEntity<List<LabelDTO>> sortByTitle(HttpServletRequest req , @RequestParam String sortOrder) throws NoteNotFoundException{
		
		String userId = req.getHeader("userId");
		List<LabelDTO> sortedLabel = labelService.sortByTitle(userId , sortOrder);
		
		return new ResponseEntity<>(sortedLabel , HttpStatus.OK);
	}
	
	/**
	 * @param req
	 * @param sortOrder
	 * @return
	 * @throws NoteNotFoundException
	 *//*
	@GetMapping(value = "/sort-notes-by-date")
	public ResponseEntity<List<ViewNoteDTO>> sortByDate(HttpServletRequest req , @RequestParam String sortOrder) throws NoteNotFoundException{
		
		String userId = req.getHeader("userId");
		List<ViewNoteDTO> sortedNote = noteService.sortByDate(userId , sortOrder);
		
		return new ResponseEntity<>(sortedNote , HttpStatus.OK);
	}*/
	
}
