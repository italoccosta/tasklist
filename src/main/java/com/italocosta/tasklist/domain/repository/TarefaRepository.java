package com.italocosta.tasklist.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.italocosta.tasklist.domain.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    
}
