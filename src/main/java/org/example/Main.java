package org.example;

import org.example.businesslogic.StructureAnalyzer;
import org.example.common.CSVReader;
import org.example.model.Employee;

import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        CSVReader reader = new CSVReader();
        try {
            Map<Integer, Employee> data =
                    reader.readCSV("/Users/sravani.gandhamthoughtworks.com/Documents/ADP/PKL/swiss-re-restructure/Employees.csv");
            System.out.println(data.toString());
            StructureAnalyzer analyzer = new StructureAnalyzer(data);
            analyzer.printManagerSalaryCompliance();
            analyzer.printEmployeesWithLongReportingLines();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}