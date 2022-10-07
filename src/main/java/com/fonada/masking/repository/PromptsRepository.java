package com.fonada.masking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fonada.masking.entity.PromptEntity;

@Repository
public interface PromptsRepository extends JpaRepository<PromptEntity, Integer> {

}
