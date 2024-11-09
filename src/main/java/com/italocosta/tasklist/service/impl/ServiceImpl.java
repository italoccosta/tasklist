package com.italocosta.tasklist.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.italocosta.tasklist.domain.Tarefa;
import com.italocosta.tasklist.domain.repository.TarefaRepository;
import com.italocosta.tasklist.service.TarefaService;


@Service
public class ServiceImpl implements TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;


    @Override
    public Iterable<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    @Override
    public void novaTarefa(Tarefa tarefa) {
        tarefaRepository.save(tarefa);
    }

    @Override
    public void excluirTarefa(Long id) {
        tarefaRepository.deleteById(id);
    }

    @Override
    public Tarefa atualizarTarefa(Long id, Tarefa tarefa) {
        return null;
    }

    @Override
    public void reordenarTarefa(Integer ordem) {
    }

    @Override
    public boolean validarNome(String nome) {
        return tarefaRepository.existsByNome(nome);
    }
    
}
