package aao;

import aao.algorithms.GreedyAlgorithm;
import aao.algorithms.HillClimbingAlgorithm;
import aao.algorithms.TabuSearchAlgorithm;
import aao.utils.DataLoader;
import aao.utils.Menu;
import aao.models.OptimalSolution;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Locale;

@Slf4j
public class Application {
    public static void main(String[] args) throws IOException {
        Locale.setDefault(new Locale.Builder().setLanguage("en").setRegion("US").build());

        DataLoader.Data data = DataLoader.loadCapData("cap.txt");
        OptimalSolution optData = DataLoader.loadCapOptData("cap.txt.opt");

        if (data == null) {
            log.error("Error loading data");
            return;
        }

        OptimalSolution optimalSolution = new OptimalSolution(optData.getAssignments(), optData.getCost());

        TabuSearchAlgorithm tabuSearchAlgorithm = new TabuSearchAlgorithm(data.warehouses, data.customers);
        GreedyAlgorithm greedyAlgorithm = new GreedyAlgorithm(data.warehouses, data.customers);
        HillClimbingAlgorithm hillClimbing = new HillClimbingAlgorithm(data.warehouses, data.customers);

        Menu.menu(greedyAlgorithm, tabuSearchAlgorithm, hillClimbing, optimalSolution);
    }
}
