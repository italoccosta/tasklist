package com.italocosta.tasklist.service;

import com.italocosta.tasklist.domain.Tarefa;

public interface TarefaService {
    
    Iterable<Tarefa> listarTarefas();
    void novaTarefa(Tarefa tarefa);
    void excluirTarefa(Long id);
    void atualizarTarefa(Long id, Tarefa tarefa);
    void reordenarTarefa(Long id, Integer ordem);
    boolean validarNome(String nome);
    
}
