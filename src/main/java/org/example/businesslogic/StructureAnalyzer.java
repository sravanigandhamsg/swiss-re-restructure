package org.example.businesslogic;

import org.example.model.Employee;

import java.util.HashMap;
import java.util.Map;

public class StructureAnalyzer {

    Map<Integer, Employee> heirarchialEmployees = new HashMap<>();

    public StructureAnalyzer(Map<Integer, Employee> employees){
        //build heirarchy when called
        for (Employee emp : employees.values()) {
            if (emp.getManagerId() == null) {
                System.out.println("CEO: " + emp.getFullName());
            } else {
                Employee manager = employees.get(emp.getManagerId());
                if (manager != null) {
                    manager.getSubordinates().add(emp);
                }
            }
        }

        heirarchialEmployees.putAll(employees);
    }


    public void printManagerSalaryCompliance(){
        System.out.println("Managers earning less or more than they should:");
        Map<Integer, String> managersWithLessSalary = new HashMap<>();
        Map<Integer, String> managersWithMoreSalary = new HashMap<>();
        for (Employee emp : heirarchialEmployees.values()) {
            if (!emp.getSubordinates().isEmpty()) {
                double avgSubSalary = emp.getSubordinates().stream()
                        .mapToDouble(Employee::getSalary)
                        .average().orElse(0);

                double minSalary = avgSubSalary * 1.2;
                double maxSalary = avgSubSalary * 1.5;

                if (emp.getSalary() < minSalary) {
                    double shortfall = minSalary - emp.getSalary();
                    managersWithLessSalary.put(emp.getId(),
                            String.format("Manager Name: %s, Less Amount: %.2f", emp.getFullName(), shortfall));
                } else if (emp.getSalary() > maxSalary) {
                    double excess = emp.getSalary() - maxSalary;
                    managersWithMoreSalary.put(emp.getId(),
                            String.format("Manager Name: %s, Excess Amount: %.2f", emp.getFullName(), excess));
                }
            }
        }

        System.out.println("\nManagers earning less than they should:");
        if (managersWithLessSalary.isEmpty()) {
            System.out.println("- None");
        } else {
            managersWithLessSalary.values().forEach(System.out::println);
        }

        System.out.println("\nManagers earning more than they should:");
        if (managersWithMoreSalary.isEmpty()) {
            System.out.println("- None");
        } else {
            managersWithMoreSalary.values().forEach(System.out::println);
        }

    }

    public void printEmployeesWithLongReportingLines(){
        System.out.println("\nEmployees with too long reporting lines:");
        for (Employee emp : heirarchialEmployees.values()) {
            int depth = 0;
            Employee current = emp;
            while (current.getManagerId() != -1) {
                Employee manager = heirarchialEmployees.get(current.getManagerId());
                if (manager == null) {
                    System.out.printf("⚠ Warning: Employee with ID %s not found",
                            current.getManagerId());
                    break;
                }
                depth++;
                current = manager;
            }
            if (depth > 4) {
                System.out.printf("Employee Name: %s, Managers Above: %d, Exceeds By: %d%n",
                        emp.getFullName(), depth, depth - 4);
            }
        }
    }
}
