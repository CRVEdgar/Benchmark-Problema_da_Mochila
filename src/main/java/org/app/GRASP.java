package org.app;

import org.model.Objeto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.service.FileReader.getObjetos;
import static org.util.Values.LIMITE_MOCHILA;
import static org.util.Values.QUANTIDADE_ITENS;

public class GRASP {
    private int[] weights;
    private int[] values;
    private int capacity;
    private int maxIterations;
    private double alpha;

    public GRASP(int[] weights, int[] values, int capacity, int maxIterations, double alpha) {
        this.weights = weights;
        this.values = values;
        this.capacity = capacity;
        this.maxIterations = maxIterations;
        this.alpha = alpha;
    }

    public int[] generateConstructiveSolution() {
        int[] solution = new int[weights.length];
        int remainingCapacity = capacity;

        while (remainingCapacity > 0) {
            List<Integer> candidateItems = new ArrayList<>();

            for (int i = 0; i < weights.length; i++) {
                if (weights[i] <= remainingCapacity && solution[i] == 0) {
                    candidateItems.add(i);
                }
            }

            if (candidateItems.isEmpty()) {
                break;
            }

            int[] candidateValues = new int[candidateItems.size()];
            int max = Integer.MIN_VALUE;

            for (int i = 0; i < candidateItems.size(); i++) {
                int item = candidateItems.get(i);
                candidateValues[i] = values[item];
                max = Math.max(max, candidateValues[i]);
            }

            List<Integer> bestCandidates = new ArrayList<>();

            for (int i = 0; i < candidateItems.size(); i++) {
                int item = candidateItems.get(i);
                if (candidateValues[i] == max) {
                    bestCandidates.add(item);
                }
            }

            Random random = new Random();
            int selectedItem = bestCandidates.get(random.nextInt(bestCandidates.size()));

            solution[selectedItem] = 1;
            remainingCapacity -= weights[selectedItem];
        }

        return solution;
    }

    public int evaluateSolution(int[] solution) {
        int totalValue = 0;
        int totalWeight = 0;

        for (int i = 0; i < weights.length; i++) {
            if (solution[i] == 1) {
                totalValue += values[i];
                totalWeight += weights[i];
            }
        }

        if (totalWeight > capacity) {
            totalValue = 0;
        }

        return totalValue;
    }

    public int[] localSearch(int[] solution) {
        int[] currentSolution = solution;
        int currentFitness = evaluateSolution(currentSolution);

        boolean improving = true;

        while (improving) {
            List<int[]> neighborhood = new ArrayList<>();

            for (int i = 0; i < currentSolution.length; i++) {
                int[] move = currentSolution.clone();
                move[i] = 1 - move[i];
                neighborhood.add(move);
            }

            int[] bestMove = null;
            int bestMoveFitness = 0;

            for (int[] move : neighborhood) {
                int moveFitness = evaluateSolution(move);

                if (moveFitness > bestMoveFitness) {
                    bestMove = move;
                    bestMoveFitness = moveFitness;
                }
            }

            if (bestMove != null && bestMoveFitness > currentFitness) {
                currentSolution = bestMove;
                currentFitness = bestMoveFitness;
            } else {
                improving = false;
            }
        }

        return currentSolution;
    }

    public int[] runGRASP() {
        int[] bestSolution = null;
        int bestFitness = 0;

        for (int iteration = 1; iteration <= maxIterations; iteration++) {
            int[] constructiveSolution = generateConstructiveSolution();
            int[] localSearchSolution = localSearch(constructiveSolution);
            int localSearchFitness = evaluateSolution(localSearchSolution);

            if (localSearchFitness > bestFitness) {
                bestSolution = localSearchSolution;
                bestFitness = localSearchFitness;
            }
        }

        return bestSolution;
    }

    public static void main(String[] args) {
        double alpha = 0.5;
        int capacidade = LIMITE_MOCHILA.intValue();
        int qtdIteracoes = 100;
        int[] pesos = new int[QUANTIDADE_ITENS];
        int[] valores = new int[QUANTIDADE_ITENS];



        GRASP GRASP = new GRASP(pesos, valores, capacidade, qtdIteracoes, alpha);

        long init = System.currentTimeMillis();

        List<Objeto> objetos = getObjetos();
        for (int i = 0; i < objetos.size(); i++) {
            pesos[i] = objetos.get(i).getPeso().intValue();
            valores[i] = objetos.get(i).getValorTotal().intValue();
        }
        int[] bestSolution = GRASP.runGRASP();

        long finish = System.currentTimeMillis();


        int pesoArmazenado = 0;
        for(int i=0; i<objetos.size(); i++){
            if(bestSolution[i]==1){
                pesoArmazenado += objetos.get(i).getPeso().intValue();
            }
        }


        System.out.println("Itens Selecionados: " + java.util.Arrays.toString(bestSolution));
        System.out.println("\n");
        System.out.println("Maior Valor Atingido: " + GRASP.evaluateSolution(bestSolution));
        System.out.println("Peso Minimo Atingido: " + pesoArmazenado);
        System.out.println("TEMPO TOTAL DA EXECUÇÃO: " + (finish-init) + " (milisegundos)");


    }
}

