package com.bridgelabz.fundoonotes.note.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

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
import com.bridgelabz.fundoonotes.note.models.Note;
import com.bridgelabz.fundoonotes.note.models.ViewNoteDTO;
import com.bridgelabz.fundoonotes.note.repositories.LabelRepo;
import com.bridgelabz.fundoonotes.note.repositories.LabelRepository;
import com.bridgelabz.fundoonotes.note.repositories.NoteRepo;
import com.bridgelabz.fundoonotes.note.repositories.NoteRepository;
import com.bridgelabz.fundoonotes.note.utility.Utility;

@Service
public class LabelServiceImpl implements LabelService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private NoteRepo noteRepo;

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private LabelRepo labelRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private Environment environment;
	
	/**************************************
	 * create-label
	 *********************************************************************************/
	@Override
	public LabelViewDTO createLabel(LabelDTO labelDto, String userId)
			throws CreationException, UserNotFoundException, LabelCreationException {

		Utility.isLabelValidate(labelDto);

		Optional<Label> optional = labelRepo.findByLabelNameAndUserId(labelDto.getLabelName(), userId);
		if (optional.isPresent()) {
			throw new LabelCreationException("Label already exists");
		}

		Label label = new Label();
		label.setLabelName(labelDto.getLabelName());
		label.setUserId(userId);

		labelRepository.save(label);
		labelRepo.save(label);

		LabelViewDTO viewLabel = modelMapper.map(label, LabelViewDTO.class);

		return viewLabel;
	}

	/**************************************
	 * add-label
	 * 
	 * @throws UserNotFoundException
	 * @throws NoteNotFoundException
	 * @throws UnAuthorisedAccess
	 * @throws InvalidLabelException 
	 * @throws LabelNotfoundException
	 **********************************************************************************/
	@Override

	public void addLabel(String userId, String noteId, String labelId)
			throws CreationException, UserNotFoundException, NoteNotFoundException, UnAuthorisedAccess, InvalidLabelException, LabelNotfoundException {


		   if (labelId == null || labelId.trim().length() == 0) {
	            throw new InvalidLabelException("Invalid LabelName");
	        }

	        Optional<Note> optionalNote = noteRepo.findById(noteId);

	        if (!optionalNote.isPresent()) {
	            throw new NoteNotFoundException(environment.getProperty("NoteNotFound"));
	        }
	        if (!optionalNote.get().getUserId().equals(userId)) {
	            throw new UnAuthorisedAccess(environment.getProperty("UnauthorizedUser"));
	        }

	        Note note = optionalNote.get();

	        Optional<Label> optionalLabel = labelRepo.findByLabelIdAndUserId(labelId, userId);

	        if (optionalLabel.isPresent()) {
	            /*Label label = optionalLabel.get();
	            LabelDTO labelDto = modelMapper.map(label, LabelDTO.class);
	            List<LabelDTO> labelDtoList = Stream.concat(note.getLabelList().stream(), Stream.of(labelDto))
	                    .collect(Collectors.toList());
	            note.setLabelList(labelDtoList);*/
	        	LabelDTO labelDto = new LabelDTO();
				labelDto.setLabelId(optionalLabel.get().getLabelId());
				labelDto.setLabelName(optionalLabel.get().getLabelName());
	        	/*List<LabelDTO> labelList = new ArrayList<LabelDTO>();
				labelList.add(labelDto);*/
				List<LabelDTO> labelList = Stream.concat(note.getLabelList().stream(), Stream.of(labelDto))
	                    .collect(Collectors.toList());
				note.setLabelList(labelList);
	        	
	        }
	        else{
	        	throw new LabelNotfoundException("Create label to add");
	        }
	        	/*else {
	        }
	            Label label = new Label();
	            label.setLabelName(labelNa);
	            label.setUserId(userId);
	            labelRepository.save(label);

	            labelRepo.save(label);

	            LabelDTO labelDto = modelMapper.map(label, LabelDTO.class);
	            List<LabelDTO> labelDtoList = Stream.concat(note.getLabelList().stream(), Stream.of(labelDto))
	                    .collect(Collectors.toList());
	            note.setLabelList(labelDtoList);
	        }*/

	        noteRepository.save(note);

	        noteRepo.save(note);


	}

	/**************************************
	 * remove-label
	 ******************************************************************************/
	@Override
	public void removeLabel(String labelId, String userId) throws UserNotFoundException, LabelNotfoundException {

		if (labelId == null) {
			throw new LabelNotfoundException("LabelName required");
		}

		// Optional<Label> optionalLabel =
		// labelRepository.findByLabelNameAndUserId(labelName, userId);
		Optional<Label> optionalLabel = labelRepo.findByLabelIdAndUserId(labelId, userId);
		if (!optionalLabel.isPresent()) {
			throw new LabelNotfoundException("Label not present");
		}

		labelRepository.deleteByLabelId(labelId);
		labelRepo.deleteByLabelId(labelId);

		List<Note> listOfNotes = new ArrayList<>();
		// listOfNotes = noteRepository.findAllByUserId(userId);
		listOfNotes = noteRepo.findAllByUserId(userId);

		for (int i = 0; i < listOfNotes.size(); i++) {
			if (listOfNotes.get(i).getLabelList() != null) {
				for (int j = 0; j < listOfNotes.get(i).getLabelList().size(); j++) {
					if (listOfNotes.get(i).getLabelList().get(j).getLabelName().equals(labelId)) {

						listOfNotes.get(i).getLabelList().remove(j);
						Note note = listOfNotes.get(i);
						noteRepository.save(note);
						noteRepo.save(note);
					}
				}
			}
		}
	}

	/************************************************************
	 * remove-label-from-note
	 * 
	 * @throws UserNotFoundException
	 * @throws NoteNotFoundException
	 * @throws LabelNotfoundException
	 * @throws InvalidLabelException 
	 *********************************************************/

	@Override
	public void removeLabelFromNote(String userId, String noteId, String labelId)
			throws UserNotFoundException, NoteNotFoundException, LabelNotfoundException, InvalidLabelException {

		  if (labelId == null || labelId.trim().length() == 0) {
	            throw new InvalidLabelException("Invalid LabelName");
	        }
		 
		Optional<Note> optionalNote = noteRepo.findById(noteId);
		if (!optionalNote.isPresent()) {
			throw new NoteNotFoundException("Note is not present");
		}

		Optional<Label> optionalLabel = labelRepo.findByLabelId(labelId);
		if (!optionalLabel.isPresent()) {
			throw new LabelNotfoundException("Label not present");
		}

		if (!userId.equals(optionalNote.get().getUserId())) {
			throw new NoteNotFoundException("User doesnot have the note");
		}

		Note note = optionalNote.get();
		note.getLabelList().remove(optionalLabel.get());

		noteRepository.save(optionalNote.get());
		noteRepo.save(optionalNote.get());
	}

	/**************************************
	 * change-label
	 *****************************************************************************/
	@Override
	public void updateLabel(String userId, String labelName, String renameLabel)
			throws LabelNotfoundException, UserNotFoundException {

		if (labelName == null) {
			throw new LabelNotfoundException("Label name required");
		}

		Optional<Label> optionalLabel = labelRepo.findByLabelNameAndUserId(labelName, userId);
		if (!optionalLabel.isPresent()) {
			throw new LabelNotfoundException("Label not present");
		}
		List<Note> listOfNotes = new ArrayList<>();
		listOfNotes = noteRepo.findAllByUserId(userId);

		for (int i = 0; i < listOfNotes.size(); i++) {
			if (listOfNotes.get(i).getLabelList() != null) {
				for (int j = 0; j < listOfNotes.get(i).getLabelList().size(); j++) {
					if (listOfNotes.get(i).getLabelList().get(j).getLabelName().equals(renameLabel)) {

						listOfNotes.get(i).getLabelList().get(j).setLabelName(renameLabel);

						Note note = listOfNotes.get(i);

						noteRepository.save(note);
						noteRepo.save(note);
					}
				}
			}
		}
		optionalLabel.get().setLabelName(renameLabel);
		labelRepository.save(optionalLabel.get());
		labelRepo.save(optionalLabel.get());
	}

	/*************************************
	 * view-label
	 *******************************************************************************/
	@Override
	public List<LabelDTO> readLabels(String userId) throws UserNotFoundException {

		// List<LabelDTO> labels =
		// labelRepository.findAllByUserId(optional.get().getUserId());
		List<LabelDTO> labels = labelRepo.findAllByUserId(userId);
		List<LabelDTO> labelList = new ArrayList<LabelDTO>();
		for (LabelDTO label : labels) {
			labelList.add(label);
		}

		return labelList;
	}

	/****************************************************
	 * read-labels-from-note
	 * 
	 * @throws UserNotFoundException
	 * @throws NoteNotFoundException
	 **************************************************************/
	@Override
	public List<LabelDTO> readNoteLabels(String userId, String noteId)
			throws UserNotFoundException, NoteNotFoundException {

		// Optional<Note> optionalNote = noteRepository.findById(noteId);
		Optional<Note> optionalNote = noteRepo.findById(noteId);
		if (!optionalNote.isPresent()) {
			throw new NoteNotFoundException("Note is not present");
		}

		return optionalNote.get().getLabelList();
	}

	@Override
	public List<LabelDTO> sortByTitle(String userId, String sortOrder) throws NoteNotFoundException {
		
		List<LabelDTO> listOfLabels = labelRepo.findAllByUserId(userId);
		if (listOfLabels.isEmpty()) {
			throw new NoteNotFoundException("No label present");
		}
		if (sortOrder.equalsIgnoreCase("descending")) {
			List<LabelDTO> labelList = listOfLabels.stream().sorted(Comparator.comparing(LabelDTO::getLabelName))
					.collect(Collectors.toList());
			Collections.reverse(labelList);
			return labelList;
		}
		
		List<LabelDTO> labelList = listOfLabels.stream().sorted(Comparator.comparing(LabelDTO::getLabelName))
				.map(filterStream -> modelMapper.map(filterStream, LabelDTO.class)).collect(Collectors.toList());
		
		return labelList;
	}
	
	@Override
	public List<LabelDTO> sortByDate(String userId, String sortOrder) throws NoteNotFoundException {
		
		List<LabelDTO> listOfLabels = labelRepo.findAllByUserId(userId);
		if (listOfLabels.isEmpty()) {
			throw new NoteNotFoundException("No label present");
		}
		if (sortOrder.equalsIgnoreCase("descending")) {
			List<LabelDTO> labelList = listOfLabels.stream().sorted(Comparator.comparing(LabelDTO::getCreatedAt))
					.map(filterStream -> modelMapper.map(filterStream, LabelDTO.class)).collect(Collectors.toList());
			Collections.reverse(labelList);
			return labelList;
		}
		
		List<LabelDTO> labelList = listOfLabels.stream().sorted(Comparator.comparing(LabelDTO::getLabelName))
				.collect(Collectors.toList());
		
		return labelList;
	}

}
