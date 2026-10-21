package br.edu.ifes.locadora.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Item {
 @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 public Long getId() { return id; }
 public void setId(Long id) { this.id = id; }
 @NotBlank @Column(unique = true)
 private String numSerie;
 public String getNumSerie() { return numSerie; }
 public void setNumSerie(String numSerie) { this.numSerie = numSerie; }
 @NotNull @PastOrPresent
 private java.time.LocalDate dataAquisicao;
 public java.time.LocalDate getDataAquisicao() { return dataAquisicao; }
 public void setDataAquisicao(java.time.LocalDate dataAquisicao) { this.dataAquisicao = dataAquisicao; }
 @NotNull @Enumerated(EnumType.STRING)
 private TipoItem tipoItem;
 public TipoItem getTipoItem() { return tipoItem; }
 public void setTipoItem(TipoItem tipoItem) { this.tipoItem = tipoItem; }
 @NotNull @ManyToOne(optional = false)
 private Titulo titulo;
 public Titulo getTitulo() { return titulo; }
 public void setTitulo(Titulo titulo) { this.titulo = titulo; }
}
