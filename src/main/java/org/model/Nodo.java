package org.model;

public class Nodo {
    int nivel;
    Double valorTotal;
    Double pesoTotal;
    Double valorEstimado;

    public Nodo(int nivel, Double valorTotal, Double pesoTotal, Double valorEstimado) {
        this.nivel = nivel;
        this.valorTotal = valorTotal;
        this.pesoTotal = pesoTotal;
        this.valorEstimado = valorEstimado;
    }

    public int getNivel() {
        return nivel;
    }

    public void addNivel() {
        this.nivel++;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void decrementValorTotal(Double valorDecrementado) {
        this.valorTotal -= valorDecrementado;
    }

    public Double getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(Double pesoTotal) {
        this.pesoTotal = pesoTotal;
    }

    public void decrementPesoTotal(Double pesoDecrementado) {
        this.pesoTotal -= pesoDecrementado;
    }

    public Double getValorEstimado() {
        return valorEstimado;
    }

    public void setValorEstimado(Double valorEstimado) {
        this.valorEstimado = valorEstimado;
    }

    @Override
    public String toString() {
        return "Nodo{" +
                "nivel=" + nivel +
                ", valorTotal=" + valorTotal +
                ", pesoTotal=" + pesoTotal +
                ", valorEstimado=" + valorEstimado +
                '}';
    }
}
