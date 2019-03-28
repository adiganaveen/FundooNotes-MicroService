package com.bridgelabz.fundoonotes.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "Images")
public class Images {
	@Id
	@GeneratedValue
	@Column(name = "imagesId")
	private int imagesId;

	@Column(name = "noteId")
	private int noteId;

	@Lob
	@Column(name = "images")
	private byte[] images;

	public int getImagesId() {
		return imagesId;
	}

	public void setImagesId(int imagesId) {
		this.imagesId = imagesId;
	}

	public int getNoteId() {
		return noteId;
	}

	public Images setNoteId(int noteId) {
		this.noteId = noteId;
		return this;
	}

	public byte[] getImages() {
		return images;
	}

	public Images setImages(byte[] images) {
		this.images = images;
		return this;
	}

	@Override
	public String toString() {
		return "Images [imagesId=" + imagesId + ", noteId=" + noteId + ", images=" + Arrays.toString(images) + "]";
	}

}
