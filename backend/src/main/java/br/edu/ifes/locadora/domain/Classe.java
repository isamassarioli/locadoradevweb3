package br.edu.ifes.locadora.domain;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.DecimalMin;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do classe e obrigclasseio.")
    private String nome;

    public Classe() {
    }

    public Classe(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @NotNull(message = "O valor e obrigatorio.")
    @DecimalMin(value = "0.01", message = "O valor deve ser positivo.")
    private BigDecimal valor;

    @NotNull(message = "O prazo de devolucao e obrigatorio.")
    @Positive(message = "O prazo de devolucao deve ser positivo.")
    private Integer prazoDevolucao;

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public Integer getPrazoDevolucao() { return prazoDevolucao; }
    public void setPrazoDevolucao(Integer prazoDevolucao) { this.prazoDevolucao = prazoDevolucao; }
}
