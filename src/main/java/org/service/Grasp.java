package org.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Grasp {


    public class GRASPKnapsack {

        private static class Item {
            int weight;
            int value;

            public Item(int weight, int value) {
                this.weight = weight;
                this.value = value;
            }
        }

        public static int graspKnapsack(int[] weights, int[] values, int maxWeight, int numIterations) {
            int numItems = weights.length;
            List<Item> itemList = new ArrayList<>();

            // Create a list of items with their respective weights and values
            for (int i = 0; i < numItems; i++) {
                itemList.add(new Item(weights[i], values[i]));
            }

            int bestValue = 0;

            for (int iter = 0; iter < numIterations; iter++) {
                int[] currentSolution = constructGreedyRandomizedSolution(itemList, maxWeight);
                int currentWeight = calculateWeight(currentSolution, itemList);
                int currentValue = calculateValue(currentSolution, itemList);

                // Apply local search to improve the current solution
                currentSolution = localSearch(currentSolution, itemList, maxWeight);
                currentValue = calculateValue(currentSolution, itemList);

                if (currentValue > bestValue) {
                    bestValue = currentValue;
                }
            }

            return bestValue;
        }

        private static int[] constructGreedyRandomizedSolution(List<Item> itemList, int maxWeight) {
            int numItems = itemList.size();
            int[] solution = new int[numItems];

            for (int i = 0; i < numItems; i++) {
                solution[i] = 0;
            }

            Random random = new Random();

            while (true) {
                List<Integer> candidateIndices = new ArrayList<>();

                for (int i = 0; i < numItems; i++) {
                    if (solution[i] == 0 && itemList.get(i).weight <= maxWeight) {
                        candidateIndices.add(i);
                    }
                }

                if (candidateIndices.isEmpty()) {
                    break;
                }

                double sumValues = 0;
                for (int index : candidateIndices) {
                    sumValues += itemList.get(index).value;
                }

                double randomValue = random.nextDouble() * sumValues;

                double partialSum = 0;
                int chosenIndex = -1;
                for (int index : candidateIndices) {
                    partialSum += itemList.get(index).value;
                    if (partialSum >= randomValue) {
                        chosenIndex = index;
                        break;
                    }
                }

                if (chosenIndex != -1) {
                    solution[chosenIndex] = 1;
                    maxWeight -= itemList.get(chosenIndex).weight;
                }
            }

            return solution;
        }

        private static int calculateWeight(int[] solution, List<Item> itemList) {
            int numItems = itemList.size();
            int weight = 0;

            for (int i = 0; i < numItems; i++) {
                if (solution[i] == 1) {
                    weight += itemList.get(i).weight;
                }
            }

            return weight;
        }

        private static int calculateValue(int[] solution, List<Item> itemList) {
            int numItems = itemList.size();
            int value = 0;

            for (int i = 0; i < numItems; i++) {
                if (solution[i] == 1) {
                    value += itemList.get(i).value;
                }
            }

            return value;
        }

        private static int[] localSearch(int[] solution, List<Item> itemList, int maxWeight) {
            int numItems = itemList.size();
            int[] bestSolution = new int[numItems];
            System.arraycopy(solution, 0, bestSolution, 0, numItems);
            int bestValue = calculateValue(bestSolution, itemList);
            int numIterations = 100; // Number of local search iterations

            for (int iter = 0; iter < numIterations; iter++) {
                int[] newSolution = new int[numItems];
                System.arraycopy(solution, 0, newSolution, 0, numItems);

                // Perform a random swap between two items
                Random random = new Random();
                int index1 = random.nextInt(numItems);
                int index2 = random.nextInt(numItems);

                newSolution[index1] = (newSolution[index1] + 1) % 2;
                newSolution[index2] = (newSolution[index2] + 1) % 2;

                int newValue = calculateValue(newSolution, itemList);
                int newWeight = calculateWeight(newSolution, itemList);

                // Update the best solution if the new solution is feasible and better
                if (newWeight <= maxWeight && newValue > bestValue) {
                    System.arraycopy(newSolution, 0, bestSolution, 0, numItems);
                    bestValue = newValue;
                }
            }

            return bestSolution;
        }

        public static void main(String[] args) {
            int[] weights = {2, 3, 4, 5};
            int[] values = {3, 4, 5, 6};
            int maxWeight = 5;
            int numIterations = 1000;

            int maxValue = graspKnapsack(weights, values, maxWeight, numIterations);

            System.out.println("Maximum value: " + maxValue);
        }
    }

}
