package com.bridgelabz.fundoonotes.note.repositories;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.bridgelabz.fundoonotes.note.models.Note;

public interface NoteRepo extends ElasticsearchRepository<Note , String>{

	public List<Note> findAllByUserId(String userId); 
	
	public List<Note> findAllByUserIdAndIsTrash(String userId , boolean trash); 
}
