package dev.pedro.components;
import dev.pedro.dao.PessoaDao;
import dev.pedro.domain.Pessoa;
import dev.pedro.domain.Endereco;

import java.time.LocalDate;
import java.util.Scanner;

public final class MenuPessoa {

    private MenuPessoa() {
    }

    public static void exibir(Scanner scanner) {
        PessoaDao dao = new PessoaDao();

        int opcao;

        do {
            System.out.println("\n===== MENU CLIENTE =====");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Listar clientes");
            System.out.println("3 - Buscar cliente");
            System.out.println("4 - Atualizar cliente");
            System.out.println("5 - Deletar cliente");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = MenuGeral.lerOpcao(scanner);

            switch (opcao) {
                case 1:
                    Pessoa pessoa = new Pessoa();
                    System.out.println("Digite seu nome: ");
                    pessoa.setNome(scanner.nextLine());

                    System.out.println("Digite sua data de nascimento em Ano/Mês/dia: ");
                    String dataNascimento = scanner.nextLine();
                    pessoa.setDataNascimento(LocalDate.parse(dataNascimento));

                    System.out.println("Digite seu email: ");
                    pessoa.setEmail(scanner.nextLine());

                    System.out.println("Digite seu telefone: ");
                    pessoa.setTelefone(scanner.nextLine());

                    System.out.println("Digite seu gênero M/F: ");
                    pessoa.setGenero(scanner.nextLine());

                    System.out.println("Digite seu cpf: ");
                    pessoa.setNome(scanner.nextLine());

                    Endereco endereco = new Endereco();
                    System.out.println("Digite seu cidade: ");
                    endereco.setCidade(scanner.nextLine());

                    System.out.println("Digite seu rua: ");
                    endereco.setRua(scanner.nextLine());

                    System.out.println("Digite seu numero: ");
                    endereco.setNumero(Integer.parseInt(scanner.nextLine()));

                    pessoa.setEndereco(endereco);
                    dao.salvar(pessoa);
                    break;

                case 2:
                    System.out.println("Listar clientes");
                case 3:
                    System.out.println("Buscar cliente");
                case 4:
                    System.out.println("Atualizar cliente");
                case 5:
                    System.out.println("Deletar cliente");
                case 0:
                    System.out.println("Voltando ao menu geral...");
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
}
