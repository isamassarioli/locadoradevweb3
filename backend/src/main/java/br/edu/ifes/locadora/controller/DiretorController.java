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
import br.edu.ifes.locadora.exceptions.ErroPadrao;
import br.edu.ifes.locadora.service.DiretorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/diretores", produces = "application/json")
@Tag(name = "DiretorController", description = "Fornece servicos web REST para acesso e manipulacao de dados de diretores.")
public class DiretorController {

    private final DiretorService service;

    public DiretorController(DiretorService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(description = "Retorna todos os diretores cadastrados.")
    public List<Diretor> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(
        description = "Dado o id, retorna o diretor correspondente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Caso o diretor seja encontrado."),
            @ApiResponse(responseCode = "404", description = "Caso nao exista diretor com o id informado.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public Diretor consultar(@PathVariable Long id) {
        return service.consultar(id);
    }

    @PostMapping
    @Operation(
        description = "Dados os dados do diretor, cadastra um novo diretor.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Caso o diretor seja incluido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Caso os dados do diretor sejam invalidos.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public ResponseEntity<Diretor> incluir(@Valid @RequestBody Diretor diretor) {
        Diretor salvo = service.incluir(diretor);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(
        description = "Dados o id e os novos dados, altera o diretor correspondente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Caso o diretor seja alterado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Caso os dados do diretor sejam invalidos.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class))),
            @ApiResponse(responseCode = "404", description = "Caso nao exista diretor com o id informado.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public Diretor alterar(@PathVariable Long id, @Valid @RequestBody Diretor diretor) {
        return service.alterar(id, diretor);
    }

    @DeleteMapping("/{id}")
    @Operation(
        description = "Dado o id, exclui o diretor correspondente.",
        responses = {
            @ApiResponse(responseCode = "409", description = "Registro vinculado a titulos do acervo.", content = @Content(schema = @Schema(implementation = ErroPadrao.class))),
            @ApiResponse(responseCode = "204", description = "Caso o diretor seja excluido com sucesso."),
            @ApiResponse(responseCode = "404", description = "Caso nao exista diretor com o id informado.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
