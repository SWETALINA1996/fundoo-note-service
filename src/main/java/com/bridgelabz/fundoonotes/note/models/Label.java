package com.bridgelabz.fundoonotes.note.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.elasticsearch.annotations.Document;

//@Document(collection = "Labels")
@Document(indexName = "database", type = "label")
public class Label {
	
	@Id
	private String labelId;
	
	private String labelName;
	
	private String userId;
	
	private Date createdAt;

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
