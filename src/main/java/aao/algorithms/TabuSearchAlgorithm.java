package aao.algorithms;

import aao.models.Customer;
import aao.models.Warehouse;

import java.util.*;

public class TabuSearchAlgorithm {
    private final List<Warehouse> warehouses;
    private final List<Customer> customers;

    public TabuSearchAlgorithm(List<Warehouse> warehouses, List<Customer> customers) {
        this.warehouses = warehouses;
        this.customers = customers;
    }

    // Método principal que resolve o problema usando a busca tabu
    public void solveByTabuSearch() {
        int nbStable = 0; // Número de iterações sem melhora
        Map<Customer, Warehouse> allocations = new HashMap<>(); // Alocações de clientes para armazéns
        double best = calculateTotalCost(warehouses, customers, allocations); // Melhor custo encontrado
        int tLen = 10; // Tamanho da lista tabu
        int stabilityLimit = 500; // Limite de iterações estáveis
        int it = 0; // Contador de iterações
        int[] t = new int[warehouses.size()]; // Lista tabu

        while (nbStable < stabilityLimit) { // Enquanto não atingir o limite de estabilidade
            double old = calculateTotalCost(warehouses, customers, allocations); // Custo atual
            double bestGain = bestGain(warehouses, customers, allocations); // Melhor ganho possível

            if (bestGain >= 0) { // Se o ganho é positivo
                int w = selectRandom(bestFlips(warehouses, customers, allocations)); // Seleciona uma das melhores mudanças
                warehouses.get(w).setOpen(!warehouses.get(w).isOpen()); // Inverte o estado do armazém selecionado
                t[w] = it + tLen; // Atualiza a lista tabu
                double obj = calculateTotalCost(warehouses, customers, allocations); // Calcula o custo da nova solução

                // Ajusta o tamanho da lista tabu baseado na melhora da solução
                if (obj < old && tLen > 2) {
                    tLen = tLen - 1;
                } else if (obj >= old && tLen < 10) {
                    tLen = tLen + 1;
                }
                it = it + 1;
            } else { // Se o ganho é negativo
                int w = selectRandom(openWarehouses(warehouses)); // Seleciona um armazém aberto aleatoriamente
                warehouses.get(w).setOpen(false); // Fecha o armazém selecionado
            }

            double obj = calculateTotalCost(warehouses, customers, allocations); // Calcula o custo da nova solução
            if (obj < best) { // Se a nova solução é melhor
                best = obj;
                nbStable = 0; // Reseta o contador de estabilidade
            } else {
                nbStable = nbStable + 1; // Incrementa o contador de estabilidade
            }
        }

        // Imprime o custo total final e as alocações
        System.out.println("Custo total: " + best);
    }

    // Método para calcular o custo total de uma solução
    private static double calculateTotalCost(List<Warehouse> warehouses, List<Customer> customers, Map<Customer, Warehouse> allocations) {
        double totalCost = 0.0;
        allocations.clear(); // Limpa alocações anteriores

        for (Warehouse warehouse : warehouses) {
            if (warehouse.isOpen()) {
                totalCost += warehouse.getFixedCost(); // Soma o custo fixo dos armazéns abertos
            }
        }
        for (Customer customer : customers) {
            double minCost = Double.MAX_VALUE;
            Warehouse bestWarehouse = null;
            for (int i = 0; i < warehouses.size(); i++) {
                if (warehouses.get(i).isOpen()) {
                    double cost = customer.getAllocationCosts().get(i);
                    if (cost < minCost) {
                        minCost = cost;
                        bestWarehouse = warehouses.get(i); // Armazena o melhor armazém para este cliente
                    }
                }
            }
            totalCost += minCost; // Soma o menor custo de alocação
            if (bestWarehouse != null) {
                allocations.put(customer, bestWarehouse); // Registra a alocação do cliente para o melhor armazém
            }
        }
        return totalCost; // Retorna o custo total
    }

    // Método para calcular o melhor ganho possível
    private static double bestGain(List<Warehouse> warehouses, List<Customer> customers, Map<Customer, Warehouse> allocations) {
        double bestGain = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < warehouses.size(); i++) {
            warehouses.get(i).setOpen(!warehouses.get(i).isOpen()); // Inverte o estado do armazém
            double cost = calculateTotalCost(warehouses, customers, allocations); // Calcula o custo da nova solução
            double gain = cost - calculateTotalCost(warehouses, customers, allocations); // Calcula o ganho
            if (gain > bestGain) {
                bestGain = gain; // Atualiza o melhor ganho
            }
            warehouses.get(i).setOpen(!warehouses.get(i).isOpen()); // Restaura o estado original do armazém
        }
        return bestGain; // Retorna o melhor ganho
    }

    // Método para encontrar as melhores mudanças (flips)
    private static List<Integer> bestFlips(List<Warehouse> warehouses, List<Customer> customers, Map<Customer, Warehouse> allocations) {
        List<Integer> bestFlips = new ArrayList<>();
        double bestGain = bestGain(warehouses, customers, allocations); // Calcula o melhor ganho
        for (int i = 0; i < warehouses.size(); i++) {
            warehouses.get(i).setOpen(!warehouses.get(i).isOpen()); // Inverte o estado do armazém
            double gain = calculateTotalCost(warehouses, customers, allocations) - calculateTotalCost(warehouses, customers, allocations);
            if (gain == bestGain) {
                bestFlips.add(i); // Adiciona o armazém à lista de melhores mudanças se o ganho for igual ao melhor ganho
            }
            warehouses.get(i).setOpen(!warehouses.get(i).isOpen()); // Restaura o estado original do armazém
        }
        return bestFlips; // Retorna a lista de melhores mudanças
    }

    // Método para encontrar os armazéns abertos
    private static List<Integer> openWarehouses(List<Warehouse> warehouses) {
        List<Integer> open = new ArrayList<>();
        for (int i = 0; i < warehouses.size(); i++) {
            if (warehouses.get(i).isOpen()) {
                open.add(i); // Adiciona o índice do armazém à lista se ele estiver aberto
            }
        }
        return open; // Retorna a lista de armazéns abertos
    }

    // Método para selecionar um elemento aleatório de uma lista
    private static int selectRandom(List<Integer> list) {
        return list.get(random.nextInt(list.size())); // Retorna um elemento aleatório da lista
    }

    private static final Random random = new Random(); // Instância do gerador de números aleatórios
}
