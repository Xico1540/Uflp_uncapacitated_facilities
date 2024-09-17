package aao.models;

import aao.utils.DataLoader;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OptimalSolution {
    private List<Double> assignments;
    private Double cost;

    public OptimalSolution(List<Double> assignments, Double cost) {
        this.assignments = assignments;
        this.cost = cost;
    }

    public void printTable() {
        System.out.println("+-----------+---------------------+");
        System.out.println("| Customer  | Allocated Warehouse |");
        System.out.println("+-----------+---------------------+");
        for (int i = 0; i < assignments.size(); i++) {
            System.out.printf("| %-9d | %-19.2f |%n", i, assignments.get(i));
            System.out.println("+-----------+---------------------+");
        }
        System.out.println("Total Cost: " + cost);
    }
}
