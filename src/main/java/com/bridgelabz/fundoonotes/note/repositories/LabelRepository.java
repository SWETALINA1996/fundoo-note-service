package com.bridgelabz.fundoonotes.note.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.fundoonotes.note.models.Label;
import com.bridgelabz.fundoonotes.note.models.LabelDTO;

public interface LabelRepository extends MongoRepository<Label, String>{

	public Optional<Label> findByLabelName(String labelName);
	
	public Optional<Label> deleteByLabelName(String labelName);
	
	public Optional<Label> deleteByLabelId(String labelId);
	
	public Optional<Label> findByLabelNameAndUserId(String labelName , String userId);
	
	public List<LabelDTO> findAllByUserId(String userId); 
	
	public List<Label> findAllByLabelName(String userId); 
}
