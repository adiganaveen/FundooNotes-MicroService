package com.bridgelabz.fundoonotes.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.model.Collaborator;
import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.repository.CollaboratorRepository;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.utility.TokenGenerator;

@Service
public class NoteServiceImpl implements NoteService {

	private static Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private CollaboratorRepository collaboratorRepository;

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
		if (!notes.isEmpty())
			return notes;
		return null;
	}

	@Override
	public Note updateNote(String token, int noteId, Note note, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		logger.info("note " + noteId);
		Optional<Note> maybeNote = noteRepository.findByUserIdAndNoteId(userId, noteId);
		return maybeNote
				.map(existingNote -> noteRepository
						.save(existingNote.setTitle(note.getTitle()).setDescription(note.getDescription())
								.setArchive(note.isArchive()).setInTrash(note.isInTrash()).setColor(note.getColor()).setPinned(note.isPinned())))
				.orElseGet(() -> null);
	}

	@Override
	public boolean deleteNote(String token, int noteId, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<Note> maybeNote = noteRepository.findByUserIdAndNoteId(userId, noteId);
		return maybeNote.map(existingNote -> {
			noteRepository.delete(existingNote);
			return true;
		}).orElseGet(() -> false);
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
		if (!labels.isEmpty())
			return labels;
		return null;
	}

	@Override
	public Label updateLabel(String token, int labelId, Label label, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<Label> maybeLabel = labelRepository.findByUserIdAndLabelId(userId, labelId);
		return maybeLabel.map(existingLabel -> labelRepository.save(existingLabel.setLabelName(label.getLabelName())))
				.orElseGet(() -> null);
	}

	@Override
	public boolean deleteLabel(String token, int labelId, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		Optional<Label> maybeLabel = labelRepository.findByUserIdAndLabelId(userId, labelId);
		return maybeLabel.map(existingLabel -> {
			labelRepository.delete(existingLabel);
			return true;
		}).orElseGet(() -> false);
	}

	@Override
	public boolean addNoteLabel(int noteId, Label oldLabel, HttpServletRequest request) {
		Note note = noteRepository.findByNoteId(noteId);
		Label label = labelRepository.findByLabelId(oldLabel.getLabelId());
		if (note != null && label != null) {
			List<Label> labels = note.getLabels();
			labels.add(label);
			note.setLabels(labels);
			noteRepository.save(note);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeNoteLabel(int noteId, int labelId, HttpServletRequest request) {
		Note note = noteRepository.findByNoteId(noteId);
		Label label = labelRepository.findByLabelId(labelId);
		if (note != null && label != null) {
			List<Label> labels = note.getLabels();
			if (!labels.isEmpty()) {
				labels = labels.stream().filter(newLabel -> newLabel.getLabelId() != labelId)
						.collect(Collectors.toList());
				note.setLabels(labels);
				noteRepository.save(note);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean createCollaborator(String token, int noteId, int userId) {
		Collaborator collaborator = new Collaborator();
		collaborator = collaboratorRepository.save(collaborator.setNoteId(noteId).setUserId(userId));
		if (collaborator != null)
			return true;
		return false;
	}

}
