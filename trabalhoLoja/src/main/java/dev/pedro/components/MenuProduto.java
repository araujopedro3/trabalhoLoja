package dev.pedro.components;

import dev.pedro.dao.ProdutoDao;
import dev.pedro.domain.*;
import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class MenuProduto {
    private MenuProduto() {}

    public static void exibir(Scanner s, ProdutoDao dao) {
        int op;
        do {
            System.out.println("\n---- MENU PRODUTO ----\n1 - Cadastrar produto\n2 - Listar produtos\n3 - Buscar produto\n4 - Atualizar produto\n5 - Deletar produto\n0 - Voltar");
            System.out.print("Escolha uma opção: "); op = MenuGeral.lerOpcao(s);
            switch (op) {
                case 1 -> cadastrar(s, dao);
                case 2 -> { if (dao.listar().isEmpty()) System.out.println("Nenhum produto cadastrado."); else dao.listar().forEach(p -> System.out.println(formatar(p))); }
                case 3 -> { Produto p = dao.buscar(texto(s, "Código do produto: ")); if (p == null) System.out.println("Produto não encontrado."); else System.out.println(formatar(p)); }
                case 4 -> atualizar(s, dao);
                case 5 -> excluir(s, dao);
                case 0 -> System.out.println("Voltando ao menu geral...");
                default -> System.out.println("Opção inválida!");
            }
        } while (op != 0);
    }

    private static void cadastrar(Scanner s, ProdutoDao dao) {
        System.out.println("Tipo: 1 - Eletrodoméstico | 2 - Móvel");
        int tipo; do { System.out.print("Opção: "); tipo = MenuGeral.lerOpcao(s); } while (tipo != 1 && tipo != 2);
        Produto p = tipo == 1 ? new Eletrodomestico() : new Movel();
        preencherBasico(s, p, false);
        preencherDetalhes(s, p);
        try { dao.salvar(p); System.out.println("Produto cadastrado com sucesso."); }
        catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
    }

    private static void atualizar(Scanner s, ProdutoDao dao) {
        String chave = texto(s, "Código do produto que deseja atualizar: ");
        Produto antigo = dao.buscar(chave);
        if (antigo == null) { System.out.println("Produto não encontrado."); return; }
        System.out.println(formatar(antigo));
        if (!confirmar(s, "Confirma a atualização? (S/N): ")) { System.out.println("Atualização cancelada."); return; }
        Produto novo = copia(antigo);
        novo.setCodigo(opcional(s, "Código", antigo.getCodigo()));
        novo.setNome(opcional(s, "Nome", antigo.getNome()));
        novo.setPreco(preco(s, "Preço", antigo.getPreco()));
        if (confirmar(s, "Atualizar também as características específicas? (S/N): ")) preencherDetalhes(s, novo);
        try { dao.atualizar(chave, novo); System.out.println("Produto atualizado com sucesso."); }
        catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
    }

    private static Produto copia(Produto p) {
        Produto n;
        if (p instanceof Movel m) {
            Movel x = new Movel(); x.setMaterial(m.getMaterial()); x.setDescricao(m.getDescricao());
            x.setDimensaoLargura(m.getDimensaoLargura()); x.setDimensaoComprimento(m.getDimensaoComprimento()); x.setDimensaoAltura(m.getDimensaoAltura());
            if (m.getMarca() != null) x.setMarca(m.getMarca()); x.setCaracteristicas(m.getCaracteristicas()); n = x;
        } else {
            Eletrodomestico x = new Eletrodomestico();
            if (((Eletrodomestico)p).getMarca() != null) x.setMarca(((Eletrodomestico)p).getMarca());
            x.setCaracteristicas(((Eletrodomestico)p).getCaracteristicas()); n = x;
        }
        n.setCodigo(p.getCodigo()); n.setNome(p.getNome()); n.setPreco(p.getPreco());
        n.setCriadoPor(p.getCriadoPor()); n.setDataCriacao(p.getDataCriacao()); return n;
    }

    private static void preencherBasico(Scanner s, Produto p, boolean manter) {
        p.setCodigo(manter ? opcional(s, "Código", p.getCodigo()) : textoObrigatorio(s, "Código: "));
        p.setNome(manter ? opcional(s, "Nome", p.getNome()) : textoObrigatorio(s, "Nome: "));
        p.setPreco(manter ? preco(s, "Preço", p.getPreco()) : precoNovo(s));
    }

    private static void preencherDetalhes(Scanner s, Produto p) {
        if (p instanceof Movel m) {
            m.setMaterial(textoObrigatorio(s, "Material: ")); m.setDescricao(textoObrigatorio(s, "Descrição: "));
            m.setDimensaoLargura(dim(s, "Largura (cm): ")); m.setDimensaoComprimento(dim(s, "Comprimento (cm): ")); m.setDimensaoAltura(dim(s, "Altura (cm): "));
            MarcaMovel[] marcas = MarcaMovel.values(); m.setMarca(escolher(s, "Marca do móvel", marcas));
            m.setCaracteristicas(caracteristicas(s, CaracteristicaMovel.values(), CaracteristicaMovel.class));
        } else if (p instanceof Eletrodomestico e) {
            e.setMarca(escolher(s, "Marca do eletrodoméstico", MarcaEletrodomestico.values()));
            e.setCaracteristicas(caracteristicas(s, CaracteristicaEletrodomestico.values(), CaracteristicaEletrodomestico.class));
        }
    }

    private static void excluir(Scanner s, ProdutoDao dao) {
        String codigo = texto(s, "Código do produto que deseja excluir: "); Produto p = dao.buscar(codigo);
        if (p == null) { System.out.println("Produto não encontrado."); return; }
        System.out.println(formatar(p));
        if (confirmar(s, "Confirma a exclusão? (S/N): ")) { dao.excluir(codigo); System.out.println("Produto excluído com sucesso."); }
        else System.out.println("Exclusão cancelada.");
    }

    private static String formatar(Produto p) {
        String r = "Código: " + p.getCodigo() + " | Nome: " + p.getNome() + " | Preço: R$ " + p.getPreco();
        if (p instanceof Movel m) return r + " | Tipo: Móvel | Material: " + m.getMaterial() + " | Descrição: " + m.getDescricao() + " | Dimensões (L×C×A): " + m.getDimensaoLargura() + "×" + m.getDimensaoComprimento() + "×" + m.getDimensaoAltura() + " cm | Marca: " + m.getMarca() + " | Características: " + m.getCaracteristicas();
        Eletrodomestico e = (Eletrodomestico)p; return r + " | Tipo: Eletrodoméstico | Marca: " + e.getMarca() + " | Características: " + e.getCaracteristicas();
    }

    private static <E extends Enum<E>> E escolher(Scanner s, String titulo, E[] valores) {
        while (true) { System.out.println(titulo + ":"); for (int i=0;i<valores.length;i++) System.out.println((i+1)+" - "+valores[i]); System.out.print("Opção: "); int n=MenuGeral.lerOpcao(s); if(n>0&&n<=valores.length)return valores[n-1]; System.out.println("Opção inválida."); }
    }
    private static <E extends Enum<E>> EnumSet<E> caracteristicas(Scanner s, E[] vals, Class<E> type) {
        System.out.println("Características (números separados por vírgula; 0 para nenhuma):"); for(int i=0;i<vals.length;i++)System.out.println((i+1)+" - "+vals[i]);
        while(true){System.out.print("Opções: ");String in=s.nextLine().trim();EnumSet<E> set=EnumSet.noneOf(type);if(in.equals("0")||in.isEmpty())return set;try{for(String a:in.split("[,;\\s]+")){int n=Integer.parseInt(a);if(n<1||n>vals.length)throw new NumberFormatException();set.add(vals[n-1]);}return set;}catch(NumberFormatException ex){System.out.println("Seleção inválida.");}}
    }
    private static String texto(Scanner s,String p){System.out.print(p);return s.nextLine().trim();}
    private static String textoObrigatorio(Scanner s,String p){String x;do{x=texto(s,p);if(x.isEmpty())System.out.println("Campo obrigatório.");}while(x.isEmpty());return x;}
    private static String opcional(Scanner s,String campo,String atual){System.out.print(campo+" ["+atual+"] (Enter mantém): ");String x=s.nextLine().trim();return x.isEmpty()?atual:x;}
    private static BigDecimal precoNovo(Scanner s){while(true){System.out.print("Preço (ex.: 99.90): ");try{BigDecimal b=new BigDecimal(s.nextLine().trim().replace(',','.'));if(b.signum()>=0)return b;}catch(NumberFormatException ignored){}System.out.println("Preço inválido; informe um valor não negativo.");}}
    private static BigDecimal preco(Scanner s,String campo,BigDecimal atual){while(true){System.out.print(campo+" ["+atual+"] (Enter mantém): ");String x=s.nextLine().trim();if(x.isEmpty())return atual;try{BigDecimal b=new BigDecimal(x.replace(',','.'));if(b.signum()>=0)return b;}catch(NumberFormatException ignored){}System.out.println("Preço inválido.");}}
    private static double dim(Scanner s,String p){while(true){System.out.print(p);try{double d=Double.parseDouble(s.nextLine().trim().replace(',','.'));if(Double.isFinite(d)&&d>0)return d;}catch(NumberFormatException ignored){}System.out.println("Informe número maior que zero.");}}
    private static boolean confirmar(Scanner s,String p){while(true){System.out.print(p);String x=s.nextLine().trim();if(x.equalsIgnoreCase("S"))return true;if(x.equalsIgnoreCase("N"))return false;System.out.println("Responda S ou N.");}}
}
