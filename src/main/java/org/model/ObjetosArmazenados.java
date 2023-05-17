package org.model;

import java.util.ArrayList;
import java.util.List;

public class ObjetosArmazenados {

    List<Nodo> nodos = new ArrayList<>();

    List<Objeto> objetoList = new ArrayList<>();

    private Double valorTotal = 0.0;
    private Double pesoAtingido = 0.0;

    public void addNodo(Nodo nodo){
        nodos.add(nodo);
    }

    public void addObjeto(Objeto obj){
        objetoList.add(obj);
    }

    public void addPeso(Double peso){
        this.pesoAtingido += peso;
    }

    public void addValor(Double valor){
        this.valorTotal += valor;
    }

    public List<Nodo> getNodos() {
        return nodos;
    }


    public Double getPesoAtingido() {
        return pesoAtingido;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public List<Objeto> getObjetoList() {
        return objetoList;
    }
}
