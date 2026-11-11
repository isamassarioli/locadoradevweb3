package br.edu.ifes.locadora.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifes.locadora.domain.Titulo;
import br.edu.ifes.locadora.dto.TituloRequest;
import br.edu.ifes.locadora.exceptions.ErroPadrao;
import br.edu.ifes.locadora.service.TituloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/titulos", produces = "application/json")
@Tag(name = "TituloController", description = "Fornece servicos web REST para acesso e manipulacao de dados de titulos.")
public class TituloController {

    private final TituloService service;

    public TituloController(TituloService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(description = "Retorna todo o titulos cadastrado.")
    public List<Titulo> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(
        description = "Dado o id, retorna o titulo correspondente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Caso o titulo seja encontrado."),
            @ApiResponse(responseCode = "404", description = "Caso nao exista titulo com o id informado.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public Titulo consultar(@PathVariable Long id) {
        return service.consultar(id);
    }

    @PostMapping
    @Operation(
        description = "Dados os dados do titulo, cadastra um novo titulo.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Caso o titulo seja incluido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Caso os dados do titulo sejam invalidos.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class))),
            @ApiResponse(responseCode = "404", description = "Caso diretor, classe ou ator informado nao exista.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public ResponseEntity<Titulo> incluir(@Valid @RequestBody TituloRequest dados) {
        Titulo salvo = service.incluir(dados);
        return ResponseEntity.created(URI.create("/api/titulos/" + salvo.getId())).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(
        description = "Dados o id e os novos dados, altera o titulo correspondente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Caso o titulo seja alterado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Caso os dados do titulo sejam invalidos.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class))),
            @ApiResponse(responseCode = "404", description = "Caso nao exista titulo com o id informado ou diretor, classe ou ator inexistente.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public Titulo alterar(@PathVariable Long id, @Valid @RequestBody TituloRequest dados) {
        return service.alterar(id, dados);
    }

    @DeleteMapping("/{id}")
    @Operation(
        description = "Dado o id, exclui o titulo correspondente.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Caso o titulo seja excluido com sucesso."),
            @ApiResponse(responseCode = "404", description = "Caso nao exista titulo com o id informado.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class))),
            @ApiResponse(responseCode = "409", description = "Caso o titulo possua itens cadastrados.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
