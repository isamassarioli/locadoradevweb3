package br.edu.ifes.locadora.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifes.locadora.domain.Diretor;
import br.edu.ifes.locadora.service.DiretorService;

@RestController
@RequestMapping("/api/diretores")
public class DiretorController {

    private final DiretorService service;

    public DiretorController(DiretorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Diretor> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Diretor consultar(@PathVariable Long id) {
        return service.consultar(id);
    }

    @PostMapping
    public ResponseEntity<Diretor> incluir(@Valid @RequestBody Diretor diretor) {
        Diretor salvo = service.incluir(diretor);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public Diretor alterar(@PathVariable Long id, @Valid @RequestBody Diretor diretor) {
        return service.alterar(id, diretor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
