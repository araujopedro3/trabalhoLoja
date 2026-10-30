package dev.pedro.domain;

public enum MarcaEletrodomestico {
    ELECTROLUX("Electrolux"),
    CONSUL("Consul"),
    DAKO("Dako"),
    BRASTEMP("Brastemp"),
    OUTRA("Outra");

    private final String nomeExibicao;

    MarcaEletrodomestico(String nomeExibicao) {
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