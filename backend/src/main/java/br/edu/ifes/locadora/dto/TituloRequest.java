package br.edu.ifes.locadora.dto;
import java.util.List;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Dados de titulo. Relacionamentos usam IDs de cadastros existentes.")
public record TituloRequest(@NotBlank String nome, @NotNull @Min(1888) @Max(2100) Integer ano,
 @NotBlank @Size(max = 5000) String sinopse, @NotBlank String categoria,
 @NotNull @Positive Long diretorId, @NotNull @Positive Long classeId,
 @NotEmpty List<@NotNull @Positive Long> atoresIds) {}
