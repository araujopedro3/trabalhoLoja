package dev.pedro.dao;

import dev.pedro.domain.Pessoa;
import dev.pedro.interfaces.Crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PessoaDao implements Crud<Pessoa> {
    private final List<Pessoa> pessoas = new ArrayList<>();

    @Override
    public void salvar(Pessoa pessoa) {
        Objects.requireNonNull(pessoa, "Cliente obrigatório.");
        String cpf = normalizarCpf(pessoa.getCpf());
        if (cpf.length() != 11) {
            throw new IllegalArgumentException("O CPF deve conter 11 dígitos.");
        }
        if (buscar(cpf) != null) {
            throw new IllegalArgumentException("Já existe um cliente com esse CPF.");
        }
        pessoas.add(pessoa);
    }

    @Override
    public List<Pessoa> listar() {
        return new ArrayList<>(pessoas);
    }

    @Override
    public void atualizar(String chave, Pessoa pessoa) {
        Objects.requireNonNull(pessoa, "Cliente atualizado obrigatório.");
        String cpfAntigo = normalizarCpf(chave);
        String cpfNovo = normalizarCpf(pessoa.getCpf());
        int indice = indicePorCpf(cpfAntigo);
        if (indice < 0) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        if (cpfNovo.length() != 11) {
            throw new IllegalArgumentException("O CPF deve conter 11 dígitos.");
        }
        for (int i = 0; i < pessoas.size(); i++) {
            if (i != indice && normalizarCpf(pessoas.get(i).getCpf()).equals(cpfNovo)) {
                throw new IllegalArgumentException("Outro cliente já usa esse CPF.");
            }
        }
        pessoas.set(indice, pessoa);
    }

    @Override
    public Pessoa buscar(String chave) {
        int indice = indicePorCpf(normalizarCpf(chave));
        return indice < 0 ? null : pessoas.get(indice);
    }

    @Override
    public void excluir(String chave) {
        int indice = indicePorCpf(normalizarCpf(chave));
        if (indice >= 0) {
            pessoas.remove(indice);
        }
    }

    private int indicePorCpf(String cpf) {
        if (cpf.isEmpty()) return -1;
        for (int i = 0; i < pessoas.size(); i++) {
            if (normalizarCpf(pessoas.get(i).getCpf()).equals(cpf)) return i;
        }
        return -1;
    }

    private String normalizarCpf(String cpf) {
        return cpf == null ? "" : cpf.replaceAll("\\D", "");
    }
}
