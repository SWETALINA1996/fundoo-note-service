package com.bridgelabz.fundoonotes.note.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NoteConfig {

	@Bean
	//@Scope("prototype")
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
