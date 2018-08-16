package com.bridgelabz.fundoonotes.note.models;

public class UrlMetaInfo {

	String link;
	
	String imageURL;
	
	String description;
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getImageURL() {
		return imageURL;
	}
	
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return "UrlMetaInfo [link=" + link + ", imageURL=" + imageURL + ", description=" + description + "]";
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
