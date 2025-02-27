package com.italocosta.tasklist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.italocosta.tasklist.domain.Tarefa;
import com.italocosta.tasklist.service.TarefaService;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController (TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping
    ResponseEntity<Tarefa> adicionar(@RequestBody Tarefa tarefa){
        if (tarefaService.validarNome(tarefa.getNome())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome da tarefa já existe!");
        } 
        tarefaService.novaTarefa(tarefa);
       return ResponseEntity.status(HttpStatus.CREATED).body(tarefa);
    }

    @GetMapping
    ResponseEntity<Iterable<Tarefa>> exibirTodas() {
        return ResponseEntity.ok(tarefaService.listarTarefas());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> excluirTarefa(@PathVariable Long id) {
        tarefaService.excluirTarefa(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualizar/{id}")
    ResponseEntity<Void> editarTarefa(@PathVariable Long id, @RequestBody Tarefa attTarefa) {
        tarefaService.atualizarTarefa(id, attTarefa);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reordenar/{id}")
    ResponseEntity<Void> reordenarTarefa(@PathVariable Long id, @RequestParam Integer novaOrdem) {
       tarefaService.reordenarTarefa(id, novaOrdem);
        return ResponseEntity.ok().build();
    }
    
}
