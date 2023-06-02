package org.app;

import org.model.Objeto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.service.FileReader.getObjetos;


public class TabuSearch {

    private int tabuSize;
    private List<int[]> tabuList;
    private int capacity;

    public TabuSearch(int tabuSize, int capacity) {
        this.tabuSize = tabuSize;
        this.capacity = capacity;
        tabuList = new ArrayList<>();
    }

    public Knapsack search(Knapsack initialSolution, int iterations) {
        Knapsack bestSolution = initialSolution;
        Knapsack nextSolution = initialSolution;

        for (int i = 0; i < iterations; i++) {
            List<Knapsack> neighbors = generateNeighbors(nextSolution);
            nextSolution = null;

            for (Knapsack neighbor : neighbors) {
                if (!isTabu(neighbor) && neighbor.getWeight() <= capacity &&
                        (nextSolution == null || neighbor.getValue() > nextSolution.getValue())) {
                    nextSolution = neighbor;
                }
            }

            if (nextSolution == null) {
                break; // No feasible neighbors found
            }

            tabuList.add(nextSolution.getSelectedItems());

            if (tabuList.size() > tabuSize) {
                tabuList.remove(0);
            }

            if (nextSolution.getValue() > bestSolution.getValue()) {
                bestSolution = nextSolution;
            }

            System.out.println("ITERAÇÃO " + (i + 1) + ", MELHOR VALOR: " + bestSolution.getValue());
        }

        return bestSolution;
    }

    private boolean isTabu(Knapsack knapsack) {
        for (int[] tabuItems : tabuList) {
            if (Arrays.equals(tabuItems, knapsack.getSelectedItems())) {
                return true;
            }
        }
        return false;
    }

    private List<Knapsack> generateNeighbors(Knapsack knapsack) {
        List<Knapsack> neighbors = new ArrayList<>();

        int numItems = knapsack.getNumItems();
        int[] items = knapsack.getSelectedItems();

        for (int i = 0; i < numItems; i++) {
            int[] neighborItems = Arrays.copyOf(items, numItems);
            neighborItems[i] = (neighborItems[i] + 1) % 2;

            neighbors.add(new Knapsack(knapsack.getValues(), knapsack.getWeights(), neighborItems));
        }

        return neighbors;
    }

    // Knapsack class represents a candidate solution
    private class Knapsack {
        private int[] values;
        private int[] weights;
        private int[] selectedItems;

        public Knapsack(int[] values, int[] weights, int[] selectedItems) {
            this.values = values;
            this.weights = weights;
            this.selectedItems = selectedItems;
        }

        public int getValue() {
            int totalValue = 0;
            for (int i = 0; i < values.length; i++) {
                if (selectedItems[i] == 1) {
                    totalValue += values[i];
                }
            }
            return totalValue;
        }

        public int getWeight() {
            int totalWeight = 0;
            for (int i = 0; i < weights.length; i++) {
                if (selectedItems[i] == 1) {
                    totalWeight += weights[i];
                }
            }
            return totalWeight;
        }

        public int[] getValues() {
            return values;
        }

        public int[] getWeights() {
            return weights;
        }

        public int[] getSelectedItems() {
            return selectedItems;
        }

        public int getNumItems() {
            return values.length;
        }
    }

    public static void main(String[] args) {
//        TabuSearchKnapsack tabuSearch = new TabuSearchKnapsack(10, 50);
        TabuSearch tabuSearch = new TabuSearch(10, 524);


        // Define the problem instance
        int[] values = {60, 100, 120};
        int[] weights = {10, 20, 30};
        int[] initialItems = {1, 0, 1}; // Initial solution

        List<Objeto> objetos = getObjetos();
        int[] pesos = new int[objetos.size()];
        int[] valores = new int[objetos.size()];
        int[] solucaoInicial = new int[objetos.size()]; // Initial solution

        for (int i = 0; i < objetos.size(); i++) {
            pesos[i] = objetos.get(i).getPeso().intValue();
            valores[i] = objetos.get(i).getValorTotal().intValue();
        }

//        Knapsack initialSolution = tabuSearch.new Knapsack(values, weights, initialItems);

//        Knapsack initialSolution = tabuSearch.new Knapsack(valores, pesos, initialItems);
        Knapsack initialSolution = tabuSearch.new Knapsack(valores, pesos, solucaoInicial);



        // Run the Tabu Search algorithm
        Knapsack bestSolution = tabuSearch.search(initialSolution, 100);

        System.out.println("Best Solution Value: " + bestSolution.getValue());
        System.out.println("Best Solution Weight: " + bestSolution.getWeight());
        System.out.println("Best Solution Items: " + Arrays.toString(bestSolution.getSelectedItems()));
    }
}


