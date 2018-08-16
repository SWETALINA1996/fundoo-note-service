package com.bridgelabz.fundoonotes.note.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.fundoonotes.note.models.Note;

public interface NoteRepository extends MongoRepository<Note , String>{

	//public List<ViewNoteDTO> findAllByUserId(String userId); 
	
	public List<Note> findAllByUserId(String userId); 
	
	public List<Note> findAllByUserIdAndIsTrash(String userId , boolean trash); 

	
	//public Optional<Note> findByUserId(String userId);

}
