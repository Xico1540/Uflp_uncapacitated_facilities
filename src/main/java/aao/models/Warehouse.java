package aao.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Warehouse {
    int capacity;
    double fixedCost;
    boolean open;

    public Warehouse(int capacity, double fixedCost, boolean open) {
        this.capacity = capacity;
        this.fixedCost = fixedCost;
        this.open = open;
    }
}
