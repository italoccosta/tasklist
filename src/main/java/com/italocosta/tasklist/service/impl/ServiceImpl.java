package com.italocosta.tasklist.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void excluirTarefa(Integer ordem) {
        tarefaRepository.deleteByOrdemExibicao(ordem);
    }

    @Override
    public void atualizarTarefa(Long id, Tarefa novaTarefa) {
        var tarefaAntiga = tarefaRepository.findById(id);
        if (tarefaAntiga.isPresent()) {
            Tarefa nova = tarefaAntiga.get();
            nova.setNome(novaTarefa.getNome());
            nova.setCusto(novaTarefa.getCusto());
            nova.setDataLimite(novaTarefa.getDataLimite());
            tarefaRepository.save(nova);
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
