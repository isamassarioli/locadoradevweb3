package br.edu.ifes.locadora.controller;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.edu.ifes.locadora.domain.Titulo;
import br.edu.ifes.locadora.dto.TituloRequest;
import br.edu.ifes.locadora.service.TituloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.*;
import br.edu.ifes.locadora.exceptions.ErroPadrao;
@RestController
@RequestMapping(value="/api/titulos", produces="application/json")
@Tag(name="Titulo", description="Cadastro de titulos do acervo.")
public class TituloController {
 private final TituloService service;
 public TituloController(TituloService service) { this.service=service; }
 @GetMapping
 @Operation(summary="Listar titulos", responses={
 @ApiResponse(responseCode="200", description="Operacao realizada com sucesso."),
 @ApiResponse(responseCode="400", description="Dados invalidos.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="404", description="Registro ou relacionamento nao encontrado.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="409", description="Numero de serie duplicado ou registro com vinculos no acervo.", content=@Content(schema=@Schema(implementation=ErroPadrao.class)))})
 public List<Titulo> listar() { return service.listar(); }
 @GetMapping("/{id}")
 @Operation(summary="Consultar titulos", responses={
 @ApiResponse(responseCode="200", description="Operacao realizada com sucesso."),
 @ApiResponse(responseCode="400", description="Dados invalidos.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="404", description="Registro ou relacionamento nao encontrado.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="409", description="Numero de serie duplicado ou registro com vinculos no acervo.", content=@Content(schema=@Schema(implementation=ErroPadrao.class)))})
 public Titulo consultar(@PathVariable Long id) { return service.consultar(id); }
 @PostMapping
 @Operation(summary="Incluir titulos", responses={
 @ApiResponse(responseCode="201", description="Operacao realizada com sucesso."),
 @ApiResponse(responseCode="400", description="Dados invalidos.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="404", description="Registro ou relacionamento nao encontrado.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="409", description="Numero de serie duplicado ou registro com vinculos no acervo.", content=@Content(schema=@Schema(implementation=ErroPadrao.class)))})
 public ResponseEntity<Titulo> incluir(@Valid @RequestBody TituloRequest dados) { Titulo salvo=service.incluir(dados); return ResponseEntity.created(java.net.URI.create("/api/titulos/"+salvo.getId())).body(salvo); }
 @PutMapping("/{id}")
 @Operation(summary="Alterar titulos", responses={
 @ApiResponse(responseCode="200", description="Operacao realizada com sucesso."),
 @ApiResponse(responseCode="400", description="Dados invalidos.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="404", description="Registro ou relacionamento nao encontrado.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="409", description="Numero de serie duplicado ou registro com vinculos no acervo.", content=@Content(schema=@Schema(implementation=ErroPadrao.class)))})
 public Titulo alterar(@PathVariable Long id, @Valid @RequestBody TituloRequest dados) { return service.alterar(id,dados); }
 @DeleteMapping("/{id}")
 @Operation(summary="Excluir titulos", responses={
 @ApiResponse(responseCode="204", description="Operacao realizada com sucesso."),
 @ApiResponse(responseCode="400", description="Dados invalidos.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="404", description="Registro ou relacionamento nao encontrado.", content=@Content(schema=@Schema(implementation=ErroPadrao.class))),
 @ApiResponse(responseCode="409", description="Numero de serie duplicado ou registro com vinculos no acervo.", content=@Content(schema=@Schema(implementation=ErroPadrao.class)))})
 public ResponseEntity<Void> excluir(@PathVariable Long id) { service.excluir(id); return ResponseEntity.noContent().build(); }
}
