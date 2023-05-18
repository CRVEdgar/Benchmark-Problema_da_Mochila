package org.service;

import org.model.Objeto;
import org.model.ObjetosArmazenados;

import java.util.List;

import static org.util.Values.LIMITE_MOCHILA;

public class GulosoService {

    public ObjetosArmazenados solverGulosoByPeso(List<Objeto> objetos, Double pesoMaximo) {
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

    public ObjetosArmazenados solverGulosoByValor(List<Objeto> objetos, Double pesoMaximo) {
//        ObjetosArmazenados objetosArmazenados = new ObjetosArmazenados();
//        Double valorTotal = 0.0;
//        Double pesoDisponivel = LIMITE_MOCHILA;
//
//        for(int i=0; i<objetos.size(); i++){
//            if(pesoDisponivel >= LIMITE_MOCHILA){
//                objetosArmazenados.addObjeto(objetos.get(i));
//                objetosArmazenados.addPeso(objetos.get(i).getPeso());
//                objetosArmazenados.addValor(objetos.get(i).getValorTotal());
//
//                pesoDisponivel -= objetos.get(i).getPeso();
//                valorTotal += objetos.get(i).getValorTotal();
//            }
//        }
//        return objetosArmazenados;
//
//    }

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
