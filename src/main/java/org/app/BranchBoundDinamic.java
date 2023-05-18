package org.app;

import org.model.ObjetosArmazenados;
import org.service.BranchBoundDinamicService;

import static org.service.FileReader.getObjetos;
import static org.util.DirFile.LIMITE_MOCHILA;

public class BranchBoundDinamic {

    public static void main(String[] args) {
        BranchBoundDinamicService service = new BranchBoundDinamicService();

        ObjetosArmazenados valorFinal = service.solverDinamic(LIMITE_MOCHILA ,getObjetos());

        System.out.println("VALOR TOTAL: " + valorFinal.getValorTotal());


    }
}
