package br.edu.ifes.locadora.controller;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.edu.ifes.locadora.domain.Item;
import br.edu.ifes.locadora.dto.ItemRequest;
import br.edu.ifes.locadora.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.*;
import br.edu.ifes.locadora.exceptions.ErroPadrao;
@RestController
@RequestMapping(value="/api/itens", produces="application/json")
@Tag(name="Item", description="Cadastro de itens do acervo.")
public class ItemController {
 private final ItemService service;
 public ItemController(ItemService service) { this.service=service; }
 @GetMapping
 @Operation(summary="Listar itens", responses={
 @ApiResponse(responseCode="200", description="Operacao realizada com sucesso."),
 @ApiResponse(responseCode="400", description="Dados invalidos.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="404", description="Registro ou relacionamento nao encontrado.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="409", description="Numero de serie duplicado ou registro com vinculos no acervo.", content=@Content(schema=@Schema(implementation=ErroPadrao.class)))})
 public List<Item> listar() { return service.listar(); }
 @GetMapping("/{id}")
 @Operation(summary="Consultar itens", responses={
 @ApiResponse(responseCode="200", description="Operacao realizada com sucesso."),
 @ApiResponse(responseCode="400", description="Dados invalidos.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="404", description="Registro ou relacionamento nao encontrado.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="409", description="Numero de serie duplicado ou registro com vinculos no acervo.", content=@Content(schema=@Schema(implementation=ErroPadrao.class)))})
 public Item consultar(@PathVariable Long id) { return service.consultar(id); }
 @PostMapping
 @Operation(summary="Incluir itens", responses={
 @ApiResponse(responseCode="201", description="Operacao realizada com sucesso."),
 @ApiResponse(responseCode="400", description="Dados invalidos.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="404", description="Registro ou relacionamento nao encontrado.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="409", description="Numero de serie duplicado ou registro com vinculos no acervo.", content=@Content(schema=@Schema(implementation=ErroPadrao.class)))})
 public ResponseEntity<Item> incluir(@Valid @RequestBody ItemRequest dados) { Item salvo=service.incluir(dados); return ResponseEntity.created(java.net.URI.create("/api/itens/"+salvo.getId())).body(salvo); }
 @PutMapping("/{id}")
 @Operation(summary="Alterar itens", responses={
 @ApiResponse(responseCode="200", description="Operacao realizada com sucesso."),
 @ApiResponse(responseCode="400", description="Dados invalidos.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="404", description="Registro ou relacionamento nao encontrado.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="409", description="Numero de serie duplicado ou registro com vinculos no acervo.", content=@Content(schema=@Schema(implementation=ErroPadrao.class)))})
 public Item alterar(@PathVariable Long id, @Valid @RequestBody ItemRequest dados) { return service.alterar(id,dados); }
 @DeleteMapping("/{id}")
 @Operation(summary="Excluir itens", responses={
 @ApiResponse(responseCode="204", description="Operacao realizada com sucesso."),
 @ApiResponse(responseCode="400", description="Dados invalidos.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="404", description="Registro ou relacionamento nao encontrado.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="409", description="Numero de serie duplicado ou registro com vinculos no acervo.", content=@Content(schema=@Schema(implementation=ErroPadrao.class)))})
 public ResponseEntity<Void> excluir(@PathVariable Long id) { service.excluir(id); return ResponseEntity.noContent().build(); }
}
