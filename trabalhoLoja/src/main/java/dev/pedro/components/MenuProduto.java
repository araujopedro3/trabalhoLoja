package dev.pedro.components;

import dev.pedro.dao.ProdutoDao;
import dev.pedro.domain.Eletrodomestico;
import dev.pedro.domain.Movel;
import dev.pedro.domain.Pessoa;
import dev.pedro.domain.Produto;

import java.math.BigDecimal;
import java.util.Scanner;

public final class MenuProduto {

    private MenuProduto() {
    }

    public static void exibir(Scanner scanner) {
        int opcao;
        ProdutoDao dao = new ProdutoDao();

        do {
            System.out.println("\n===== MENU PRODUTO =====");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Buscar produto");
            System.out.println("4 - Atualizar produto");
            System.out.println("5 - Deletar produto");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = MenuGeral.lerOpcao(scanner);

            switch (opcao) {
                case 1:
                    cadastrarProduto(scanner, dao);
                    break;

                case 2:
                    for (Produto produto : dao.listar()) {
                        System.out.println(produto.getNome() + "-" + produto.getCodigo() + "-" + produto.getPreco());
                    }
                    break;

                case 3:
                    System.out.print("Digite o codigo do produto: ");
                    String produtoBusca = scanner.nextLine().trim();
                    Produto produtoEncontrado = dao.buscar(produtoBusca);

                    if (produtoEncontrado == null) {
                        System.out.println("Produto não encontrado, por favor tente novamente");
                    } else
                        System.out.println("Nome: " + produtoEncontrado.getNome() +  " - Código: " + produtoEncontrado.getCodigo());
                    break;

                case 4:
                    System.out.println("Atualizar produto: ainda não implementado.");
                    break;

                case 5:
                    System.out.println("Deletar produto: ainda não implementado.");
                    break;

                case 0:
                    System.out.println("Voltando ao menu geral...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void cadastrarProduto(Scanner scanner, ProdutoDao dao) {
        System.out.println("Qual o tipo do produto (1 - Eletrodoméstico ou 2 - Móvel): ");
        int tipoProduto = Integer.parseInt(scanner.nextLine());

        Produto produto;

        if (tipoProduto == 1) {
            produto = new Eletrodomestico();
        } else if (tipoProduto == 2) {
            produto = new Movel();
        } else {
            System.out.println("Tipo inválido.");
            return;
        }

        System.out.println("Digite o código do produto: ");
        produto.setCodigo(scanner.nextLine());

        System.out.println("Digite o nome do produto: ");
        produto.setNome(scanner.nextLine());

        System.out.println("Digite o valor do produto (use ponto para os centavos): ");
        produto.setPreco(new BigDecimal(scanner.nextLine()));

        dao.salvar(produto);
        System.out.println("Produto cadastrado com sucesso!");
    }
}