package org.service;

import org.model.Nodo;
import org.model.Objeto;

import java.util.List;

import static org.util.Values.LIMITE_MOCHILA;
import static org.util.Values.OBJETOS_ARMAZENADOS;

public class BranchBound_TreeAlternativeService {

    public Double solverRecursionTree_Alternative(List<Objeto> objetos) {


        Nodo melhorNodo = new Nodo(-1, 0.0, 0.0, 0.0); //Instancia o primeiro nodo
        Nodo nodoAtual = new Nodo(-1, 0.0, 0.0, calculaValorEstimado(objetos, melhorNodo)); //Instancia o nodo atual com dados do primeiro, iniciando o valor Estimado

        //PERCORRE TODA LISTA
        while (nodoAtual.getNivel() < objetos.size()) {
            nodoAtual.addNivel();

            Double pesoAtual = nodoAtual.getPesoTotal() + objetos.get(nodoAtual.getNivel()).getPeso(); //ATUAL + PROXIMO DA LISTA
            Double valorAtual = nodoAtual.getValorTotal() + objetos.get(nodoAtual.getNivel()).getValorTotal();

            if (pesoAtual <= LIMITE_MOCHILA && valorAtual > melhorNodo.getValorTotal()) {
                melhorNodo.setValorTotal(valorAtual);
                melhorNodo.setPesoTotal(pesoAtual);
                melhorNodo.setNivel(nodoAtual.getNivel());
            }

            Double valorEstimado = calculaValorEstimado(objetos, nodoAtual);

            if (valorEstimado > melhorNodo.getValorTotal()) {
                Nodo ramoEsquerdo = new Nodo(nodoAtual.getNivel(), valorAtual, pesoAtual, valorEstimado);
                melhorNodo = branchAndBoundRecursivo(objetos, ramoEsquerdo, melhorNodo);


                nodoAtual.decrementPesoTotal( objetos.get(nodoAtual.getNivel()).getPeso() );
                nodoAtual.decrementValorTotal( objetos.get(nodoAtual.getNivel()).getValorTotal() );

                Nodo ramoDireito = new Nodo(nodoAtual.getNivel(), nodoAtual.getValorTotal(), nodoAtual.getPesoTotal(), valorEstimado);
                melhorNodo = branchAndBoundRecursivo(objetos, ramoDireito, melhorNodo);
            }


            if (nodoAtual.getPesoTotal() <= LIMITE_MOCHILA && nodoAtual.getValorTotal() + valorEstimado > melhorNodo.getValorTotal()) {
                Nodo ramoSuperior = new Nodo(nodoAtual.getNivel(), nodoAtual.getValorTotal(), nodoAtual.getPesoTotal(), valorEstimado);
                nodoAtual = ramoSuperior;
            } else {
                break;
            }
        }

        OBJETOS_ARMAZENADOS.addPeso(melhorNodo.getPesoTotal());
        OBJETOS_ARMAZENADOS.addValor(melhorNodo.getValorTotal());

//        System.out.println(" --- MELHOR CASO: Peso Total: " + melhorNodo.getPesoTotal() + " || Valor Total: " + melhorNodo.getValorTotal());
        return melhorNodo.getValorTotal();
    }

    private Double calculaValorEstimado(List<Objeto> objetos, Nodo nodo) {
        Double pesoRestante = LIMITE_MOCHILA - nodo.getPesoTotal();
        Double valorEstimado = nodo.getValorTotal();

        int nivel = nodo.getNivel() + 1;

        while (nivel < objetos.size() && pesoRestante >= objetos.get(nivel).getPeso()) {
            pesoRestante -= objetos.get(nivel).getPeso(); //limite deve ser reduzido pois: ADICIONA O PESO DO OBJETO NA MOCHILA
            valorEstimado += objetos.get(nivel).getValorTotal(); //acrescenta o valor total de cada objeto

            nivel++;
        }

        if (nivel < objetos.size()) {
            valorEstimado += (pesoRestante / objetos.get(nivel).getPeso()) * objetos.get(nivel).getValorTotal();
        }
//        System.out.println("VALOR ESTIMADO APÓS DIVISAO: " + valorEstimado);

        return valorEstimado;
    }

    private Nodo branchAndBoundRecursivo(List<Objeto> objetos, Nodo nodoAtual, Nodo melhorNodo) {
        if (nodoAtual.getPesoTotal() > LIMITE_MOCHILA) {
            OBJETOS_ARMAZENADOS.addNodo(melhorNodo);

            return melhorNodo;
        }

        if (nodoAtual.getNivel() == objetos.size() - 1) {
            if (nodoAtual.getValorTotal() > melhorNodo.getValorTotal()) {
                OBJETOS_ARMAZENADOS.addNodo(nodoAtual);

                return nodoAtual;
            } else {
                OBJETOS_ARMAZENADOS.addNodo(melhorNodo);

                return melhorNodo;
            }
        }

        int proximoNivel = nodoAtual.getNivel() + 1;

        Nodo ramoEsquerdo = new Nodo( proximoNivel, nodoAtual.getValorTotal() + objetos.get(proximoNivel).getValorTotal(),
                nodoAtual.getPesoTotal() + objetos.get(proximoNivel).getPeso(), nodoAtual.getValorEstimado() );
        melhorNodo = branchAndBoundRecursivo(objetos, ramoEsquerdo, melhorNodo);

        Nodo ramoDireito = new Nodo(proximoNivel, nodoAtual.getValorTotal(), nodoAtual.getPesoTotal(), nodoAtual.getValorEstimado());
        melhorNodo = branchAndBoundRecursivo(objetos, ramoDireito, melhorNodo);

        OBJETOS_ARMAZENADOS.addNodo(melhorNodo);

        return melhorNodo;
    }
}
