package dev.pedro.dao;

import dev.pedro.domain.Produto;
import dev.pedro.interfaces.Crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ProdutoDao implements Crud<Produto> {
    private final List<Produto> produtos = new ArrayList<>();

    @Override
    public void salvar(Produto produto) {
        Objects.requireNonNull(produto, "Produto obrigatório.");
        String codigo = normalizarCodigo(produto.getCodigo());
        if (codigo.isEmpty()) {
            throw new IllegalArgumentException("O código do produto é obrigatório.");
        }
        if (buscar(codigo) != null) {
            throw new IllegalArgumentException("Já existe um produto com esse código.");
        }
        produtos.add(produto);
    }

    @Override
    public List<Produto> listar() {
        return new ArrayList<>(produtos);
    }

    @Override
    public void atualizar(String chave, Produto produto) {
        Objects.requireNonNull(produto, "Produto atualizado obrigatório.");
        String codigoAntigo = normalizarCodigo(chave);
        String codigoNovo = normalizarCodigo(produto.getCodigo());
        int indice = indicePorCodigo(codigoAntigo);
        if (indice < 0) {
            throw new IllegalArgumentException("Produto não encontrado.");
        }
        if (codigoNovo.isEmpty()) {
            throw new IllegalArgumentException("O código do produto é obrigatório.");
        }
        for (int i = 0; i < produtos.size(); i++) {
            if (i != indice && normalizarCodigo(produtos.get(i).getCodigo()).equals(codigoNovo)) {
                throw new IllegalArgumentException("Outro produto já usa esse código.");
            }
        }
        produtos.set(indice, produto);
    }

    @Override
    public Produto buscar(String chave) {
        int indice = indicePorCodigo(normalizarCodigo(chave));
        return indice < 0 ? null : produtos.get(indice);
    }

    @Override
    public void excluir(String chave) {
        int indice = indicePorCodigo(normalizarCodigo(chave));
        if (indice >= 0) {
            produtos.remove(indice);
        }
    }

    private int indicePorCodigo(String codigo) {
        if (codigo.isEmpty()) return -1;
        for (int i = 0; i < produtos.size(); i++) {
            if (normalizarCodigo(produtos.get(i).getCodigo()).equals(codigo)) return i;
        }
        return -1;
    }

    private String normalizarCodigo(String codigo) {
        return codigo == null ? "" : codigo.trim().toUpperCase(Locale.ROOT);
    }
}
