package org.example;

import org.example.businesslogic.StructureAnalyzer;
import org.example.common.CSVReader;
import org.example.model.Employee;

import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -jar <jar-file> <csv-file-path>");
            return;
        }

        String csvFilePath = args[0];
        CSVReader reader = new CSVReader();
        try {
            Map<Integer, Employee> employees = reader.readCSV(csvFilePath);
            StructureAnalyzer analyzer = new StructureAnalyzer(employees);
            analyzer.printManagerSalaryCompliance();
            analyzer.printEmployeesWithLongReportingLines();

        } catch (IOException e) {
            System.err.println("Error reading CSV: " + e.getMessage());
        }

    }
}