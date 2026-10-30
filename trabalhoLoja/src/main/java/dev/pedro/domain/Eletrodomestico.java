package dev.pedro.domain;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class Eletrodomestico extends Produto {

    private MarcaEletrodomestico marca;
    private EnumSet<CaracteristicaEletrodomestico> caracteristicas =
            EnumSet.noneOf(CaracteristicaEletrodomestico.class);

    public MarcaEletrodomestico getMarca() {
        return marca;
    }

    public void setMarca(MarcaEletrodomestico marca) {
        this.marca = Objects.requireNonNull(marca, "A marca é obrigatória.");
    }

    public Set<CaracteristicaEletrodomestico> getCaracteristicas() {
        return Collections.unmodifiableSet(EnumSet.copyOf(caracteristicas));
    }

    public void setCaracteristicas(Set<CaracteristicaEletrodomestico> caracteristicas) {
        Objects.requireNonNull(caracteristicas, "As características não podem ser nulas.");
        this.caracteristicas = caracteristicas.isEmpty()
                ? EnumSet.noneOf(CaracteristicaEletrodomestico.class)
                : EnumSet.copyOf(caracteristicas);
    }
}