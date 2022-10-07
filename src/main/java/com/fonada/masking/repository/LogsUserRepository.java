package com.fonada.masking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fonada.masking.entity.LogsUser;

@Repository
public interface LogsUserRepository extends JpaRepository<LogsUser, Integer> {

}
