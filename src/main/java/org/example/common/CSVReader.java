package org.example.common;

import org.example.model.Employee;

import java.io.*;
import java.util.*;

public class CSVReader {

    public Map<Integer, Employee> readCSV(String filePath) throws IOException {
        System.out.println("Reading CSV from: " + filePath);

        Map<Integer, Employee> employeeMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // to skip header

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if(tokens.length>5){
                    System.out.println("⚠ Invalid row: " + line);
                    continue;
                }
                int id = Integer.parseInt(tokens[0]);
                String firstName = tokens[1];
                String lastName = tokens[2];
                double salary = Double.parseDouble(tokens[3]);
                int managerId = (tokens.length == 4 || tokens[4].isBlank()) ? -1 : Integer.parseInt(tokens[4]);

                employeeMap.put(id, new Employee(id, firstName, lastName, salary, managerId));

            }
        }
        return employeeMap;
    }

}
