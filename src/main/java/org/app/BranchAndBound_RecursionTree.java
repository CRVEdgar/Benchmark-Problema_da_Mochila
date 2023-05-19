package org.app;

import org.service.BranchBoundService;

import static org.service.FileReader.getObjetos;
import static org.util.Values.OBJETOS_ARMAZENADOS;

/** @NOTE-Algoritmo Branch and Bound: implementação utilizando arvore de Busca, cujo resultado se assemelha ao da programação dinamica */
public class BranchAndBound_RecursionTree {


    public static void main(String[] args) {
        BranchBoundService service = new BranchBoundService();
        long init = System.currentTimeMillis();
        service.solverRecursionTree(getObjetos());
        long finish = System.currentTimeMillis();
        var ref = new Object() {
            int cont = 1;
        };

        System.out.println( "\n");

        System.out.println(" **** HISTÓRICO COM A VERIFICAÇÃO DE CASOS OTIMOS: ****");
        OBJETOS_ARMAZENADOS.getNodos().forEach( nodo -> {
            System.out.println("ITERAÇÃO: " + ref.cont + " ==> PESO: " + nodo.getPesoTotal() + " || VALOR: " + nodo.getValorTotal());
            ref.cont++;
        });

        System.out.println("\n\n");
        System.out.println(" ****** [Branch and Bound 1] VALORES ÓTIMOS: ******" );
        System.out.printf("PESO MAXIMO ATINGIDO: " + OBJETOS_ARMAZENADOS.getPesoAtingido());
        System.out.printf("\nVALOR MAXIMO ATINGIDO: " + OBJETOS_ARMAZENADOS.getValorTotal());
        System.out.println("\nTEMPO TOTAL DA EXECUÇÃO: " + (finish-init) + " (milisegundos)");
        System.out.println("NUMERO DE ITERAÇÕES: " + OBJETOS_ARMAZENADOS.getNodos().size());
    }
}
