package org.app;
import java.util.Arrays;
public class Guloso_PorPeso {

    public static void main(String[] args) {
        int volumeMochila = 524;

        //TODO UTLIZAR OS VALORES DA PLANILHA
        int[] volume = {2, 5, 4, 3};
        int[] peso = {1, 6, 4, 3};

        double[] indice = new double[volume.length];
        for (int i = 0; i < volume.length; i++) {
            indice[i] = (double) peso[i] / volume[i];
        }

        int[] pos = new int[indice.length];
        for (int i = 0; i < indice.length; i++) {
            pos[i] = i;
        }

        // Ordenar pos de acordo com os valores em indice em ordem decrescente
        for (int i = 0; i < pos.length - 1; i++) {
            for (int j = 0; j < pos.length - i - 1; j++) {
                if (indice[j] < indice[j + 1]) {
                    double tempIndice = indice[j];
                    indice[j] = indice[j + 1];
                    indice[j + 1] = tempIndice;

                    int tempPos = pos[j];
                    pos[j] = pos[j + 1];
                    pos[j + 1] = tempPos;
                }
            }
        }

        int[] x = new int[volume.length];
        int pesoTotal = 0;

        for (int i = 0; i < pos.length; i++) {
            if (volume[pos[i]] <= volumeMochila) {
                x[pos[i]] = 1;
                volumeMochila -= volume[pos[i]];
                pesoTotal += peso[pos[i]];
            }
        }

        System.out.println("Itens selecionados:");
        for (int i = 0; i < x.length; i++) {
            if (x[i] == 1) {
                System.out.println("Item " + (i + 1));
            }
        }

        System.out.println("Peso total: " + pesoTotal);
    }

}
