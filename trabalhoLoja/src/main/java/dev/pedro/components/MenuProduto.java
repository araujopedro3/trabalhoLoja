package dev.pedro.components;
import dev.pedro.domain.Eletrodomestico;
import dev.pedro.domain.Movel;
import dev.pedro.domain.Produto;
import dev.pedro.dao.ProdutoDao;

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
                    Produto produto = null;

                    System.out.println("Qual o tipo do produto (1 - Eletrodoméstico ou 2 - Móvel): ");
                    int tipoProduto = scanner.nextInt();

                    if (tipoProduto == 1) {
                        produto = new Eletrodomestico();
                    }
                    else if (tipoProduto == 2) {
                       produto = new Movel();
                    }
                    else {
                        System.out.println("Comando errado, tente novamente");
                        break;
                    }

                    System.out.println("Digite o código do produto: ");
                    produto.setCodigo(scanner.nextInt());

                    System.out.println("Digite o nome do produto: ");
                    produto.setNome(scanner.nextLine());

                    System.out.println("Digite o valor do produto: ");
                    produto.setPreco(BigDecimal.valueOf(scanner.nextDouble()));

                    dao.salvar(produto);
                    break;

                case 2:
                    System.out.println("Listar produtos");
                case 3:
                    System.out.println("Buscar produto");
                case 4: System.out.println("Atualizar produto");
                case 5: System.out.println("Deletar produto");
                case 0: System.out.println("Voltando ao menu geral...");
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
}