package dev.pedro.domain;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class Movel extends Produto {

    private String material;
    private String descricao;
    private MarcaMovel marca;
    private EnumSet<CaracteristicaMovel> caracteristicas =
            EnumSet.noneOf(CaracteristicaMovel.class);
    private double dimensaoLargura;
    private double dimensaoComprimento;
    private double dimensaoAltura;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public MarcaMovel getMarca() {
        return marca;
    }

    public void setMarca(MarcaMovel marca) {
        this.marca = Objects.requireNonNull(marca, "A marca é obrigatória.");
    }

    public Set<CaracteristicaMovel> getCaracteristicas() {
        return Collections.unmodifiableSet(EnumSet.copyOf(caracteristicas));
    }

    public void setCaracteristicas(Set<CaracteristicaMovel> caracteristicas) {
        Objects.requireNonNull(caracteristicas, "As características não podem ser nulas.");
        this.caracteristicas = caracteristicas.isEmpty()
                ? EnumSet.noneOf(CaracteristicaMovel.class)
                : EnumSet.copyOf(caracteristicas);
    }

    public double getDimensaoLargura() {
        return dimensaoLargura;
    }

    public void setDimensaoLargura(double dimensaoLargura) {
        this.dimensaoLargura = validarDimensao(dimensaoLargura, "largura");
    }

    public double getDimensaoComprimento() {
        return dimensaoComprimento;
    }

    public void setDimensaoComprimento(double dimensaoComprimento) {
        this.dimensaoComprimento = validarDimensao(dimensaoComprimento, "comprimento");
    }

    public double getDimensaoAltura() {
        return dimensaoAltura;
    }

    public void setDimensaoAltura(double dimensaoAltura) {
        this.dimensaoAltura = validarDimensao(dimensaoAltura, "altura");
    }

    private double validarDimensao(double dimensao, String nome) {
        if (!Double.isFinite(dimensao) || dimensao <= 0) {
            throw new IllegalArgumentException("A dimensão " + nome + " deve ser maior que zero.");
        }
        return dimensao;
    }
}