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
            System.out.println("\n---- MENU PRODUTO ----");
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
                    System.out.print("Digite o código do produto que deseja atualizar: ");
                    String codigoAtualiza = scanner.nextLine().trim();
                    Produto produtoAtualiza = dao.buscar(codigoAtualiza);

                    if (produtoAtualiza == null) {
                        System.out.println("Produto não encontrado.");
                        break;
                    }

                    System.out.println("Produto: " + produtoAtualiza.getNome()
                            + " - Código: " + produtoAtualiza.getCodigo());

                    System.out.print("Confirma a atualização? (S/N): ");
                    String confirmaAtualizacao = scanner.nextLine().trim();

                    if (confirmaAtualizacao.equalsIgnoreCase("S")) {
                        System.out.print("Digite o novo nome: ");
                        produtoAtualiza.setNome(scanner.nextLine().trim());

                        System.out.print("Digite o novo preço: ");
                        produtoAtualiza.setPreco(new BigDecimal(scanner.nextLine().trim()));

                        dao.atualizar(codigoAtualiza, produtoAtualiza);
                        System.out.println("Produto atualizado com sucesso!");
                    } else {
                        System.out.println("Atualização cancelada.");
                    }
                    break;

                case 5:
                    System.out.print("Digite o código do produto que deseja excluir: ");
                    String codigoDeleta = scanner.nextLine().trim();
                    Produto produtoDeletar = dao.buscar(codigoDeleta);

                    if (produtoDeletar == null) {
                        System.out.println("Produto não encontrado.");
                    } else {
                        System.out.println("Produto: " + produtoDeletar.getNome()
                                + " - Código: " + produtoDeletar.getCodigo());

                        System.out.print("Confirma a exclusão? (S/N): ");
                        String confirmaDeletar = scanner.nextLine().trim();

                        if (confirmaDeletar.equalsIgnoreCase("S")) {
                            dao.excluir(codigoDeleta);
                            System.out.println("Produto excluído com sucesso!");
                        } else {
                            System.out.println("Exclusão cancelada.");
                        }
                    }
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