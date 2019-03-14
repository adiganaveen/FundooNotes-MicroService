package com.bridgelabz.fundoonotes.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;

public interface NoteService {
	Note createNote(String token, Note note, HttpServletRequest request);

	List<Note> retrieveNote(String token, HttpServletRequest request);
	
//	List<Note> archiveNote(String token, HttpServletRequest request);

	Note updateNote(String token,int noteId, Note note, HttpServletRequest request);

	boolean deleteNote(String token, int noteId, HttpServletRequest request);

	Label createLabel(String token, Label label, HttpServletRequest request);

	Label updateLabel(String token, int labelId, Label label, HttpServletRequest request);

	boolean deleteLabel(String token, int labelId, HttpServletRequest request);

	List<Label> retrieveLabel(String token, HttpServletRequest request);

	boolean addNoteLabel( int noteId, Label label, HttpServletRequest request);

	boolean removeNoteLabel( int noteId, int labelId, HttpServletRequest request);
	
}
