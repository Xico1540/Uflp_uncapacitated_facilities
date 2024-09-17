package aao.utils;

import aao.models.Customer;
import aao.models.Warehouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReaderUtility {
    public static List<Warehouse> readWarehouses(BufferedReader dataReader, int m) throws IOException {
        List<Warehouse> warehouses = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            String line = dataReader.readLine();
            Scanner lineScanner = new Scanner(line);
            while (lineScanner.hasNextInt()) {
                int capacity = lineScanner.nextInt();
                double fixedCost = lineScanner.nextDouble();
                warehouses.add(new Warehouse(capacity, fixedCost, false));
            }
            lineScanner.close();
        }
        return warehouses;
    }

    public static List<Customer> readCustomers(BufferedReader reader) throws IOException {
        List<Customer> customers = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            Scanner lineScanner = new Scanner(line);

            if (lineScanner.hasNextInt()) {
                int demand = lineScanner.nextInt();
                Customer customer = new Customer();
                customer.addDemand(demand);

                reader.mark(1000);

                while ((line = reader.readLine()) != null) {
                    lineScanner = new Scanner(line);

                    if (lineScanner.hasNextInt()) {
                        reader.reset();
                        break;
                    }

                    processAllocationCosts(lineScanner, customer);
                    reader.mark(1000);
                }

                customers.add(customer);
            }
        }

        return customers;
    }

    private static void processAllocationCosts(Scanner scanner, Customer customer) {
        while (scanner.hasNextDouble()) {
            double cost = scanner.nextDouble();
            customer.addAllocationCosts(cost);
        }
    }
}
