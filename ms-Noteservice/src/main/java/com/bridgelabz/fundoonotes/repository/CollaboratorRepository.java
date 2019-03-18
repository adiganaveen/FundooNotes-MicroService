package com.bridgelabz.fundoonotes.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoonotes.model.Collaborator;

public interface CollaboratorRepository extends JpaRepository<Collaborator, Integer> {
	
	Optional<Collaborator> findByNoteIdAndUserId(int noteId,int userId);
	
	List<Collaborator> findAllByUserId(int userId);

}
