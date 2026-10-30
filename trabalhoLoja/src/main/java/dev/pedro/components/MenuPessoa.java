package dev.pedro.components;

import dev.pedro.dao.PessoaDao;
import dev.pedro.domain.Endereco;
import dev.pedro.domain.Pessoa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public final class MenuPessoa {
    private static final DateTimeFormatter DATA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private MenuPessoa() {
    }

    public static void exibir(Scanner scanner, PessoaDao dao) {
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
                case 1 -> cadastrar(scanner, dao);
                case 2 -> listar(dao);
                case 3 -> buscar(scanner, dao);
                case 4 -> atualizar(scanner, dao);
                case 5 -> excluir(scanner, dao);
                case 0 -> System.out.println("Voltando ao menu geral...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void cadastrar(Scanner scanner, PessoaDao dao) {
        System.out.println("\n--- Cadastro de cliente ---");
        String nome = textoObrigatorio(scanner, "Nome: ");
        LocalDate nascimento = data(scanner, "Data de nascimento (DD/MM/AAAA): ");
        String email = textoObrigatorio(scanner, "Email: ");
        String telefone = textoObrigatorio(scanner, "Telefone: ");
        String genero = genero(scanner);
        String cpf = cpf(scanner, "CPF (11 dígitos): ");
        if (dao.buscar(cpf) != null) {
            System.out.println("Já existe um cliente cadastrado com esse CPF.");
            return;
        }
        Endereco endereco = new Endereco(
                textoObrigatorio(scanner, "Rua: "),
                numero(scanner, "Número do endereço: "),
                textoObrigatorio(scanner, "Cidade: ")
        );
        dao.salvar(new Pessoa(nome, nascimento, email, telefone, genero, cpf,
                endereco, LocalDate.now(), null));
        System.out.println("Cliente cadastrado com sucesso.");
    }

    private static void listar(PessoaDao dao) {
        var pessoas = dao.listar();
        if (pessoas.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        System.out.println("\n--- Clientes cadastrados ---");
        pessoas.forEach(p -> System.out.println(formatar(p)));
    }

    private static void buscar(Scanner scanner, PessoaDao dao) {
        Pessoa pessoa = dao.buscar(cpf(scanner, "CPF do cliente: "));
        if (pessoa == null) {
            System.out.println("Cliente não encontrado.");
        } else {
            System.out.println(formatar(pessoa));
        }
    }

    private static void atualizar(Scanner scanner, PessoaDao dao) {
        String chave = cpf(scanner, "CPF do cliente que deseja atualizar: ");
        Pessoa atual = dao.buscar(chave);
        if (atual == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        System.out.println("Cadastro atual: " + formatar(atual));
        if (!confirmar(scanner, "Confirma a atualização? (S/N): ")) {
            System.out.println("Atualização cancelada.");
            return;
        }

        Endereco enderecoAtual = atual.getEndereco() == null
                ? new Endereco("", 0, "") : atual.getEndereco();
        String nome = textoOpcional(scanner, "Nome", atual.getNome());
        LocalDate nascimento = dataOpcional(scanner, atual.getDataNascimento());
        String email = textoOpcional(scanner, "Email", atual.getEmail());
        String telefone = textoOpcional(scanner, "Telefone", atual.getTelefone());
        String genero = generoOpcional(scanner, atual.getGenero());
        String novoCpf = cpfOpcional(scanner, atual.getCpf());
        String rua = textoOpcional(scanner, "Rua", enderecoAtual.getRua());
        int numero = numeroOpcional(scanner, enderecoAtual.getNumero());
        String cidade = textoOpcional(scanner, "Cidade", enderecoAtual.getCidade());

        Pessoa atualizada = new Pessoa(nome, nascimento, email, telefone, genero, novoCpf,
                new Endereco(rua, numero, cidade), atual.getDataCriacao(), atual.getCriadoPor());
        try {
            dao.atualizar(chave, atualizada);
            System.out.println("Cliente atualizado com sucesso.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void excluir(Scanner scanner, PessoaDao dao) {
        String chave = cpf(scanner, "CPF do cliente que deseja excluir: ");
        Pessoa pessoa = dao.buscar(chave);
        if (pessoa == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        System.out.println("Cliente: " + formatar(pessoa));
        if (confirmar(scanner, "Confirma a exclusão? (S/N): ")) {
            dao.excluir(chave);
            System.out.println("Cliente excluído com sucesso.");
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

    private static String formatar(Pessoa p) {
        Endereco e = p.getEndereco();
        String endereco = e == null ? "Não informado"
                : e.getRua() + ", " + e.getNumero() + " - " + e.getCidade();
        return "Nome: " + p.getNome() + " | Nascimento: " + p.getDataNascimento().format(DATA)
                + " | CPF: " + p.getCpf() + " | Gênero: " + p.getGenero()
                + " | Email: " + p.getEmail() + " | Telefone: " + p.getTelefone()
                + " | Endereço: " + endereco;
    }

    private static String textoObrigatorio(Scanner s, String pergunta) {
        while (true) {
            System.out.print(pergunta);
            String valor = s.nextLine().trim();
            if (!valor.isEmpty()) return valor;
            System.out.println("Este campo é obrigatório.");
        }
    }

    private static String textoOpcional(Scanner s, String campo, String atual) {
        System.out.print(campo + " [" + (atual == null ? "" : atual) + "] (Enter mantém): ");
        String valor = s.nextLine().trim();
        return valor.isEmpty() ? atual : valor;
    }

    private static LocalDate data(Scanner s, String pergunta) {
        while (true) {
            System.out.print(pergunta);
            try {
                return LocalDate.parse(s.nextLine().trim().replace('-', '/'), DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Use DD/MM/AAAA.");
            }
        }
    }

    private static LocalDate dataOpcional(Scanner s, LocalDate atual) {
        while (true) {
            System.out.print("Nascimento [" + atual.format(DATA) + "] (Enter mantém): ");
            String valor = s.nextLine().trim();
            if (valor.isEmpty()) return atual;
            try {
                return LocalDate.parse(valor.replace('-', '/'), DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Use DD/MM/AAAA.");
            }
        }
    }

    private static String genero(Scanner s) {
        while (true) {
            System.out.print("Gênero (M/F/O): ");
            String valor = s.nextLine().trim().toUpperCase();
            if (valor.equals("M") || valor.equals("F") || valor.equals("O")) return valor;
            System.out.println("Informe M, F ou O.");
        }
    }

    private static String generoOpcional(Scanner s, String atual) {
        while (true) {
            System.out.print("Gênero (M/F/O) [" + atual + "] (Enter mantém): ");
            String valor = s.nextLine().trim().toUpperCase();
            if (valor.isEmpty()) return atual;
            if (valor.equals("M") || valor.equals("F") || valor.equals("O")) return valor;
            System.out.println("Informe M, F ou O.");
        }
    }

    private static String cpf(Scanner s, String pergunta) {
        while (true) {
            System.out.print(pergunta);
            String valor = s.nextLine().replaceAll("\\D", "");
            if (valor.length() == 11) return valor;
            System.out.println("CPF inválido. Informe 11 dígitos.");
        }
    }

    private static String cpfOpcional(Scanner s, String atual) {
        while (true) {
            System.out.print("CPF [" + atual + "] (Enter mantém): ");
            String valor = s.nextLine().trim();
            if (valor.isEmpty()) return atual;
            valor = valor.replaceAll("\\D", "");
            if (valor.length() == 11) return valor;
            System.out.println("CPF inválido. Informe 11 dígitos.");
        }
    }

    private static int numero(Scanner s, String pergunta) {
        while (true) {
            System.out.print(pergunta);
            try {
                int valor = Integer.parseInt(s.nextLine().trim());
                if (valor >= 0) return valor;
            } catch (NumberFormatException ignored) {
                // Repetir a pergunta.
            }
            System.out.println("Informe um inteiro igual ou maior que zero.");
        }
    }

    private static int numeroOpcional(Scanner s, int atual) {
        while (true) {
            System.out.print("Número [" + atual + "] (Enter mantém): ");
            String entrada = s.nextLine().trim();
            if (entrada.isEmpty()) return atual;
            try {
                int valor = Integer.parseInt(entrada);
                if (valor >= 0) return valor;
            } catch (NumberFormatException ignored) {
                // Repetir a pergunta.
            }
            System.out.println("Informe um inteiro igual ou maior que zero.");
        }
    }

    private static boolean confirmar(Scanner s, String pergunta) {
        while (true) {
            System.out.print(pergunta);
            String valor = s.nextLine().trim();
            if (valor.equalsIgnoreCase("S")) return true;
            if (valor.equalsIgnoreCase("N")) return false;
            System.out.println("Responda S ou N.");
        }
    }
}
