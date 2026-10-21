package br.edu.ifes.locadora.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Titulo {
 @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 public Long getId() { return id; }
 public void setId(Long id) { this.id = id; }
 @NotBlank
 private String nome;
 public String getNome() { return nome; }
 public void setNome(String nome) { this.nome = nome; }
 @NotNull @Min(1888) @Max(2100)
 private Integer ano;
 public Integer getAno() { return ano; }
 public void setAno(Integer ano) { this.ano = ano; }
 @NotBlank @Size(max = 5000) @Column(length = 5000)
 private String sinopse;
 public String getSinopse() { return sinopse; }
 public void setSinopse(String sinopse) { this.sinopse = sinopse; }
 @NotBlank
 private String categoria;
 public String getCategoria() { return categoria; }
 public void setCategoria(String categoria) { this.categoria = categoria; }
 @NotNull @ManyToOne(optional = false)
 private Diretor diretor;
 public Diretor getDiretor() { return diretor; }
 public void setDiretor(Diretor diretor) { this.diretor = diretor; }
 @NotNull @ManyToOne(optional = false)
 private Classe classe;
 public Classe getClasse() { return classe; }
 public void setClasse(Classe classe) { this.classe = classe; }
 @NotEmpty @ManyToMany
 private java.util.List<Ator> atores;
 public java.util.List<Ator> getAtores() { return atores; }
 public void setAtores(java.util.List<Ator> atores) { this.atores = atores; }
}
