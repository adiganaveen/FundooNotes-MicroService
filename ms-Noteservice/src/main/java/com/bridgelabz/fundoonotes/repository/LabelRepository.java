package com.bridgelabz.fundoonotes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Integer> {
	List<Label> findAllByUserId(int userId);

	Optional<Label> findByLabelId(int labelId);

	Optional<Label> findByUserIdAndLabelId(int userId, int labelId);

}
