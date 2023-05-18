package org.service;

import org.model.Objeto;
import org.model.ObjetosArmazenados;

import java.util.List;

public class BranchBoundDinamicService {

    public ObjetosArmazenados solverDinamic(Double limiteMochila, List<Objeto> objetos) {
        int n = objetos.size();
        int[][] matriz = new int[n + 1][limiteMochila.intValue() + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= limiteMochila.intValue(); j++) {
                if (objetos.get(i - 1).getPeso() <= j) {
                    matriz[i][j] = Math.max(objetos.get(i - 1).getValorTotal().intValue() + matriz[i - 1][ j - objetos.get(i - 1).getPeso().intValue() ], matriz[i - 1][j]);
                } else {
                    matriz[i][j] = matriz[i - 1][j];
                }
            }
        }

        ObjetosArmazenados objetosArmazenados = new ObjetosArmazenados();
        objetosArmazenados.addValor(Double.valueOf(matriz[n][limiteMochila.intValue()]));

        return objetosArmazenados;
    }
}
