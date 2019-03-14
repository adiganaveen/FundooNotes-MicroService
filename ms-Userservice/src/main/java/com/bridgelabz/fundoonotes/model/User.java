package com.bridgelabz.fundoonotes.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


@Entity
@Table(name = "User")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "emailId", unique = true)
	private String emailId;

	@Column(name = "password", length = 60)
	private String password;

	@Column(name = "mobileNumber")
	private long mobileNumber;

	@Column(name = "activation_status")
	private boolean activationStatus;
	
	@Lob
	@Column(name = "profilePicture")
	private byte[] profilePicture;

	public int getId() {
		return id;
	}

	public User setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
	}

	public boolean isActivationStatus() {
		return activationStatus;
	}

	public User setActivationStatus(boolean activationStatus) {
		this.activationStatus = activationStatus;
		return this;
	}

	public String getEmailId() {
		return emailId;
	}

	public User setEmailId(String emailId) {
		this.emailId = emailId;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}
	
	public long getMobileNumber() {
		return mobileNumber;
	}

	public User setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
		return this;
	}

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public User setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
		return this;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", emailId=" + emailId + ", password=" + password
				+ ", mobileNumber=" + mobileNumber + ", activationStatus=" + activationStatus + "]";
	}



	

}
