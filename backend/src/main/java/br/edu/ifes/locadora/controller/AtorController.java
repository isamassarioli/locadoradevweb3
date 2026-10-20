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

import br.edu.ifes.locadora.domain.Ator;
import br.edu.ifes.locadora.exceptions.AtorNaoEncontradoException;
import br.edu.ifes.locadora.exceptions.ErroPadrao;
import br.edu.ifes.locadora.repository.AtorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/atores", produces = "application/json")
@Tag(name = "AtorController", description = "Fornece servicos web REST para acesso e manipulacao de dados de atores.")
public class AtorController {

    private final AtorRepository repository;

    public AtorController(AtorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(description = "Retorna todos os atores cadastrados.")
    public List<Ator> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
        description = "Dado o id, retorna o ator correspondente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Caso o ator seja encontrado."),
            @ApiResponse(responseCode = "404", description = "Caso nao exista ator com o id informado.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public Ator consultar(@PathVariable Long id) {
        return buscarOuFalhar(id);
    }

    @PostMapping
    @Operation(
        description = "Dados os dados do ator, cadastra um novo ator.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Caso o ator seja incluido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Caso os dados do ator sejam invalidos.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public ResponseEntity<Ator> incluir(@Valid @RequestBody Ator ator) {
        ator.setId(null);
        Ator salvo = repository.save(ator);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(
        description = "Dados o id e os novos dados, altera o ator correspondente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Caso o ator seja alterado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Caso os dados do ator sejam invalidos.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class))),
            @ApiResponse(responseCode = "404", description = "Caso nao exista ator com o id informado.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public Ator alterar(@PathVariable Long id, @Valid @RequestBody Ator ator) {
        Ator existente = buscarOuFalhar(id);
        existente.setNome(ator.getNome());
        return repository.save(existente);
    }

    @DeleteMapping("/{id}")
    @Operation(
        description = "Dado o id, exclui o ator correspondente.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Caso o ator seja excluido com sucesso."),
            @ApiResponse(responseCode = "404", description = "Caso nao exista ator com o id informado.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Ator existente = buscarOuFalhar(id);
        repository.delete(existente);
        return ResponseEntity.noContent().build();
    }

    private Ator buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AtorNaoEncontradoException("Nao foi encontrado ator com o ID informado (id " + id + ")."));
    }
}
