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

import br.edu.ifes.locadora.domain.Cliente;
import br.edu.ifes.locadora.exceptions.ClienteNaoEncontradoException;
import br.edu.ifes.locadora.exceptions.ErroPadrao;
import br.edu.ifes.locadora.repository.ClienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/clientes", produces = "application/json")
@Tag(name = "ClienteController", description = "Fornece servicos web REST para acesso e manipulacao de dados de clientes.")
public class ClienteController {

    private final ClienteRepository repository;

    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(description = "Retorna todos os clientes cadastrados.")
    public List<Cliente> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
        description = "Dado o id, retorna o cliente correspondente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Caso o cliente seja encontrado."),
            @ApiResponse(responseCode = "404", description = "Caso nao exista cliente com o id informado.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public Cliente consultar(@PathVariable Long id) {
        return buscarOuFalhar(id);
    }

    @PostMapping
    @Operation(
        description = "Dados os dados do cliente (nome e CPF obrigatorios), cadastra um novo cliente.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Caso o cliente seja incluido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Caso os dados do cliente sejam invalidos.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public ResponseEntity<Cliente> incluir(@Valid @RequestBody Cliente cliente) {
        cliente.setId(null);
        Cliente salvo = repository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(
        description = "Dados o id e os novos dados, altera o cliente correspondente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Caso o cliente seja alterado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Caso os dados do cliente sejam invalidos.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class))),
            @ApiResponse(responseCode = "404", description = "Caso nao exista cliente com o id informado.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public Cliente alterar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        Cliente existente = buscarOuFalhar(id);
        existente.setNome(cliente.getNome());
        existente.setCpf(cliente.getCpf());
        existente.setEndereco(cliente.getEndereco());
        existente.setSexo(cliente.getSexo());
        existente.setTipo(cliente.getTipo());
        existente.setComunicados(cliente.isComunicados());
        existente.setObs(cliente.getObs());
        return repository.save(existente);
    }

    @DeleteMapping("/{id}")
    @Operation(
        description = "Dado o id, exclui o cliente correspondente.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Caso o cliente seja excluido com sucesso."),
            @ApiResponse(responseCode = "404", description = "Caso nao exista cliente com o id informado.",
                content = @Content(schema = @Schema(implementation = ErroPadrao.class)))
        }
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Cliente existente = buscarOuFalhar(id);
        repository.delete(existente);
        return ResponseEntity.noContent().build();
    }

    private Cliente buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Nao foi encontrado cliente com o ID informado (id " + id + ")."));
    }
}
