package com.bridgelabz.fundoonotes.note.models;

import java.util.Date;
import java.util.List;

public class CreateNoteDTO {

	private String title;
	
	private String description;
	
	private String color = "#FFFFFF";

	private Date createdAt;
	
	private Date reminder;
	
	private boolean pin;
	
	private String labelId;
	
	private boolean isTrash;
	
	private boolean archive;
	
	//List<String> labelList;
	
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


	
	
	//private Reminder remind;

	//Label
	
	/*public Reminder getRemind() {
		return remind;
	}

	public void setRemind(Reminder remind) {
		this.remind = remind;
	}*/

	/*public List<String> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<String> labelList) {
		this.labelList = labelList;
	}*/
	public boolean isTrash() {
		return isTrash;
	}

	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}
	
	public String getColor() {
		return color;
	}

	public Date getReminder() {
		return reminder;
	}

	public void setReminder(Date reminder) {
		this.reminder = reminder;
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
	
	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	@Override
	public String toString() {
		return "CreateNoteDTO [title=" + title + ", description=" + description + "]";
	}
	
}
