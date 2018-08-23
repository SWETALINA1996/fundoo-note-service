package com.bridgelabz.fundoonotes.note.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.note.exceptions.CreationException;
import com.bridgelabz.fundoonotes.note.exceptions.DateNotFoundException;
import com.bridgelabz.fundoonotes.note.exceptions.LabelNotfoundException;
import com.bridgelabz.fundoonotes.note.exceptions.LinkNotFoundException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteNotTrashedException;
import com.bridgelabz.fundoonotes.note.exceptions.RemainderSetException;
import com.bridgelabz.fundoonotes.note.exceptions.UnAuthorisedAccess;
import com.bridgelabz.fundoonotes.note.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonotes.note.models.CreateNoteDTO;
import com.bridgelabz.fundoonotes.note.models.Label;
import com.bridgelabz.fundoonotes.note.models.LabelDTO;
import com.bridgelabz.fundoonotes.note.models.Note;
import com.bridgelabz.fundoonotes.note.models.UpdateNoteDTO;
import com.bridgelabz.fundoonotes.note.models.UrlMetaInfo;
import com.bridgelabz.fundoonotes.note.models.ViewNoteDTO;
import com.bridgelabz.fundoonotes.note.repositories.LabelRepo;
import com.bridgelabz.fundoonotes.note.repositories.NoteRepo;
import com.bridgelabz.fundoonotes.note.repositories.NoteRepository;
import com.bridgelabz.fundoonotes.note.utility.LinkInfoProvider;
import com.bridgelabz.fundoonotes.note.utility.Utility;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private NoteRepo elasticNoteRepo;

	
	@Autowired
	private LabelRepo elasticLabelRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	LinkInfoProvider linkInfoProvider;

	/***********************************************
	 * create
	 * 
	 * @throws LinkNotFoundException
	 ***********************************************************************************/
	@Override
	public ViewNoteDTO createNote(CreateNoteDTO createNote, String userId)
			throws CreationException, LinkNotFoundException {

		Utility.isNoteValidate(createNote);

		Note note = new Note();
		note.setTitle(createNote.getTitle());
		note.setDescription(createNote.getDescription());
		Date createdDate = new Date();
		note.setCreatedAt(createdDate);
		note.setUpdatedAt(createdDate);
		note.setUserId(userId);
		note.setPin(createNote.isPin());
		note.setArchive(createNote.isArchive());

		if (note.getColor() == null) {
			note.setColor(createNote.getColor());
		}

		if (createNote.getReminder() != null) {
			note.setReminder(createNote.getReminder());
		}
		
		if (createNote.getLabelId() != null) {
			Optional<Label> optionalLabel = elasticLabelRepo.findByLabelIdAndUserId(createNote.getLabelId(), userId);
			if (optionalLabel.isPresent()) {
				LabelDTO labelDto = new LabelDTO();
				labelDto.setLabelId(optionalLabel.get().getLabelId());
				labelDto.setLabelName(optionalLabel.get().getLabelName());
				
				List<LabelDTO> labelList = Stream.concat(note.getLabelList().stream(), Stream.of(labelDto))
						.collect(Collectors.toList());
				note.setLabelList(labelList);
			}
		}

		List<UrlMetaInfo> urlList = linkInfoProvider.getDescription(createNote.getDescription());
		note.setUrlList(urlList);

		noteRepository.save(note);

		elasticNoteRepo.save(note);

		ViewNoteDTO viewNote = modelMapper.map(note, ViewNoteDTO.class);

		return viewNote;

	}

	/*************************************************************
	 * update
	 * 
	 * @throws UnAuthorisedAccess
	 * @throws LinkNotFoundException 
	 **********************************************************************************/
	@Override
	public void updateNote(UpdateNoteDTO updateNote, String userId)
			throws NoteNotFoundException, UserNotFoundException, UnAuthorisedAccess, LinkNotFoundException {

		Optional<Note> optionalNote = elasticNoteRepo.findById(updateNote.getId());
		if (!optionalNote.isPresent()) {
			throw new NoteNotFoundException("Note not present");
		}

		if (!userId.equals(optionalNote.get().getUserId())) {
			throw new UnAuthorisedAccess("User doesnot have the note");
		}

		Note note = optionalNote.get();

		if (note.isTrash()) {
			throw new NoteNotFoundException("Note not present..Please check your trash");
		}

		Date updatedDate = new Date();
		updateNote.setUpdatedDate(updatedDate);

		if (updateNote.getTitle() != null) {
			note.setTitle(updateNote.getTitle());
		}
		if (updateNote.getDescription() != null) {
			note.setDescription(updateNote.getDescription());
			List<UrlMetaInfo> urlList = linkInfoProvider.getDescription(updateNote.getDescription());
			note.setUrlList(urlList);
		}
		note.setUpdatedAt(updateNote.getUpdatedDate());

		noteRepository.save(note);
		elasticNoteRepo.save(note);

	}

	/******************************************
	 * trash
	 * 
	 * @throws UnAuthorisedAccess
	 *********************************************************************************/
	@Override
	public void trashNoteAndRestore(String noteId, String userId, boolean restore)
			throws NoteNotFoundException, UserNotFoundException, UnAuthorisedAccess {

		Optional<Note> optionalNote = isValidRequest(noteId, userId);
		Note note = optionalNote.get();

		note.setTrash(restore);

		noteRepository.save(note);
		elasticNoteRepo.save(note);

	}

	/**********************************************************
	 * emptyTrash
	 * 
	 * @throws UserNotFoundException
	 ************************************************************************/
	@Override
	public void emptyTrash(String userId) throws UserNotFoundException {

		List<Note> notes = elasticNoteRepo.findAllByUserIdAndIsTrash(userId, true);
		for (Note note : notes) {

			noteRepository.delete(note);
			elasticNoteRepo.delete(note);

		}

	}

	/***********************************************************
	 * view-trash
	 * 
	 * @throws UserNotFoundException
	 ************************************************************************/
	@Override
	public List<Note> viewTrash(String userId) throws UserNotFoundException {

		List<Note> notes = elasticNoteRepo.findAllByUserIdAndIsTrash(userId, true);
		List<Note> noteList = new ArrayList<Note>();
		for (Note note : notes) {
			if (note.isArchive())
				noteList.add(note);
		}

		return noteList;
	}

	/********************************************
	 * delete
	 * 
	 * @throws UnAuthorisedAccess
	 ******************************************************************************/
	@Override
	public void deleteNote(String noteId, String userId)
			throws UserNotFoundException, NoteNotTrashedException, NoteNotFoundException, UnAuthorisedAccess {

		Optional<Note> optionalNote = isValidRequest(noteId, userId);
		Note note = optionalNote.get();
		if (!note.isTrash()) {
			throw new NoteNotTrashedException("Note is not in trash");
		}

		noteRepository.deleteById(noteId);
		elasticNoteRepo.deleteById(noteId);
	}

	/***********************************************
	 * read
	 *****************************************************************************/
	@Override
	public List<Note> readNotes(String userId , String sortType) throws UserNotFoundException {

		List<Note> notes = elasticNoteRepo.findAllByUserId(userId);
		List<Note> noteList = new ArrayList<Note>();
		for (Note note : notes) {
			if (!note.isTrash())
				noteList.add(note);
		}

		return noteList;

	}

	/***************************************
	 * add remainder
	 * 
	 * @throws UnAuthorisedAccess
	 ****************************************************************************/
	@Override
	public void addReminder(String noteId, String userId, String remindDate) throws NoteNotFoundException,
			UserNotFoundException, DateNotFoundException, RemainderSetException, ParseException, UnAuthorisedAccess {

		Optional<Note> optionalNote = isValidRequest(noteId, userId);

		if (remindDate == null) {
			throw new RemainderSetException("Please enter date");
		}

		Date reminderDate = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(remindDate);

		if (reminderDate.before(new Date())) {
			throw new DateNotFoundException("Past dates cannot be recorded");
		}
		optionalNote.get().setReminder(reminderDate);

		noteRepository.save(optionalNote.get());
		elasticNoteRepo.save(optionalNote.get());
	}

	/*****************************************
	 * remove remainder
	 * 
	 * @throws UnAuthorisedAccess
	 ***********************************************************************/
	@Override
	public void removeReminder(String noteId, String userId)
			throws NoteNotFoundException, UserNotFoundException, UnAuthorisedAccess {

		Optional<Note> optionalNote = isValidRequest(noteId, userId);
		optionalNote.get().setReminder(null);
		noteRepository.save(optionalNote.get());
		elasticNoteRepo.save(optionalNote.get());
	}

	/******************************************
	 * PinNote
	 * 
	 * @throws UnAuthorisedAccess
	 *******************************************************************************/

	@Override
	public void pinNote(String noteId, String userId, boolean pin)
			throws NoteNotFoundException, UserNotFoundException, UnAuthorisedAccess {

		Optional<Note> optionalNote = isValidRequest(noteId, userId);

		Note note = optionalNote.get();

		if(pin){
			note.setPin(true);
			if (note.isArchive())
				note.setArchive(false);
		}
		note.setPin(false);

		noteRepository.save(note);
		elasticNoteRepo.save(note);
	}

	/*********************************************
	 * Archive
	 ***************************************************************************/

	@Override
	public void archiveNote(String noteId, String userId, boolean archive)
			throws NoteNotFoundException, UserNotFoundException, UnAuthorisedAccess {

		Optional<Note> optionalNote = isValidRequest(noteId, userId);

		Note note = optionalNote.get();
		if (archive) {
			note.setArchive(true);
			if (note.isPin())
				note.setPin(false);
		}
		note.setArchive(false);

		noteRepository.save(note);
		elasticNoteRepo.save(note);

	}

	/*************************************
	 * read-archive-notes
	 ************************************************************************/

	@Override
	public List<Note> readArchiveNotes(String userId) throws UserNotFoundException {

		List<Note> notes = elasticNoteRepo.findAllByUserId(userId);
		List<Note> noteList = new ArrayList<Note>();
		for (Note note : notes) {
			if (note.isArchive())
				noteList.add(note);
		}

		return noteList;
	}

	/**************************************
	 * add-color
	 * 
	 * @throws UnAuthorisedAccess
	 ***********************************************************************/
	@Override
	public void addColor(String userId, String noteId, String color)
			throws NoteNotFoundException, UserNotFoundException, UnAuthorisedAccess {

		Optional<Note> optionalNote = isValidRequest(noteId, userId);

		Note note = optionalNote.get();
		note.setColor(color);

		noteRepository.save(note);
		elasticNoteRepo.save(note);
	}

	/***********************************************************************sort-notes-by-title**********************************************************************/
	@Override
	public List<ViewNoteDTO> sortByTitle(String userId, String sortOrder) throws NoteNotFoundException {

		List<Note> listOfNotes = elasticNoteRepo.findAllByUserId(userId);
		if (listOfNotes.isEmpty()) {
			throw new NoteNotFoundException("No note present");
		}
		if (sortOrder.equalsIgnoreCase("descending")) {
			List<ViewNoteDTO> noteList = listOfNotes.stream().sorted((n1, n2) -> n1.getTitle().compareTo(n2.getTitle()))
					.map(filterStream -> modelMapper.map(filterStream, ViewNoteDTO.class)).collect(Collectors.toList());
			//Collections.reverse(noteList);
			return noteList;
		}
		
		List<ViewNoteDTO> noteList = listOfNotes.stream().sorted(Comparator.comparing(Note::getTitle))
				.map(filterStream -> modelMapper.map(filterStream, ViewNoteDTO.class)).collect(Collectors.toList());
		
		return noteList;
	}
