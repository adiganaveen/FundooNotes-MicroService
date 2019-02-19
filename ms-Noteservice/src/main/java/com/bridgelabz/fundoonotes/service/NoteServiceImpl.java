package com.bridgelabz.fundoonotes.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.utility.TokenGenerator;

@Service
public class NoteServiceImpl implements NoteService {

	private static Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private TokenGenerator tokenGenerator;

	@Override
	public Note createNote(String token, Note note, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		note.setUserId(userId);
		noteRepository.save(note);
		return note;
	}

	@Override
	public List<Note> retrieveNote(String token, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		List<Note> notes = noteRepository.findAllByUserId(userId);
		if (!notes.isEmpty()) {
			return notes;
		}
		return null;
	}

	@Override
	public Note updateNote(String token, int noteId, Note note, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<Note> optional = noteRepository.findById(noteId);
		if (optional.isPresent()) {
			Note newNote = optional.get();
			if (newNote.getUserId() == userId) {
				Note updatedNote = newNoteUpdate(newNote, note);
				noteRepository.save(updatedNote);
				return updatedNote;
			}
		}
		return null;
	}

	public Note newNoteUpdate(Note newNote, Note note) {
		if (note.getTitle() != null)
			newNote.setTitle(note.getTitle());
		if (note.getDescription() != null)
			newNote.setDescription(note.getDescription());
		if (note.isArchive() != false)
			newNote.setArchive(note.isArchive());
		if (note.isPinned() != false)
			newNote.setPinned(note.isPinned());
		if (note.isInTrash() != false)
			newNote.setInTrash(note.isInTrash());
		return newNote;
	}

	@Override
	public Note deleteNote(String token, int noteId, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<Note> optional = noteRepository.findById(noteId);
		if (optional.isPresent()) {
			Note newNote = optional.get();
			if (newNote.getUserId() == userId)
				noteRepository.delete(newNote);
			return newNote;
		}
		return null;
	}

	@Override
	public Label createLabel(String token, Label label, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		label.setUserId(userId);
		labelRepository.save(label);
		return label;
	}

	@Override
	public List<Label> retrieveLabel(String token, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		List<Label> labels = labelRepository.findAllByUserId(userId);
		if (!labels.isEmpty()) {
			return labels;
		}
		return null;
	}

	@Override
	public Label updateLabel(String token, int labelId, Label label, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<Label> optional = labelRepository.findById(labelId);
		if (optional.isPresent()) {
			Label newLabel = optional.get();
			if (newLabel.getUserId() == userId) {
				if (label.getLabelName() != null)
					newLabel.setLabelName(label.getLabelName());
				labelRepository.save(newLabel);
				return newLabel;
			}
		}
		return null;
	}

	@Override
	public Label deleteLabel(String token, int labelId, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<Label> optional = labelRepository.findById(labelId);
		if (optional.isPresent()) {
			Label newLabel = optional.get();
			if (newLabel.getUserId() == userId)
				labelRepository.delete(newLabel);
			return newLabel;
		}
		return null;
	}

	@Override
	public boolean addNoteLabel(String token, int noteId, int labelId, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<Note> optionalNote = noteRepository.findById(noteId);
		Optional<Label> optionalLabel = labelRepository.findById(labelId);
		if (optionalNote.isPresent() && optionalLabel.isPresent()) {
			Note note = optionalNote.get();
			Label label = optionalLabel.get();
			if (note.getUserId() == userId && label.getUserId() == userId) {
				List<Label> labels = note.getLabels();
				labels.add(label);
				if (!labels.isEmpty()) {
					note.setLabels(labels);
					noteRepository.save(note);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean removeNoteLabel(String token, int noteId, int labelId, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<Note> optionalNote = noteRepository.findById(noteId);
		Optional<Label> optionalLabel = labelRepository.findById(labelId);
		if (optionalNote.isPresent() && optionalLabel.isPresent()) {
			Note note = optionalNote.get();
			Label label = optionalLabel.get();
			if (note.getUserId() == userId && label.getUserId() == userId) {
				List<Label> labels = note.getLabels();
				if (!labels.isEmpty()) {
					labels = labels.stream().filter(newLabel -> newLabel.getLabelId() != labelId)
							.collect(Collectors.toList());
					note.setLabels(labels);
					noteRepository.save(note);
					return true;
				}
			}
		}
		return false;
	}
}
