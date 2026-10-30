package dev.pedro.domain;

public enum CaracteristicaEletrodomestico {
    BAIXO_CONSUMO_ENERGIA("Baixo consumo de energia"),
    TECNOLOGIA_INVERTER("Tecnologia inverter"),
    PAINEL_DIGITAL("Painel digital"),
    CONTROLE_TEMPERATURA("Controle de temperatura"),
    TIMER_PROGRAMAVEL("Timer programável"),
    DESCONGELAMENTO_AUTOMATICO("Descongelamento automático"),
    CONECTIVIDADE_WIFI("Conectividade Wi-Fi");

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
