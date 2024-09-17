package aao.algorithms;

import aao.models.Customer;
import aao.models.Warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HillClimbingAlgorithm {
    List<Warehouse> warehouses;
    List<Customer> customers;
    Map<Customer, Warehouse> solution;
    double currentCost;

    public HillClimbingAlgorithm(List<Warehouse> warehouses, List<Customer> customers) {
        this.warehouses = warehouses;
        this.customers = customers;
        this.solution = new HashMap<>();
        this.currentCost = Integer.MAX_VALUE; // Inicializa o custo atual com o maior valor possível
    }

    // Método para inicializar uma solução aleatória
    public void initializeSolution() {
        Random rand = new Random();
        for (Customer customer : customers) {
            Warehouse warehouse = warehouses.get(rand.nextInt(warehouses.size())); // Seleciona um armazém aleatório
            solution.put(customer, warehouse); // Aloca o cliente ao armazém selecionado
        }
        currentCost = calculateTotalCost(solution); // Calcula o custo total da solução inicial
    }

    // Método para calcular o custo total de uma solução
    private double calculateTotalCost(Map<Customer, Warehouse> solution) {
        double totalCost = 0;
        Map<Warehouse, Integer> warehouseCount = new HashMap<>();
        for (Warehouse w : warehouses) {
            warehouseCount.put(w, 0); // Inicializa o contador de clientes para cada armazém
        }
        for (Customer customer : customers) {
            Warehouse warehouse = solution.get(customer);
            totalCost += customer.getAllocationCosts().get(warehouses.indexOf(warehouse)); // Soma o custo de alocação
            warehouseCount.put(warehouse, warehouseCount.get(warehouse) + 1); // Atualiza o contador de clientes do armazém
        }
        for (Warehouse warehouse : warehouses) {
            if (warehouseCount.get(warehouse) > 0) {
                totalCost += warehouse.getFixedCost(); // Adiciona o custo fixo dos armazéns que estão em uso
            }
        }
        return totalCost; // Retorna o custo total
    }

    // Método que implementa o algoritmo de Hill Climbing
    public void hillClimbing() {
        boolean improved = true; // Flag para verificar se houve melhora
        while (improved) {
            improved = false;
            Map<Customer, Warehouse> bestNeighbor = null; // Melhor vizinho encontrado
            double bestCost = currentCost; // Melhor custo encontrado

            // Itera sobre cada cliente
            for (Customer customer : customers) {
                // Tenta alocar o cliente a cada armazém
                for (Warehouse warehouse : warehouses) {
                    if (solution.get(customer) != warehouse) { // Verifica se o cliente já está alocado ao armazém
                        Map<Customer, Warehouse> neighbor = new HashMap<>(solution);
                        neighbor.put(customer, warehouse); // Cria uma solução vizinha
                        double cost = calculateTotalCost(neighbor); // Calcula o custo da solução vizinha
                        if (cost < bestCost) { // Se o custo da solução vizinha é melhor, atualiza o melhor custo e vizinho
                            bestCost = cost;
                            bestNeighbor = neighbor;
                            improved = true;
                        }
                    }
                }
            }

            // Se encontrou uma solução vizinha melhor, atualiza a solução atual e o custo
            if (improved) {
                solution = bestNeighbor;
                currentCost = bestCost;
            }
        }

        // Imprime o custo final após o algoritmo
        System.out.println("Final cost: " + currentCost);
    }
}
