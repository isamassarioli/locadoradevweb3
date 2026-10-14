package br.edu.ifes.locadora.domain;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da classe e obrigatorio.")
    private String nome;

    @NotNull(message = "O valor da classe e obrigatorio.")
    @PositiveOrZero(message = "O valor nao pode ser negativo.")
    private BigDecimal valor;

    // Quantidade de dias que o cliente tem para devolver o item
    @NotNull(message = "O prazo de devolucao e obrigatorio.")
    @Positive(message = "O prazo de devolucao deve ser de pelo menos 1 dia.")
    private Integer prazoDevolucao;

    public Classe() {
    }

    public Classe(String nome, BigDecimal valor, Integer prazoDevolucao) {
        this.nome = nome;
        this.valor = valor;
        this.prazoDevolucao = prazoDevolucao;
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

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getPrazoDevolucao() {
        return prazoDevolucao;
    }

    public void setPrazoDevolucao(Integer prazoDevolucao) {
        this.prazoDevolucao = prazoDevolucao;
    }
}
