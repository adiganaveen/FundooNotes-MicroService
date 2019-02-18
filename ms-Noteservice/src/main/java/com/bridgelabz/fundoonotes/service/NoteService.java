package com.bridgelabz.fundoonotes.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;

public interface NoteService {
	Note createNote(String token, Note note, HttpServletRequest request);

	List<Note> retrieveNote(String token, HttpServletRequest request);

	Note updateNote(String token, int noteId, Note note, HttpServletRequest request);

	Note deleteNote(String token, int noteId, HttpServletRequest request);

	Label createLabel(String token, Label label, HttpServletRequest request);

	Label updateLabel(String token, int labelId, Label label, HttpServletRequest request);

	Label deleteLabel(String token, int labelId, HttpServletRequest request);

	List<Label> retrieveLabel(String token, HttpServletRequest request);
	
	boolean addNoteLabel(String token, int noteId, int labelId, HttpServletRequest request);


}
