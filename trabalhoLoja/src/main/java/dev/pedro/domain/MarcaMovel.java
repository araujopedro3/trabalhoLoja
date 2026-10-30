package dev.pedro.domain;

public enum MarcaMovel {
    TODESCHINI("Todeschini"),
    FLORENSE("Florense"),
    MADEIRA_MADEIRA("MadeiraMadeira"),
    IKEA("IKEA"),
    OUTRA("Outra");

    private final String nomeExibicao;

    MarcaMovel(String nomeExibicao) {
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