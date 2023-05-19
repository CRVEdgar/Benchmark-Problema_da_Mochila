package org.app;

import org.service.DinamicProgrammingService;

import static org.service.FileReader.getObjetos;
import static org.util.Values.LIMITE_MOCHILA;
import static org.util.Values.OBJETOS_ARMAZENADOS;

/** @NOTE: implementação utilizando programação dinamica */
public class DinamicProgrammingSolver {

    public static void main(String[] args) {
        DinamicProgrammingService service = new DinamicProgrammingService();
        long init = System.currentTimeMillis();
        service.solverDinamic(LIMITE_MOCHILA ,getObjetos());
        long finish = System.currentTimeMillis();

        System.out.println("VALOR TOTAL: " + OBJETOS_ARMAZENADOS.getValorTotal() + " || PESO ATINGIDO: " + OBJETOS_ARMAZENADOS.getPesoAtingido());
        System.out.println("TEMPO TOTAL DA EXECUÇÃO: " + (finish-init) + " (milisegundos)");

    }
}
