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

import br.edu.ifes.locadora.domain.Classe;
import br.edu.ifes.locadora.exceptions.ErroPadrao;
import br.edu.ifes.locadora.service.ClasseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/classes", produces = "application/json")
@Tag(name = "ClasseController", description = "Fornece servicos web REST para acesso e manipulacao de dados de classes.")
public class ClasseController {

    private final ClasseService service;

    public ClasseController(ClasseService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(description = "Retorna todas as classes cadastradas.")
    public List<Classe> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(
        description = "Dado o id, retorna a classe correspondente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Caso a classe seja encontrada."),
            @ApiResponse(responseCode = "404", description = "Caso nao exista classe com o id informado.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public Classe consultar(@PathVariable Long id) {
        return service.consultar(id);
    }

    @PostMapping
    @Operation(
        description = "Dados os dados da classe, cadastra um nova classe.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Caso a classe seja incluida com sucesso."),
            @ApiResponse(responseCode = "400", description = "Caso os dados da classe sejam invalidos.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public ResponseEntity<Classe> incluir(@Valid @RequestBody Classe classe) {
        Classe salvo = service.incluir(classe);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(
        description = "Dados o id e os novos dados, altera a classe correspondente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Caso a classe seja alterada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Caso os dados da classe sejam invalidos.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class))),
            @ApiResponse(responseCode = "404", description = "Caso nao exista classe com o id informado.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public Classe alterar(@PathVariable Long id, @Valid @RequestBody Classe classe) {
        return service.alterar(id, classe);
    }

    @DeleteMapping("/{id}")
    @Operation(
        description = "Dado o id, exclui a classe correspondente.",
        responses = {
            @ApiResponse(responseCode = "409", description = "Registro vinculado a titulos do acervo.", content = @Content(schema = @Schema(implementation = ErroPadrao.class))),
            @ApiResponse(responseCode = "204", description = "Caso a classe seja excluida com sucesso."),
            @ApiResponse(responseCode = "404", description = "Caso nao exista classe com o id informado.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
