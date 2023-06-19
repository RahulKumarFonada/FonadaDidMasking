package com.fonada.masking.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.fonada.masking.entity.Roles;

public interface RolesRepository extends CrudRepository<Roles, Integer> {

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	Optional<Roles> findById(int id);

	Roles findByName(String name);
}
