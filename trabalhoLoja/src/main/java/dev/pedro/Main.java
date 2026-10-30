package dev.pedro;

import dev.pedro.components.MenuGeral;
import dev.pedro.dao.PessoaDao;
import dev.pedro.dao.ProdutoDao;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            PessoaDao pessoaDao = new PessoaDao();
            ProdutoDao produtoDao = new ProdutoDao();
            MenuGeral.exibir(scanner, pessoaDao, produtoDao);
        }
    }
}