package com.bridgelabz.fundoonotes.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoonotes.model.Collaborator;

public interface CollaboratorRepository extends JpaRepository<Collaborator, Integer> {

}
