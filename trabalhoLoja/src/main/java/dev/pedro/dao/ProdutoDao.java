package dev.pedro.dao;

import dev.pedro.domain.Produto;
import dev.pedro.interfaces.Crud;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDao implements Crud<Produto> {
    private final List<Produto> produtos = new ArrayList<>();

    @Override
    public void salvar(Produto produto) {
        produtos.add(produto);
    }

    @Override
    public List<Produto> listar() {
        return new ArrayList<>(produtos);
    }

    @Override
    public void atualizar(String chave, Produto produto) {
        for(int i = 0; i < produtos.size(); i++) {
            if(produtos.get(i).getCodigo().equals(produto.getCodigo())) {
                produtos.set(i, produto);
                return;
            }
        }
    }

    @Override
    public Produto buscar(String chave) {
        for (Produto produto : produtos) {
            if (produto.getCodigo().equals(chave)) {
                return produto;
            }
        }
        return null;
    }

    @Override
    public void excluir(String chave) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getCodigo().equals(chave)) {
            produtos.remove(i);
            return;
            }
        }
        }
    }