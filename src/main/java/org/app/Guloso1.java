package org.app;

import org.model.ObjetosArmazenados;
import org.service.GulosoService;

import static org.service.FileReader.getObjetos;
import static org.util.Values.LIMITE_MOCHILA;

/**@@apiNote Considerando um algoritmo guloso no qual se escolha o objeto de maior valor
 * */
public class Guloso1 {

    public static void main(String[] args) {
        GulosoService service = new GulosoService();

        ObjetosArmazenados objetosNaMochila = service.solverGulosoByValor(getObjetos(), LIMITE_MOCHILA);
        System.out.println("\n >>>>>>>>> Considerando um algoritmo guloso no qual se escolha o objeto de MAIOR VALOR, TEMOS:");
        System.out.println("\nOBJETOS NA MOCHILA: ");
        objetosNaMochila.getObjetoList().forEach(objeto -> {
            System.out.println("ITEM INDICE:" + objeto.getRowIndex() + " || PESO: " + objeto.getPeso() + " || VALOR TOTAL: " + objeto.getValorTotal());
        });
        System.out.println("\nPESO MÍNIMO ATINGIDO: " + objetosNaMochila.getPesoAtingido() + " || PARA O VALOR VALOR MAXIMO: " + objetosNaMochila.getValorTotal());
    }

}
