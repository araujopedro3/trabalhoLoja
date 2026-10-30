package dev.pedro.domain;

public enum CaracteristicaMovel {
    ESTOFADO("Estofado"),
    DOBRAVEL("Dobrável"),
    COM_GAVETAS("Com gavetas"),
    COM_PRATELEIRAS("Com prateleiras"),
    RESISTENTE_A_UMIDADE("Resistente à umidade"),
    MONTAGEM_FACIL("Montagem fácil"),
    ERGONOMICO("Ergonômico");

    private final String nomeExibicao;

    CaracteristicaMovel(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    public String getNomeExibicao() {
        return nomeExibicao;
    }

    @Override
    public String toString() {
        return nomeExibicao;
    }
}