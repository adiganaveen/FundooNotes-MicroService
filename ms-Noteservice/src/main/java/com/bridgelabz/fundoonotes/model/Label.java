package com.bridgelabz.fundoonotes.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Label")
public class Label implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "labelId")
	private int labelId;

	@Column(name = "labelName")
	private String labelName;

	@Column(name = "userId")
	private int userId;

	public int getLabelId() {
		return labelId;
	}

	public Label setLabelId(int labelId) {
		this.labelId = labelId;
		return this;
	}

	public String getLabelName() {
		return labelName;
	}

	public Label setLabelName(String labelName) {
		this.labelName = labelName;
		return this;
	}

	public int getUserId() {
		return userId;
	}

	public Label setUserId(int userId) {
		this.userId = userId;
		return this;
	}

	@Override
	public String toString() {
		return "Label [labelId=" + labelId + ", labelName=" + labelName + ", userId=" + userId + "]";
	}

}
