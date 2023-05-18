package org.service;

import org.model.Objeto;

import java.util.List;

import static org.util.Values.OBJETOS_ARMAZENADOS;

public class DinamicProgrammingService {

    public void solverDinamic(Double limiteMochila, List<Objeto> objetos) {
        int qtdObjetos = objetos.size();
        int[][] matriz = new int[qtdObjetos + 1][limiteMochila.intValue() + 1];

        for (int i = 1; i <= qtdObjetos; i++) {
            for (int j = 1; j <= limiteMochila.intValue(); j++) {
                if (objetos.get(i - 1).getPeso() <= j) {
                    matriz[i][j] = Math.max(
                            objetos.get(i - 1).getValorTotal().intValue()
                            + matriz[i - 1][ j - objetos.get(i - 1).getPeso().intValue() ], matriz[i - 1][j]
                    );
                } else {
                    matriz[i][j] = matriz[i - 1][j];
                }
            }
        }

        int valorMaximo = matriz[qtdObjetos][limiteMochila.intValue()];
        int pesoMaximo = 0;
        int capacidadeAtual = limiteMochila.intValue();

        // Verifica quais itens foram escolhidos
        for (int i = qtdObjetos; i > 0 && valorMaximo > 0; i--) {
            if (valorMaximo != matriz[i - 1][capacidadeAtual]) {
                pesoMaximo += objetos.get(i - 1).getPeso();
                valorMaximo -= objetos.get(i - 1).getValorTotal().intValue();
                capacidadeAtual -= objetos.get(i - 1).getPeso();
            }
        }

        OBJETOS_ARMAZENADOS.addValor(Double.valueOf(matriz[qtdObjetos][limiteMochila.intValue()]));


        OBJETOS_ARMAZENADOS.addPeso(Double.valueOf(pesoMaximo));

//        return OBJETOS_ARMAZENADOS;
    }
}
