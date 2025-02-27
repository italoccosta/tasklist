package com.italocosta.tasklist.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.italocosta.tasklist.domain.Tarefa;
import com.italocosta.tasklist.domain.repository.TarefaRepository;
import com.italocosta.tasklist.service.TarefaService;


@Service
@Transactional
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
    public void atualizarTarefa(Long id, Tarefa novaTarefa) {
        var tarefaAntiga = tarefaRepository.findById(id);
        if (tarefaAntiga.isPresent()) {
            if (validarNome(novaTarefa.getNome()) && !tarefaAntiga.get().getNome().equals(novaTarefa)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome já existe!");
            }
            Tarefa nova = tarefaAntiga.get();
            nova.setNome(novaTarefa.getNome());
            nova.setCusto(novaTarefa.getCusto());
            nova.setDataLimite(novaTarefa.getDataLimite());
            tarefaRepository.save(nova);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada.");
        }
    }

    @Override
    public void reordenarTarefa(Long id, Integer ordem) {
      var encontrarTarefa = tarefaRepository.findById(id);
      
      if (encontrarTarefa.isPresent()) {
        Tarefa tarefa = encontrarTarefa.get();
        Integer tarefaAntiga = tarefa.getOrdemExibicao();

        Iterable<Tarefa> tarefas = tarefaRepository.findAll();
        for (Tarefa t : tarefas) {
            if(t.getOrdemExibicao().equals(ordem)) {
                t.setOrdemExibicao(tarefaAntiga);
                tarefaRepository.save(t);
                break;
            }
        }

        tarefa.setOrdemExibicao(ordem);
        tarefaRepository.save(tarefa);
      }
    }

    @Override
    public boolean validarNome(String nome) {
        return tarefaRepository.existsByNome(nome);
    }
    
}
