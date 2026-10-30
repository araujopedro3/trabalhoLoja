package dev.pedro.domain;

public enum CaracteristicaMovel {
    ESTOFADO("Estofado"),
    DOBRAVEL("Dobrável"),
    RECLINAVEL("Reclinável"),
    EXTENSIVEL("Extensível"),
    COM_GAVETAS("Com gavetas"),
    COM_PRATELEIRAS("Com prateleiras"),
    COM_PORTAS("Com portas");

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
