package org.service;

import org.model.Objeto;
import org.model.ObjetosArmazenados;

import java.util.List;

public class GulosoByPeso {

    public static ObjetosArmazenados knapsack(List<Objeto> objetos, Double pesoMaximo) {
        ObjetosArmazenados objetosArmazenados = new ObjetosArmazenados();
        Double pesoAtual = 0.0;
        Double valorTotal = 0.0;

        for (Objeto item : objetos) {
            if (pesoAtual + item.getPeso() <= pesoMaximo) {
                objetosArmazenados.addObjeto(item);
                objetosArmazenados.addPeso(item.getPeso());
                objetosArmazenados.addValor(item.getValorTotal());

                pesoAtual += item.getPeso();
                valorTotal += item.getValorTotal();
            }
            /*else {
                break;
            }*/
        }

        return objetosArmazenados;
    }
}
