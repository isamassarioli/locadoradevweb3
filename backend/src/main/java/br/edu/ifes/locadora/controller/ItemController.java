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

import br.edu.ifes.locadora.domain.Item;
import br.edu.ifes.locadora.dto.ItemRequest;
import br.edu.ifes.locadora.exceptions.ErroPadrao;
import br.edu.ifes.locadora.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/itens", produces = "application/json")
@Tag(name = "ItemController", description = "Fornece servicos web REST para acesso e manipulacao de dados de itens.")
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(description = "Retorna todo o itens cadastrado.")
    public List<Item> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(
        description = "Dado o id, retorna o item correspondente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Caso o item seja encontrado."),
            @ApiResponse(responseCode = "404", description = "Caso nao exista item com o id informado.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public Item consultar(@PathVariable Long id) {
        return service.consultar(id);
    }

    @PostMapping
    @Operation(
        description = "Dados os dados do item, cadastra um novo item.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Caso o item seja incluido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Caso os dados do item sejam invalidos.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class))),
            @ApiResponse(responseCode = "404", description = "Caso titulo informado nao exista.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class))),
            @ApiResponse(responseCode = "409", description = "Caso o numero de serie ja esteja cadastrado.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public ResponseEntity<Item> incluir(@Valid @RequestBody ItemRequest dados) {
        Item salvo = service.incluir(dados);
        return ResponseEntity.created(URI.create("/api/itens/" + salvo.getId())).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(
        description = "Dados o id e os novos dados, altera o item correspondente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Caso o item seja alterado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Caso os dados do item sejam invalidos.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class))),
            @ApiResponse(responseCode = "404", description = "Caso nao exista item com o id informado ou titulo inexistente.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class))),
            @ApiResponse(responseCode = "409", description = "Caso o numero de serie ja esteja cadastrado.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public Item alterar(@PathVariable Long id, @Valid @RequestBody ItemRequest dados) {
        return service.alterar(id, dados);
    }

    @DeleteMapping("/{id}")
    @Operation(
        description = "Dado o id, exclui o item correspondente.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Caso o item seja excluido com sucesso."),
            @ApiResponse(responseCode = "404", description = "Caso nao exista item com o id informado.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
