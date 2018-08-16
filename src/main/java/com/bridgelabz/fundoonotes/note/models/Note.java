package com.bridgelabz.fundoonotes.note.models;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.elasticsearch.annotations.Document;

//@Document(collection = "Notes")
@Document(indexName = "fundoouser", type = "note")
public class Note {

	@Id
	private String id;
	
	private String userId;

	private String title;
	
	private String description;
	
	private String color;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	private boolean isTrash;
	
	private boolean pin;
	
	private boolean archive;
	
	private Date reminder;
	//private Reminder reminder;
	
	List<LabelDTO> labelList;
	
	List<UrlMetaInfo> urlList;
	/*
	public Reminder getRemind() {
		return remind;
	}

	public void setRemind(Reminder remind) {
		this.remind = remind;
	}*/

	

	public List<LabelDTO> getLabelList() {
		return labelList;
	}

	public List<UrlMetaInfo> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<UrlMetaInfo> urlList) {
		this.urlList = urlList;
	}

	public void setLabelList(List<LabelDTO> labelList) {
		this.labelList = labelList;
	}

	public boolean isPin() {
		return pin;
	}

	public void setPin(boolean pin) {
		this.pin = pin;
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

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isTrash() {
		return isTrash;
	}

	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
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

	@Override
	public String toString() {
		return "Note [id=" + id + ", userId=" + userId + ", title=" + title + ", description=" + description
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
}
