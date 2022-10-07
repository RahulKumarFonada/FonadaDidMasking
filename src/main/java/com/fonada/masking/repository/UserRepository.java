package com.fonada.masking.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fonada.masking.entity.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {

	Users findByEmailAndIsActive(String email, String isActive);

	Users findByEmailAndUsernameAndIsActive(String email, String username, String isActive);

	Users findByUsernameAndIsActive(String userName, String isActive);

	List<Users> findAllByIsActive(String isActive);

	Users findByUsername(String userName);

	Users findByEmail(String email);

	Users findByUsernameAndPassword(String userName, String password);

	Users findByIdAndIsActive(Integer id, String isActive);

}
