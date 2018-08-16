package com.bridgelabz.fundoonotes.note.utility;

import com.bridgelabz.fundoonotes.note.exceptions.CreationException;
import com.bridgelabz.fundoonotes.note.models.CreateNoteDTO;
import com.bridgelabz.fundoonotes.note.models.LabelDTO;

public class Utility {

	public static void isNoteValidate(CreateNoteDTO noteDto) throws CreationException {

		if ((noteDto.getTitle() == null || noteDto.getTitle().trim().isEmpty()) && noteDto.getDescription() == null
				|| noteDto.getDescription().trim().isEmpty()) {
			throw new CreationException("Title and Description fields cannot be empty");
		}
	}

	public static void isLabelValidate(LabelDTO labelDto) throws CreationException {

		if ((labelDto.getLabelName() == null || labelDto.getLabelName().trim().isEmpty())) {
			throw new CreationException("Label name is mandatory to create a label");
		}
	}
}
