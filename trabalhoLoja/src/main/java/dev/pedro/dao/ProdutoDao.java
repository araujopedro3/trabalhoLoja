package dev.pedro.dao;
import dev.pedro.domain.Pessoa;
import dev.pedro.domain.Produto;
import dev.pedro.interfaces.Crud;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDao implements Crud<Produto> {
    private List<Produto> listarProdutos = new ArrayList<Produto>();

    @Override
    public void salvar(Produto produto){
        listarProdutos.add(produto);
    }

    @Override
    public List<Produto> listar() {
        return List.of();
    }

    @Override
    public void atualizar(int indice, Produto objeto) {

    }

    @Override
    public Pessoa buscar(int indice) {
        return null;
    }

    @Override
    public void excluir(int indice) {

    }

}
