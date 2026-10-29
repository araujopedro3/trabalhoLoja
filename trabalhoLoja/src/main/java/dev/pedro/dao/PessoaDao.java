package dev.pedro.dao;

import dev.pedro.domain.Pessoa;
import dev.pedro.interfaces.Crud;

import java.util.ArrayList;
import java.util.List;

public class PessoaDao implements Crud<Pessoa> {
    private final List<Pessoa> listaPessoas = new ArrayList<>();

    @Override
    public void salvar(Pessoa pessoa) {
        listaPessoas.add(pessoa);
    }

    @Override
    public List<Pessoa> listar() {
        return new ArrayList<>(listaPessoas);
    }

    @Override
    public void atualizar(String chave, Pessoa pessoa) {
        for (int i = 0; i < listaPessoas.size(); i++) {
            if (listaPessoas.get(i).getCpf().equals(chave)) {
                listaPessoas.set(i, pessoa);
                return;
            }
        }
    }

    @Override
    public Pessoa buscar(String chave) {
        for (Pessoa pessoa : listaPessoas) {
            if (pessoa.getCpf().equals(chave)) {
                return pessoa;
            }
        }
        return null;
    }

    @Override
    public void excluir(String chave) {
        for (int i = 0; i < listaPessoas.size(); i++) {
            if (listaPessoas.get(i).getCpf().equals(chave)) {
                listaPessoas.remove(i);
                return;
            }
        }
    }

    public List<Pessoa> getListaPessoas() {
        return listaPessoas;
    }
}