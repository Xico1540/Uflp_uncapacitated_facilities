package aao.utils;

import aao.algorithms.GreedyAlgorithm;
import aao.algorithms.HillClimbingAlgorithm;
import aao.algorithms.TabuSearchAlgorithm;
import aao.models.OptimalSolution;

import java.util.Scanner;

public class Menu {
    public static void menu(GreedyAlgorithm greedyAlgorithm, TabuSearchAlgorithm tabuSearchAlgorithm, HillClimbingAlgorithm hillClimbing, OptimalSolution optimalSolution) {
        int op = 0;
        Scanner sc = new Scanner(System.in);
        while (op != 1) {
            System.out.println("0. Exit");
            System.out.println("1. Solve by Greedy Algorithm");
            System.out.println("2. Solve by Tabu Search Algorithm");
            System.out.println("3. Solve by Hill Climbing Algorithm");
            System.out.println("4. Print optimal solution");
            System.out.println("Enter your choice: ");
            op = sc.nextInt();
            if (op >= 1 && op <= 3) {
                System.out.println("Enter the number of executions: ");
                int executions = sc.nextInt();
                switch (op) {
                    case 1 -> executeGreedyAlgorithm(greedyAlgorithm, executions);
                    case 2 -> executeTabuSearchAlgorithm(tabuSearchAlgorithm, executions);
                    case 3 -> executeHillClimbingAlgorithm(hillClimbing, executions);
                }
            } else {
                switch (op) {
                    case 4 -> optimalSolution.printTable();
                    case 0 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private static void executeGreedyAlgorithm(GreedyAlgorithm greedyAlgorithm, int executions) {
        long totalDuration = 0;
        for (int i = 0; i < executions; i++) {
            long startTime = System.nanoTime();
            greedyAlgorithm.solveByGreedyAlgorithm();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            totalDuration += duration;
        }
        long averageDuration = totalDuration / executions;
        System.out.println("Greedy Algorithm average execution time: " + averageDuration + " ns over " + executions + " executions");
    }

    private static void executeTabuSearchAlgorithm(TabuSearchAlgorithm tabuSearchAlgorithm, int executions) {
        long totalDuration = 0;
        for (int i = 0; i < executions; i++) {
            long startTime = System.nanoTime();
            tabuSearchAlgorithm.solveByTabuSearch();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1_000_000;
            totalDuration += duration;
        }
        long averageDuration = totalDuration / executions;
        System.out.println("Tabu Search Algorithm average execution time: " + averageDuration + " ms over " + executions + " executions");
    }

    private static void executeHillClimbingAlgorithm(HillClimbingAlgorithm hillClimbing, int executions) {
        long totalDuration = 0;
        for (int i = 0; i < executions; i++) {
            long startTime = System.nanoTime();
            hillClimbing.initializeSolution();
            hillClimbing.hillClimbing();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1_000_000;
            totalDuration += duration;
        }
        long averageDuration = totalDuration / executions;
        System.out.println("Hill Climbing Algorithm average execution time: " + averageDuration + " ms over " + executions + " executions");
    }
}
