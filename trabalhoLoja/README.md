# Sistema de Gerenciamento de Loja

Projeto desenvolvido para a disciplina **Fundamentos de Orientação a Objetos**.

## Descrição

O sistema permite gerenciar clientes e produtos de uma loja por meio de um menu executado no terminal. O usuário pode cadastrar, listar, buscar, atualizar e excluir registros.

## Funcionalidades

### Clientes

- Cadastrar clientes
- Listar clientes
- Buscar cliente pelo CPF
- Atualizar dados do cliente
- Excluir cliente

### Produtos

- Cadastrar produtos
- Listar produtos
- Buscar produto pelo código
- Atualizar produto
- Excluir produto

Os produtos podem ser dos tipos **Eletrodoméstico** e **Móvel**.

## Organização do projeto

O código está organizado em pacotes:

- `components`: menus e interação com o usuário
- `dao`: operações de cadastro, busca, atualização, listagem e exclusão
- `domain`: classes que representam as entidades do sistema
- `interfaces`: contratos compartilhados pelas classes

## Conceitos de orientação a objetos utilizados

- **Classes e objetos:** representam elementos do sistema, como pessoas e produtos.
- **Encapsulamento:** os dados das classes são acessados por métodos getters e setters.
- **Herança:** tipos específicos de produto reutilizam características da classe `Produto`.
- **Abstração:** a classe `Produto` representa os dados e comportamentos comuns dos produtos.
- **Interfaces:** definem operações comuns de cadastro e consulta.
- **DAO:** concentra as operações de acesso e manipulação dos dados.

## Armazenamento

Os dados são mantidos em listas durante a execução do programa. Ao encerrar a aplicação, os registros cadastrados deixam de estar disponíveis.

## Como executar

1. Abra o projeto na IDE, como o IntelliJ IDEA.
2. Localize a classe `Main`.
3. Execute o método `main`.
4. Use as opções exibidas no terminal para navegar pelo sistema.