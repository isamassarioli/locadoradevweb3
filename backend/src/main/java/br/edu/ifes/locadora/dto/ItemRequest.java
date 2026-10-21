package br.edu.ifes.locadora.dto;
import java.time.LocalDate;
import jakarta.validation.constraints.*;
import br.edu.ifes.locadora.domain.TipoItem;
import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Exemplar fisico de um titulo. Numero de serie unico e data de aquisicao sem data futura.")
public record ItemRequest(@NotBlank String numSerie, @NotNull @PastOrPresent LocalDate dataAquisicao,
 @NotNull TipoItem tipoItem, @NotNull @Positive Long tituloId) {}
