package org.app;


import org.service.BranchBound_TreeAlternativeService;

import static org.service.FileReader.getObjetos;
import static org.util.Values.OBJETOS_ARMAZENADOS;

/** @NOTE-Algoritmo: Variação do Branch and Bound: implementação utilizando arvore de Busca*/

public class BranchAndBound_RecursionTreeAlternative {

    public static void main(String[] args) {
        BranchBound_TreeAlternativeService service = new BranchBound_TreeAlternativeService();

        service.solverRecursionTree_Alternative(getObjetos());

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
