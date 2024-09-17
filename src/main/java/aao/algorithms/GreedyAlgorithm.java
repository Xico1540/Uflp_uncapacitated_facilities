package aao.algorithms;

import aao.models.Customer;
import aao.models.Warehouse;

import java.util.List;

public class GreedyAlgorithm {
    private final List<Warehouse> warehouses;
    private final List<Customer> customers;

    public GreedyAlgorithm(List<Warehouse> warehouses, List<Customer> customers) {
        this.warehouses = warehouses;
        this.customers = customers;
    }

    public void solveByGreedyAlgorithm() {
        int numWarehouses = warehouses.size();
        int numCustomers = customers.size();

        boolean[] openWarehouses = new boolean[numWarehouses];
        int[] customerAllocation = new int[numCustomers];

        double totalCost = 0.0; // Variável que mantém o custo total

        // Itera sobre cada cliente
        for (int c = 0; c < numCustomers; c++) {
            double minCost = Double.MAX_VALUE; // Inicializa o custo mínimo com o maior valor possível
            int bestWarehouse = -1;            // Inicializa o melhor armazém como não encontrado

            // Itera sobre cada armazém para encontrar o de menor custo para o cliente atual
            for (int w = 0; w < numWarehouses; w++) {
                double cost = customers.get(c).getAllocationCosts().get(w); // Obtém o custo de alocação do cliente ao armazém
                if (!openWarehouses[w]) { // Se o armazém ainda não está aberto, adiciona o custo fixo de abertura
                    cost += warehouses.get(w).getFixedCost();
                }

                // Atualiza o custo mínimo e o melhor armazém se o custo atual for menor
                if (cost < minCost) {
                    minCost = cost;
                    bestWarehouse = w;
                }
            }

            // Se um melhor armazém foi encontrado
            if (bestWarehouse != -1) {
                // Abre o armazém se ainda não está aberto e adiciona o custo fixo ao custo total
                if (!openWarehouses[bestWarehouse]) {
                    openWarehouses[bestWarehouse] = true;
                    totalCost += warehouses.get(bestWarehouse).getFixedCost();
                }
                customerAllocation[c] = bestWarehouse; // Aloca o cliente ao melhor armazém encontrado
                totalCost += customers.get(c).getAllocationCosts().get(bestWarehouse); // Adiciona o custo de alocação ao custo total
            }
        }

        // Imprime o custo total final
        System.out.println("Total Cost: " + totalCost);
        for (int i = 0; i < numCustomers; i++) {
            System.out.println("Customer " + i + " allocated to Warehouse " + customerAllocation[i]);
        }
    }
}
