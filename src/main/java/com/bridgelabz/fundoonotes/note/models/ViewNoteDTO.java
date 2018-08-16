package com.bridgelabz.fundoonotes.note.models;

import java.util.Date;
import java.util.List;

public class ViewNoteDTO {

	private String id;
	
	private String title;
	
	private String description;
	
	private String color;
	
	private Date createdAt;
		
	private Date reminder;
	
	private boolean isTrash;
	
	private boolean pin;
	
	private boolean archive;
	
	private List<LabelDTO> labelList;
	
	private List<UrlMetaInfo> urlList;

	public List<UrlMetaInfo> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<UrlMetaInfo> urlList) {
		this.urlList = urlList;
	}

	public List<LabelDTO> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<LabelDTO> labelList) {
		this.labelList = labelList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isTrash() {
		return isTrash;
	}

	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}

	public boolean isPin() {
		return pin;
	}

	public void setPin(boolean pin) {
		this.pin = pin;
	}

	public boolean isArchive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}
	
	public Date getReminder() {
		return reminder;
	}

	public void setReminder(Date reminder) {
		this.reminder = reminder;
	}

	 
	
}
