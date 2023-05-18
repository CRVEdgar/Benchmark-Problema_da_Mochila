package org.app;

import org.service.BranchBoundService;

import static org.service.FileReader.getObjetos;
import static org.util.Values.OBJETOS_ARMAZENADOS;

/** @NOTE-Algoritmo Branch and Bound: implementação utilizando arvore de Busca, cujo resultado se assemelha ao da programação dinamica */
public class BranchAndBound_RecursionTree {


    public static void main(String[] args) {
        BranchBoundService service = new BranchBoundService();

        service.solverRecursionTree(getObjetos());

        System.out.println(" ****** VALORES ÓTIMOS: ******" );
        System.out.printf("PESO MAXIMO ATINGIDO: " + OBJETOS_ARMAZENADOS.getPesoAtingido());
        System.out.printf("\nVALOR MAXIMO ATINGIDO: " + OBJETOS_ARMAZENADOS.getValorTotal());

        System.out.println( "\n");

        System.out.println(" **** HISTÓRICO COM A VERIFICAÇÃO DE CASOS OTIMOS: ****");
        OBJETOS_ARMAZENADOS.getNodos().forEach( nodo -> {
            System.out.println("nodos adicionado: " + nodo.getPesoTotal() + " || " + nodo.getValorTotal());
        });


    }
}
