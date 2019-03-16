package com.bridgelabz.fundoonotes.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "Collaborator", uniqueConstraints = @UniqueConstraint(columnNames = { "userId", "noteId" }))
public class Collaborator {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "colabId")
	private int colabId;

	@Column(name = "userId")
	private int userId;

	@Column(name = "noteId")
	private int noteId;

	@Column(name = "updatedTime")
	@UpdateTimestamp
	private Timestamp updatedTime;

	public int getColabId() {
		return colabId;
	}

	public Collaborator setColabId(int colabId) {
		this.colabId = colabId;
		return this;
	}

	public int getUserId() {
		return userId;
	}

	public Collaborator setUserId(int userId) {
		this.userId = userId;
		return this;
	}

	public int getNoteId() {
		return noteId;
	}

	public Collaborator setNoteId(int noteId) {
		this.noteId = noteId;
		return this;
	}

	public Timestamp getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Collaborator [colabId=" + colabId + ", userId=" + userId + ", noteId=" + noteId + ", updatedTime="
				+ updatedTime + "]";
	}

}
