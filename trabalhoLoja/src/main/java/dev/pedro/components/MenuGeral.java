package dev.pedro.components;

import dev.pedro.dao.PessoaDao;
import dev.pedro.dao.ProdutoDao;

import java.util.Scanner;

public final class MenuGeral {

    private MenuGeral() {
    }

    public static void exibir(Scanner scanner, PessoaDao pessoaDao, ProdutoDao produtoDao) {
        int opcao;

        do {
            System.out.println("\n---- MENU GERAL ----");
            System.out.println("1 - Clientes");
            System.out.println("2 - Produtos");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao(scanner);

            switch (opcao) {
                case 1 -> MenuPessoa.exibir(scanner, pessoaDao);
                case 2 -> MenuProduto.exibir(scanner, produtoDao);
                case 0 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    static int lerOpcao(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}