//package com.bridgelabz.fundoonotes.model;
//
//import java.util.Arrays;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Lob;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "files")
//public class Files {
//	@Id
//	@Column(name = "fileId")
//	private int fileId;
//
//	@Column(name = "name")
//	private String name;
//
//	@Column(name = "type")
//	private String type;
//
//	@Column(name = "userId")
//	private int userId;
//
//	@Lob
//	@Column(name = "pic")
//	private byte[] pic;
//
//	public Files() {
//	}
//
//	public Files(String name, String type, byte[] pic, int userId) {
//		this.name = name;
//		this.type = type;
//		this.pic = pic;
//		this.userId = userId;
//	}
//
//	public int getFileId() {
//		return fileId;
//	}
//
//	public void setFileId(int fileId) {
//		this.fileId = fileId;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	public int getUserId() {
//		return userId;
//	}
//
//	public void setUserId(int userId) {
//		this.userId = userId;
//	}
//
//	public byte[] getPic() {
//		return pic;
//	}
//
//	public void setPic(byte[] pic) {
//		this.pic = pic;
//	}
//
//	@Override
//	public String toString() {
//		return "Files [fileId=" + fileId + ", name=" + name + ", type=" + type + ", userId=" + userId + ", pic="
//				+ Arrays.toString(pic) + "]";
//	}
//
//}
