package com.italocosta.tasklist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.italocosta.tasklist.domain.Tarefa;
import com.italocosta.tasklist.service.TarefaService;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private TarefaService tarefaService;

    public TarefaController (TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping
    ResponseEntity<Tarefa> adicionar(@RequestBody Tarefa tarefa){
        if (tarefaService.validarNome(tarefa.getNome())) {
            throw new IllegalArgumentException();
        } 
        tarefaService.novaTarefa(tarefa);
       return ResponseEntity.ok(tarefa);
    }

    @GetMapping
    ResponseEntity<Iterable<Tarefa>> exibirTodas() {
        return ResponseEntity.ok(tarefaService.listarTarefas());
    }

    @DeleteMapping
    ResponseEntity<Void> excluirTarefa(Integer ordem) {
        tarefaService.excluirTarefa(ordem);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/atualizar/{id}")
    ResponseEntity<Void> editarTarefa(@PathVariable Long id, @RequestBody Tarefa attTarefa) {
        tarefaService.atualizarTarefa(id, attTarefa);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reordenar/{id}")
    ResponseEntity<Void> reordenarTarefa(@PathVariable Long id, Integer novaOrdem) {
       tarefaService.reordenarTarefa(id, novaOrdem);
        return ResponseEntity.ok().build();
    }
    
}
