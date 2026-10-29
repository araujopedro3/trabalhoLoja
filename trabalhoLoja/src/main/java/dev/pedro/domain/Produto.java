package dev.pedro.domain;

import java.math.BigDecimal;
import java.util.Objects;

/** Modelo minimo proposto; ajustar os campos conforme o enunciado do trabalho. */
public abstract class Produto extends Base {
    private String codigo;
    private String nome;
    private BigDecimal preco;

    public Produto(String codigo, String nome, BigDecimal preco) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("O codigo e obrigatorio.");
        }
        this.codigo = codigo;
        setNome(nome);
        setPreco(preco);
    }

    public Produto() {

    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome e obrigatorio.");
        }
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        Objects.requireNonNull(preco, "O preco e obrigatorio.");
        if (preco.signum() < 0) {
            throw new IllegalArgumentException("O preco nao pode ser negativo.");
        }
        this.preco = preco;
    }

    public void setCodigo(String  codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("O codigo é obrigatório.");
        }
        this.codigo = codigo;
    }
}