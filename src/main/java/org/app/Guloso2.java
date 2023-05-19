package org.app;

import org.model.ObjetosArmazenados;
import org.service.GulosoService;

import static org.service.FileReader.getObjetos;
import static org.util.Values.LIMITE_MOCHILA;

/**@@apiNote Considerando um algoritmo guloso no qual se escolha o objeto mais leve
 * */
public class Guloso2 {

    public static void main(String[] args) {
        GulosoService service = new GulosoService();
        long init = System.currentTimeMillis();
        ObjetosArmazenados objetosNaMochila = service.solverGulosoByPeso(getObjetos(), LIMITE_MOCHILA);
        long finish = System.currentTimeMillis();

        System.out.println("\n >>>>>>>>> Considerando um algoritmo guloso no qual se escolha o objeto MAIS LEVE, TEMOS:");
        System.out.println("\nOBJETOS NA MOCHILA: ");
        objetosNaMochila.getObjetoList().forEach(objeto -> {
            System.out.println("ITEM INDICE:" + objeto.getRowIndex() + " || PESO: " + objeto.getPeso() + " || VALOR TOTAL: " + objeto.getValorTotal());
        });
        System.out.println("\nPESO MÍNIMO ATINGIDO: " + objetosNaMochila.getPesoAtingido() + " || PARA O VALOR VALOR MAXIMO: " + objetosNaMochila.getValorTotal());
        System.out.println("TEMPO TOTAL DA EXECUÇÃO: " + (finish-init) + " (milisegundos)");
        System.out.println("NUMERO DE ITERAÇÕES: " + objetosNaMochila.getObjetoList().size());

    }
}
