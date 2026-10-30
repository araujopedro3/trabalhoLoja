package dev.pedro.components;

import dev.pedro.dao.PessoaDao;
import dev.pedro.domain.Endereco;
import dev.pedro.domain.Pessoa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public final class MenuPessoa {

    private MenuPessoa() {
    }

    public static void exibir(Scanner scanner) {
        PessoaDao dao = new PessoaDao();
        int opcao;

        do {
            System.out.println("\n---- MENU CLIENTE ----");
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
                    cadastrarPessoa(scanner, dao);
                    break;

                case 2:
                    if (dao.listar().isEmpty()) {
                        System.out.println("Nenhum cliente cadastrado.");
                    } else {
                        System.out.println("\n--- Listagem de clientes ---");

                        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                        for (Pessoa pessoa : dao.listar()) {
                            System.out.println("Nome: " + pessoa.getNome());
                            System.out.println("Data de nascimento: "
                                    + pessoa.getDataNascimento().format(formato));
                            System.out.println("CPF: " + pessoa.getCpf());
                            System.out.println("Gênero: " + pessoa.getGenero());
                            System.out.println("Email: " + pessoa.getEmail());
                            System.out.println("Telefone: " + pessoa.getTelefone());

                            Endereco endereco = pessoa.getEndereco();
                            if (endereco != null) {
                                System.out.println("Endereço: " + endereco.getRua() + ", "
                                        + endereco.getNumero() + " - " + endereco.getCidade());
                            } else {
                                System.out.println("Endereço: não informado");
                            }
                        }
                    }
                    break;

                case 3:
                    System.out.print("Digite o CPF do cliente: ");
                    String cpfBusca = scanner.nextLine().trim();
                    Pessoa clienteEncontrado = dao.buscar(cpfBusca);

                    if (clienteEncontrado == null) {
                        System.out.println("Cliente não encontrado, por favor tente novamente");
                    } else
                        System.out.println("Nome: " + clienteEncontrado.getNome() +  " - CPF: " + clienteEncontrado.getCpf());
                    break;

                case 4:
                    System.out.println("Digite o CPF do cliente: ");
                    String cpfAtualiza =  scanner.nextLine().trim();
                    Pessoa clienteAtualiza = dao.buscar(cpfAtualiza);

                    if (clienteAtualiza == null) {
                        System.out.println("Cliente não encontrado, por favor tente novamente");
                    } else {
                        System.out.println("Nome: " + clienteAtualiza.getNome() + " - CPF: " + clienteAtualiza.getCpf());

                        System.out.println("Confirma a atualização do cadastro do Úsuario? (S/N");
                        String confirmaAtualizacao = scanner.nextLine().trim();

                        if (confirmaAtualizacao.equalsIgnoreCase("S")) {
                            System.out.print("Digite o novo nome: ");
                            String novoNome = scanner.nextLine().trim();
                            String nomeAntigo = clienteAtualiza.getNome();
                            clienteAtualiza.setNome(novoNome);
                            dao.atualizar(cpfAtualiza, clienteAtualiza);
                            System.out.println("Cliente atualizado com sucesso!");
                            System.out.println("Nome atualizado de: " + nomeAntigo + " para " +  novoNome);
                        } else {
                            System.out.println("Operação cancelada!");
                            System.out.println("Nenhum Úsuario foi atualizado!");
                        }
                    }
                    break;

                case 5:
                    System.out.println("Digite o CPF do cliente para a exclusão \uD83D\uDEA8: ");
                    String cpfDeleta = scanner.nextLine().trim();
                    Pessoa clienteDeletado = dao.buscar(cpfDeleta);

                    if (clienteDeletado == null) {
                        System.out.println("Cliente não encontrado, nenhum Úsuario foi deletado!");
                    } else {
                        System.out.println("Nome: " + clienteDeletado.getNome() + " - CPF: " + clienteDeletado.getCpf());

                        System.out.println("Confirma o excluimento do Úsuario? (S/N)");
                        String confirmaDeletar = scanner.nextLine().trim();
                            if (confirmaDeletar.equalsIgnoreCase("S")) {
                                dao.excluir(cpfDeleta);
                                System.out.println("Cliente excluido com sucesso!");
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

    private static void cadastrarPessoa(Scanner scanner, PessoaDao dao) {
        Pessoa pessoa = new Pessoa();

        System.out.print("Digite seu nome: ");
        pessoa.setNome(scanner.nextLine().trim());
        pessoa.setDataNascimento(lerData(scanner));

        System.out.print("Digite seu email: ");
        pessoa.setEmail(scanner.nextLine().trim());

        System.out.print("Digite seu telefone: ");
        pessoa.setTelefone(scanner.nextLine().trim());

        System.out.print("Digite seu gênero (M/F): ");
        pessoa.setGenero(scanner.nextLine().trim());

        System.out.print("Digite seu CPF: ");
        pessoa.setCpf(scanner.nextLine().trim());

        Endereco endereco = new Endereco();

        System.out.print("Digite sua cidade: ");
        endereco.setCidade(scanner.nextLine().trim());

        System.out.print("Digite sua rua: ");
        endereco.setRua(scanner.nextLine().trim());

        endereco.setNumero(lerNumero(scanner));

        pessoa.setEndereco(endereco);
        dao.salvar(pessoa);

        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static LocalDate lerData(Scanner scanner) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            System.out.print("Digite sua data de nascimento (DD/MM/AAAA): ");
            String entrada = scanner.nextLine().trim().replace('-', '/');

            try {
                return LocalDate.parse(entrada, formato);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Exemplo: 25/05/2008.");
            }
        }
    }

    private static int lerNumero(Scanner scanner) {
        while (true) {
            System.out.print("Digite o número do endereço: ");
            String entrada = scanner.nextLine().trim();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Número inválido. Digite um número inteiro.");
            }
        }
    }
}