/*****************************************************************************sort-notes-by-date*********************************************************************************/
	@Override
	public List<ViewNoteDTO> sortByDate(String userId, String sortOrder) throws NoteNotFoundException {
		
		List<Note> listOfNotes = elasticNoteRepo.findAllByUserId(userId);
		if (listOfNotes.isEmpty()) {
			throw new NoteNotFoundException("No note present");
		}
		if (sortOrder.equalsIgnoreCase("descending")) {
			List<ViewNoteDTO> noteList = listOfNotes.stream().sorted(Comparator.comparing(Note::getCreatedAt))
					.map(filterStream -> modelMapper.map(filterStream, ViewNoteDTO.class)).collect(Collectors.toList());
			Collections.reverse(noteList);
			return noteList;
		}
		
		List<ViewNoteDTO> noteList = listOfNotes.stream().sorted(Comparator.comparing(Note::getTitle))
				.map(filterStream -> modelMapper.map(filterStream, ViewNoteDTO.class)).collect(Collectors.toList());
		
		return noteList;
	}
	@Override
	public List<Note> readNotesByLabelId(String userId, String labelId) throws LabelNotfoundException, UnAuthorisedAccess{
		
		Optional<Label> optionalLabel = elasticLabelRepo.findById(labelId);
		if(!optionalLabel.isPresent()) {
			throw new LabelNotfoundException("label not found");
		}
		if(!userId.equals(optionalLabel.get().getUserId())) {
			throw new UnAuthorisedAccess("User doenot have the label");
		}
		List<Note> notes = elasticNoteRepo.findAllByUserId(userId);
		/*Optional<Note> objectNote=elasticNoteRepo.findById(userId);
		List<LabelDTO> list=objectNote.get().getLabelList();*/
		List<Note> noteList = new ArrayList<Note>();
		noteList=notes.stream().filter(str->str.equals(labelId)).collect(Collectors.toList());
		/*for (Note note : notes) {
			if (labelId.equals())
				noteList.add(note);
		}*/

		return noteList;
		
	}
	/*********************************************************************************************************************************
	 * @param noteId
	 * @param token
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UserNotFoundException
	 * @throws UnAuthorisedAccess
	 *********************************************************************************************************************************/
	public Optional<Note> isValidRequest(String noteId, String userId)
			throws NoteNotFoundException, UnAuthorisedAccess {

		if (noteId == null) {
			throw new NoteNotFoundException("NoteId required");
		}

		Optional<Note> optionalNote = elasticNoteRepo.findById(noteId);

		if (!optionalNote.isPresent()) {
			throw new NoteNotFoundException("Note not present");
		}

		if (!userId.equals(optionalNote.get().getUserId())) {
			throw new UnAuthorisedAccess("User doesnot have the note");
		}
		return optionalNote;
	}


}
