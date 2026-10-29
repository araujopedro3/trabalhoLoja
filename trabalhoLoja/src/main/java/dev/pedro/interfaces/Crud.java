package dev.pedro.interfaces;

import java.util.List;

public interface Crud<T> {

    void salvar(T objeto);

    List<T> listar();

    void atualizar(String chave, T objeto);

    T buscar(String chave);

    void excluir(String chave);
}
