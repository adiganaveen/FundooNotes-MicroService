package com.bridgelabz.fundoonotes.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.service.NoteService;

@Controller
@RequestMapping("/user/note/")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@PostMapping(value = "createnote")
	public ResponseEntity<?> createNote(@RequestHeader("token") String token, @RequestBody Note note,
			HttpServletRequest request) {
		if (noteService.createNote(token, note, request) != null)
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("There was a issue raised cannot create a note", HttpStatus.CONFLICT);
	}

	@GetMapping(value = "/notes")
	public ResponseEntity<?> getNotes(@RequestHeader("token") String token, HttpServletRequest request) {
		List<Note> notes = noteService.getNotes(token, request);
		if (!notes.isEmpty())
			return new ResponseEntity<List<Note>>(notes, HttpStatus.OK);
		return new ResponseEntity<String>("Please enter the value note id or verify your login", HttpStatus.NOT_FOUND);
	}

	@PutMapping(value = "/{noteId:.+}")
	public ResponseEntity<?> updateNote(@RequestHeader("token") String token, @PathVariable("noteId") int noteId,
			@RequestBody Note note, HttpServletRequest request) {
		Note newNote = noteService.updateNote(token, noteId, note, request);
		if (newNote != null)
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("Please enter the value note id or verify your login", HttpStatus.CONFLICT);
	}

	@DeleteMapping(value = "/{noteId:.+}")
	public ResponseEntity<?> deleteNote(@RequestHeader("token") String token, @PathVariable("noteId") int noteId,
			HttpServletRequest request) {
		if (noteService.deleteNote(token, noteId, request))
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("Please enter the value note id or verify your login", HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/label")
	public ResponseEntity<?> createLabel(@RequestHeader("token") String token, @RequestBody Label label,
			HttpServletRequest request) {
		Label newLabel = noteService.createLabel(token, label, request);
		if (newLabel != null)
			return new ResponseEntity<Label>(newLabel, HttpStatus.OK);
		return new ResponseEntity<String>("There was a issue raised cannot create a note", HttpStatus.CONFLICT);
	}

	@GetMapping(value = "labels")
	public ResponseEntity<?> getLabels(@RequestHeader("token") String token, HttpServletRequest request) {
		List<Label> labels = noteService.getLabels(token, request);
		if (!labels.isEmpty())
			return new ResponseEntity<List<Label>>(labels, HttpStatus.OK);
		return new ResponseEntity<String>("Please enter the value note id or verify your login", HttpStatus.NOT_FOUND);
	}

	@PutMapping(value = "/label/{labelId:.+}")
	public ResponseEntity<?> updateUser(@RequestHeader("token") String token, @PathVariable("labelId") int labelId,
			@RequestBody Label label, HttpServletRequest request) {
		Label newLabel = noteService.updateLabel(token, labelId, label, request);
		if (newLabel != null)
			return new ResponseEntity<Label>(newLabel, HttpStatus.OK);
		return new ResponseEntity<String>("User id given is not present or Note yet been activated",
				HttpStatus.NOT_FOUND);
	}

	@DeleteMapping(value = "/label/{labelId:.+}")
	public ResponseEntity<?> deleteLabel(@RequestHeader("token") String token, @PathVariable("labelId") int labelId,
			HttpServletRequest request) {
		if (noteService.deleteLabel(token, labelId, request))
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("User id given is not present or Note yet been activated",
				HttpStatus.NOT_FOUND);
	}

	@PutMapping(value = "/labelnote/{noteId:.+}")
	public ResponseEntity<?> addNoteLabel(@PathVariable(value = "noteId") int noteId, @RequestBody Label label,
			HttpServletRequest request) {
		if (noteService.addNoteLabel(noteId, label, request))
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("User id given is not present or Note yet been activated",
				HttpStatus.NOT_FOUND);
	}

	@DeleteMapping(value = "labelnote")
	public ResponseEntity<?> removeNoteLabel(@RequestParam("noteId") int noteId, @RequestParam("labelId") int labelId,
			HttpServletRequest request) {
		if (noteService.removeNoteLabel(noteId, labelId, request))
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("User id given is not present or Note yet been activated",
				HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "collaborator/{noteId}/{userId}")
	public ResponseEntity<?> createCollaborator(@RequestHeader("token") String token,
			@PathVariable("noteId") int noteId, @PathVariable("userId") int userId, HttpServletRequest request) {
		if (noteService.createCollaborator(token, noteId, userId))
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("There was a issue raised cannot create a collaborator", HttpStatus.CONFLICT);
	}

	@DeleteMapping("collaborator/{userId}/{noteId}")
	public ResponseEntity<?> removeCollaborator(@PathVariable("userId") int userId,
			@PathVariable("noteId") int noteId) {
		if (noteService.removeCollaborator(userId, noteId))
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("Couldnot delete the image", HttpStatus.CONFLICT);
	}

	@PostMapping(value = "photo/{noteId}")
	public ResponseEntity<?> storeFile(@RequestParam("file") MultipartFile file, @PathVariable("noteId") int noteId)
			throws IOException {
		if (noteService.store(file, noteId))
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("Error in uploading the image", HttpStatus.CONFLICT);
	}

	@DeleteMapping("photo/{imagesId}")
	public ResponseEntity<?> deleteFile(@PathVariable("imagesId") int imagesId) {
		if (noteService.deleteFile(imagesId))
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("Couldnot delete the image", HttpStatus.CONFLICT);
	}

}
