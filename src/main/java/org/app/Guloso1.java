package org.app;

import org.model.ObjetosArmazenados;
import org.service.GulosoService;

import static org.service.FileReader.getObjetos;
import static org.util.Values.LIMITE_MOCHILA;

public class Guloso1 {

    public static void main(String[] args) {
        GulosoService service = new GulosoService();

        ObjetosArmazenados objetosNaMochila = service.solverGulosoByPeso(getObjetos(), LIMITE_MOCHILA);

        System.out.println("OBJETOS NA MOCHILA: ");
        objetosNaMochila.getObjetoList().forEach(objeto -> {
            System.out.println("ITEM INDICE:" + objeto.getRowIndex() + " || PESO: " + objeto.getPeso() + " || VALOR TOTAL: " + objeto.getValorTotal());
        });
        System.out.println("\nVALOR MAXIMO ATINGIDO: " + objetosNaMochila.getPesoAtingido() + " || " + objetosNaMochila.getValorTotal());
    }

}
