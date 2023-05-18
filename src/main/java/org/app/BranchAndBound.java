package org.app;

import org.service.BranchBoundService;

import static org.service.FileReader.getObjetos;

public class BranchAndBound {


    public static void main(String[] args) {
        BranchBoundService service = new BranchBoundService();

        Double vlorMaximo = service.solverRecursionTree(getObjetos());

        System.out.println("VALOR TOTAL: " + vlorMaximo);


    }
}
