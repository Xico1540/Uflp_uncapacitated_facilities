package aao.utils;

import aao.models.Customer;
import aao.models.OptimalSolution;
import aao.models.Warehouse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class DataLoader {

    @Getter
    @Setter
    public static class Data {
        public List<Warehouse> warehouses;
        public List<Customer> customers;

        public Data(List<Warehouse> warehouses, List<Customer> customers) {
            this.warehouses = warehouses;
            this.customers = customers;
        }
    }

    public static Data loadCapData(String fileName) {
        InputStream dataStream;
        try {
            dataStream = DataLoader.class.getClassLoader().getResourceAsStream(fileName);
            if (dataStream == null) {
                log.error("File not found!");
                return null;
            }
        } catch (Exception e) {
            log.error("Error reading file: {}", e.getMessage());
            return null;
        }

        try (BufferedReader dataReader = new BufferedReader(new InputStreamReader(dataStream))) {
            String line = dataReader.readLine();
            Scanner scanner = new Scanner(line);

            int facilitiesCount = scanner.hasNextInt() ? scanner.nextInt() : 0;
            int customersCount = scanner.hasNextInt() ? scanner.nextInt() : 0;

            System.out.println("Facilities: " + facilitiesCount);
            System.out.println("Customers: " + customersCount);

            List<Warehouse> warehouses = FileReaderUtility.readWarehouses(dataReader, facilitiesCount);
            List<Customer> customers = FileReaderUtility.readCustomers(dataReader);

            return new Data(warehouses, customers);
        } catch (IOException e) {
            log.error("Error processing file: {}", e.getMessage());
            return null;
        }
    }

    public static OptimalSolution loadCapOptData(String fileName) throws IOException {
        InputStream dataStream = DataLoader.class.getClassLoader().getResourceAsStream(fileName);
        BufferedReader dataReader = new BufferedReader(new InputStreamReader(dataStream));

        List<Double> assignments = new ArrayList<>();
        double cost = 0.0;

        String line;
        while ((line = dataReader.readLine()) != null) {
            String[] parts = line.split("\\s+");
            cost = Double.parseDouble(parts[parts.length - 1]);

            for (int i = 0; i < parts.length - 1; i++) {
                assignments.add(Double.parseDouble(parts[i]));
            }
        }
        dataReader.close();
        return new OptimalSolution(assignments, cost);
    }
}