//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class TabuSearchKnapsack {
//
//    private int tabuSize;
//    private List<int[]> tabuList;
//    private int capacity;
//
//    public TabuSearchKnapsack(int tabuSize, int capacity) {
//        this.tabuSize = tabuSize;
//        this.capacity = capacity;
//        tabuList = new ArrayList<>();
//    }
//
//    public Knapsack search(Knapsack initialSolution, int iterations) {
//        Knapsack bestSolution = initialSolution;
//
//        for (int i = 0; i < iterations; i++) {
//            List<Knapsack> neighbors = generateNeighbors(bestSolution);
//            Knapsack nextSolution = null;
//
//            for (Knapsack neighbor : neighbors) {
//                if (!isTabu(neighbor) && neighbor.getWeight() <= capacity &&
//                        (nextSolution == null || neighbor.getValue() > nextSolution.getValue())) {
//                    nextSolution = neighbor;
//                }
//            }
//
//            tabuList.add(nextSolution.getSelectedItems());
//
//            if (tabuList.size() > tabuSize) {
//                tabuList.remove(0);
//            }
//
//            if (nextSolution.getValue() > bestSolution.getValue()) {
//                bestSolution = nextSolution;
//            }
//
//            System.out.println("Iteration " + (i + 1) + ", Best Value: " + bestSolution.getValue());
//        }
//
//        return bestSolution;
//    }
//
//    private boolean isTabu(Knapsack knapsack) {
//        for (int[] tabuItems : tabuList) {
//            if (Arrays.equals(tabuItems, knapsack.getSelectedItems())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private List<Knapsack> generateNeighbors(Knapsack knapsack) {
//        List<Knapsack> neighbors = new ArrayList<>();
//
//        int numItems = knapsack.getNumItems();
//        int[] items = knapsack.getSelectedItems();
//
//        for (int i = 0; i < numItems; i++) {
//            int[] neighborItems = Arrays.copyOf(items, numItems);
//            neighborItems[i] = (neighborItems[i] + 1) % 2;
//
//            neighbors.add(new Knapsack(knapsack.getValues(), knapsack.getWeights(), neighborItems));
//        }
//
//        return neighbors;
//    }
//
//    // Knapsack class represents a candidate solution
//    private class Knapsack {
//        private int[] values;
//        private int[] weights;
//        private int[] selectedItems;
//
//        public Knapsack(int[] values, int[] weights, int[] selectedItems) {
//            this.values = values;
//            this.weights = weights;
//            this.selectedItems = selectedItems;
//        }
//
//        public int getValue() {
//            int totalValue = 0;
//            for (int i = 0; i < values.length; i++) {
//                if (selectedItems[i] == 1) {
//                    totalValue += values[i];
//                }
//            }
//            return totalValue;
//        }
//
//        public int getWeight() {
//            int totalWeight = 0;
//            for (int i = 0; i < weights.length; i++) {
//                if (selectedItems[i] == 1) {
//                    totalWeight += weights[i];
//                }
//            }
//            return totalWeight;
//        }
//
//        public int[] getValues() {
//            return values;
//        }
//
//        public int[] getWeights() {
//            return weights;
//        }
//
//        public int[] getSelectedItems() {
//            return selectedItems;
//        }
//
//        public int getNumItems() {
//            return values.length;
//        }
//    }
//
//    public static void main(String[] args) {
//        TabuSearchKnapsack tabuSearch = new TabuSearchKnapsack(10, 50);
//
//        // Define the problem instance
//        int[] values = {60, 100, 120};
//        int[] weights = {10, 20, 30};
//        int[] initialItems = {1, 0, 1}; // Initial solution
//
//        Knapsack initialSolution = tabuSearch.new Knapsack(values, weights, initialItems);
//
//        // Run the Tabu Search algorithm
//        Knapsack bestSolution = tabuSearch.search(initialSolution, 100);
//
//        System.out.println("Best Solution Value: " + bestSolution.getValue());
//        System.out.println("Best Solution Weight: " + bestSolution.getWeight());
//        System.out.println("Best Solution Items: " + Arrays.toString(bestSolution.getSelectedItems()));
//    }
//}
//
//
