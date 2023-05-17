package org.service;

import org.model.Nodo;
import org.model.Objeto;

import java.util.List;

import static org.util.DirFile.LIMITE_MOCHILA;

public class BranchBoundService {

    //TODO: analisar e modificar o parametro da mochila
//   public static Double capacidadeMochila = 524.0;

    public Double branchAndBound(List<Objeto> objetos) {
//        Arrays.sort(objetos, (i1, i2) -> {
//            double ratio1 = i1.valor / (double) i1.peso;
//            double ratio2 = i2.valor / (double) i2.peso;
//            if (ratio1 > ratio2) {
//                return -1;
//            } else if (ratio1 < ratio2) {
//                return 1;
//            }
//            return 0;
//        });

        Nodo melhorNodo = new Nodo(-1, 0.0, 0.0, 0.0); //Instancia o primeiro nodo
        Nodo nodoAtual = new Nodo(-1, 0.0, 0.0, calculaValorEstimado(objetos, melhorNodo)); //Instancia o nodo atual com

        int i=0;
        while (nodoAtual.getNivel() < objetos.size() /*- 1*/) {
//            i++;
//            System.out.println("CICLO: " + (objetos.size()));
            nodoAtual.addNivel();

            Double pesoAtual = nodoAtual.getPesoTotal() + objetos.get(nodoAtual.getNivel()).getPeso();
            Double valorAtual = nodoAtual.getValorTotal() + objetos.get(nodoAtual.getNivel()).getValorTotal();

            if (pesoAtual <= LIMITE_MOCHILA && valorAtual > melhorNodo.getValorTotal()) {
                melhorNodo.setValorTotal(valorAtual);
                melhorNodo.setPesoTotal(pesoAtual);
                melhorNodo.setNivel(nodoAtual.getNivel());
            }

            Double valorEstimado = calculaValorEstimado(objetos, nodoAtual);

            if (valorEstimado > melhorNodo.getValorTotal() && pesoAtual < 524) {
                Nodo ramoEsquerdo = new Nodo(nodoAtual.getNivel(), valorAtual, pesoAtual, valorEstimado);
                melhorNodo = branchAndBoundRecursivo(objetos, ramoEsquerdo, melhorNodo);
                System.out.println("MELHOR NODO: " + melhorNodo);

            }

            nodoAtual.decrementPesoTotal( objetos.get(nodoAtual.getNivel()).getPeso() );

            nodoAtual.decrementValorTotal( objetos.get(nodoAtual.getNivel()).getValorTotal() );

            if (nodoAtual.getPesoTotal() <= LIMITE_MOCHILA && nodoAtual.getValorTotal() + valorEstimado > melhorNodo.getValorTotal() && nodoAtual.getPesoTotal() < 524) {
                Nodo ramoDireito = new Nodo(nodoAtual.getNivel(), nodoAtual.getValorTotal(), nodoAtual.getPesoTotal(), valorEstimado);
                melhorNodo = branchAndBoundRecursivo(objetos, ramoDireito, melhorNodo);
                System.out.println("MELHOR NODO: " + melhorNodo);
            }

            if (nodoAtual.getPesoTotal() <= LIMITE_MOCHILA && nodoAtual.getValorTotal() + valorEstimado > melhorNodo.getValorTotal() && nodoAtual.getPesoTotal() < 524) {
                Nodo ramoSuperior = new Nodo(nodoAtual.getNivel(), nodoAtual.getValorTotal(), nodoAtual.getPesoTotal(), valorEstimado);
                nodoAtual = ramoSuperior;
            } else {
                System.out.println("LIMITE ATINGIDO NO NIVEL " + nodoAtual.getNivel());
                break;
            }
        }

        System.out.println(" --- MELHOR NODO: Peso: " + melhorNodo.getPesoTotal() + " || Valor Total: " + melhorNodo.getValorTotal());
        return melhorNodo.getValorTotal();
    }

    private Double calculaValorEstimado(List<Objeto> objetos, Nodo nodo) {
        Double pesoRestante = LIMITE_MOCHILA - nodo.getPesoTotal();
        Double valorEstimado = nodo.getValorTotal();

        int nivel = nodo.getNivel() + 1;

        while (nivel < objetos.size() && pesoRestante >= objetos.get(nivel).getPeso()) {
            pesoRestante -= objetos.get(nivel).getPeso();
            valorEstimado += objetos.get(nivel).getValorTotal();
            nivel++;
        }

        if (nivel < objetos.size()) {
            valorEstimado += (pesoRestante / objetos.get(nivel).getPeso()) * objetos.get(nivel).getValorTotal();
        }

        return valorEstimado;
    }

    private Nodo branchAndBoundRecursivo(List<Objeto> objetos, Nodo nodoAtual, Nodo melhorNodo) {
        if (nodoAtual.getPesoTotal() > LIMITE_MOCHILA || nodoAtual.getPesoTotal() >= 524) {
            //todo: esse retorno deve ser adicionado na lista
            return melhorNodo;
        }

        if (nodoAtual.getNivel() == objetos.size() - 1) { //TODO: size only / no -1
            if (nodoAtual.getValorTotal() > melhorNodo.getValorTotal()) {
                return nodoAtual;
            } else {
                return melhorNodo;
            }
        }

        int proximoNivel = nodoAtual.getNivel() + 1;

        Nodo ramoEsquerdo = new Nodo( proximoNivel, nodoAtual.getValorTotal() + objetos.get(proximoNivel).getValorTotal(),
                nodoAtual.getPesoTotal() + objetos.get(proximoNivel).getPeso(), nodoAtual.getValorEstimado() );
        melhorNodo = branchAndBoundRecursivo(objetos, ramoEsquerdo, melhorNodo);

        Nodo ramoDireito = new Nodo(proximoNivel, nodoAtual.getValorTotal(), nodoAtual.getPesoTotal(), nodoAtual.getValorEstimado());
        melhorNodo = branchAndBoundRecursivo(objetos, ramoDireito, melhorNodo);

        return melhorNodo;
    }
}
