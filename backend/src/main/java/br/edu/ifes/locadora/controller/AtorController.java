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
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifes.locadora.domain.Ator;
import br.edu.ifes.locadora.repository.AtorRepository;

@RestController
@RequestMapping("/api/atores")
public class AtorController {

    private final AtorRepository repository;

    public AtorController(AtorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Ator> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Ator consultar(@PathVariable Long id) {
        return buscarOuFalhar(id);
    }

    @PostMapping
    public ResponseEntity<Ator> incluir(@Valid @RequestBody Ator ator) {
        ator.setId(null);
        Ator salvo = repository.save(ator);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public Ator alterar(@PathVariable Long id, @Valid @RequestBody Ator ator) {
        Ator existente = buscarOuFalhar(id);
        existente.setNome(ator.getNome());
        return repository.save(existente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Ator existente = buscarOuFalhar(id);
        repository.delete(existente);
        return ResponseEntity.noContent().build();
    }

    private Ator buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ator nao encontrado (id " + id + ")."));
    }
}
