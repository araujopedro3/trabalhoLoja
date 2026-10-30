package dev.pedro.domain;

public enum CaracteristicaEletrodomestico {
    VOLTAGEM("Voltagem"),
    EFICIENCIA_ENERGETICA("Eficiência energética"),
    CAPACIDADE("Capacidade"),
    TIPO_PAINEL("Tipo de painel"),
    CONECTIVIDADE("Conectividade"),
    PORTATIL("Portátil"),
    ERGONOMICO("Ergonômico");

    private final String nomeExibicao;

    CaracteristicaEletrodomestico(String nomeExibicao) {
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