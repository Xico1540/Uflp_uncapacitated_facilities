package aao.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Customer {
    int demand;
    List<Double> allocationCosts;

    public Customer() {
        demand = 0;
        allocationCosts = new ArrayList<>();
    }

    public void addAllocationCosts(Double allocationCost) {
        this.allocationCosts.add(allocationCost);
    }

    public void addDemand(int demand) {
        this.demand = demand;
    }
}
