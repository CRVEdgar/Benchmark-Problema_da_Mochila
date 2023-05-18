package org.service;

import org.model.Nodo;
import org.model.Objeto;

import java.util.List;

import static org.util.Values.LIMITE_MOCHILA;

public class BranchBoundService {

    //TODO: analisar e modificar o parametro da mochila
//   public static Double capacidadeMochila = 524.0;

    public Double solverRecursionTree(List<Objeto> objetos) {


        Nodo melhorNodo = new Nodo(-1, 0.0, 0.0, 0.0); //Instancia o primeiro nodo
        Nodo nodoAtual = new Nodo(-1, 0.0, 0.0, calculaValorEstimado(objetos, melhorNodo)); //Instancia o nodo atual com dados do primeiro, iniciando o valor Estimado

        int i=0;
        //PERCORRE TODA LISTA
        while (nodoAtual.getNivel() < objetos.size()) {
//            i++;
//            System.out.println("CICLO: " + (objetos.size()));
            nodoAtual.addNivel();

            Double pesoAtual = nodoAtual.getPesoTotal() + objetos.get(nodoAtual.getNivel()).getPeso(); //ATUAL + PROXIMO DA LISTA
            Double valorAtual = nodoAtual.getValorTotal() + objetos.get(nodoAtual.getNivel()).getValorTotal();
            System.out.println("PESO ATUAL: " + pesoAtual + " | VALOR ATUAL: " + valorAtual);

            if (pesoAtual <= LIMITE_MOCHILA && valorAtual > melhorNodo.getValorTotal()) {
                melhorNodo.setValorTotal(valorAtual);
                melhorNodo.setPesoTotal(pesoAtual);
                melhorNodo.setNivel(nodoAtual.getNivel());
            }

            Double valorEstimado = calculaValorEstimado(objetos, nodoAtual);

            if (valorEstimado > melhorNodo.getValorTotal()) {
                Nodo ramoEsquerdo = new Nodo(nodoAtual.getNivel(), valorAtual, pesoAtual, valorEstimado);
                melhorNodo = branchAndBoundRecursivo(objetos, ramoEsquerdo, melhorNodo);
                System.out.println("MELHOR NODO: " + melhorNodo);

            }

            nodoAtual.decrementPesoTotal( objetos.get(nodoAtual.getNivel()).getPeso() );

            nodoAtual.decrementValorTotal( objetos.get(nodoAtual.getNivel()).getValorTotal() );

            if (nodoAtual.getPesoTotal() <= LIMITE_MOCHILA && nodoAtual.getValorTotal() + valorEstimado > melhorNodo.getValorTotal()) {
                Nodo ramoDireito = new Nodo(nodoAtual.getNivel(), nodoAtual.getValorTotal(), nodoAtual.getPesoTotal(), valorEstimado);
                melhorNodo = branchAndBoundRecursivo(objetos, ramoDireito, melhorNodo);
                System.out.println("MELHOR NODO: " + melhorNodo);
            }

            if (nodoAtual.getPesoTotal() <= LIMITE_MOCHILA && nodoAtual.getValorTotal() + valorEstimado > melhorNodo.getValorTotal()) {
                Nodo ramoSuperior = new Nodo(nodoAtual.getNivel(), nodoAtual.getValorTotal(), nodoAtual.getPesoTotal(), valorEstimado);
                //TODO: INCLUIR NA LISTA DE ITENS NA MOCHILA O OBJETO DESTE NODO
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
        System.out.println("iniciará o nivel: " + nivel);

        while (nivel < objetos.size() && pesoRestante >= objetos.get(nivel).getPeso()) {
            System.out.println(pesoRestante >= objetos.get(nivel).getPeso());
            System.out.println("nivel: " + nivel + " to object: " + objetos.get(nivel).getRowIndex() );
            pesoRestante -= objetos.get(nivel).getPeso(); //limite reduz pois: ADD O PESO DO OBJETO NA MOCHILA
            valorEstimado += objetos.get(nivel).getValorTotal(); //acrescenta o valor total de cada objeto

            System.out.println("Peso Restante: " + pesoRestante  +  " Valor Estimado: " + valorEstimado
                    + "\n Peso do objeto: " + objetos.get(nivel).getPeso() + " | Valor Total: " + objetos.get(nivel).getValorTotal());
            System.out.println("\n");
            nivel++;
        }
        System.out.println("SAIU DO LAÇO");

        if (nivel < objetos.size()) {
            valorEstimado += (pesoRestante / objetos.get(nivel).getPeso()) * objetos.get(nivel).getValorTotal();
        }
        System.out.println("VALOR ESTIMADO APÓS DIVISAO: " + valorEstimado);

        return valorEstimado;
    }

    private Nodo branchAndBoundRecursivo(List<Objeto> objetos, Nodo nodoAtual, Nodo melhorNodo) {
        if (nodoAtual.getPesoTotal() > LIMITE_MOCHILA) {
            System.out.println(" * NODO INCLUIDO: " + melhorNodo.getPesoTotal() + " || " + melhorNodo.getValorTotal());
            //todo: esse retorno deve ser adicionado na lista
            return melhorNodo;
        }

        if (nodoAtual.getNivel() == objetos.size() - 1) {
            if (nodoAtual.getValorTotal() > melhorNodo.getValorTotal()) {
                //todo: esse retorno deve ser adicionado na lista
                System.out.println(" ** NODO INCLUIDO: " + nodoAtual.getPesoTotal() + " || " + nodoAtual.getValorTotal());
                return nodoAtual;
            } else {
                //todo: esse retorno deve ser adicionado na lista
                System.out.println(" *** NODO INCLUIDO: " + melhorNodo.getPesoTotal() + " || " + melhorNodo.getValorTotal());
                return melhorNodo;
            }
        }

        int proximoNivel = nodoAtual.getNivel() + 1;

        Nodo ramoEsquerdo = new Nodo( proximoNivel, nodoAtual.getValorTotal() + objetos.get(proximoNivel).getValorTotal(),
                nodoAtual.getPesoTotal() + objetos.get(proximoNivel).getPeso(), nodoAtual.getValorEstimado() );
        melhorNodo = branchAndBoundRecursivo(objetos, ramoEsquerdo, melhorNodo);

        Nodo ramoDireito = new Nodo(proximoNivel, nodoAtual.getValorTotal(), nodoAtual.getPesoTotal(), nodoAtual.getValorEstimado());
        melhorNodo = branchAndBoundRecursivo(objetos, ramoDireito, melhorNodo);

        //todo: esse retorno deve ser adicionado na lista
        System.out.println(" **** NODO INCLUIDO: " + melhorNodo.getPesoTotal() + " || " + melhorNodo.getValorTotal());
        return melhorNodo;
    }
}
