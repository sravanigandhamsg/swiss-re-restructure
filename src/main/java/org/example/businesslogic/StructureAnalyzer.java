package org.example.businesslogic;

import org.example.model.Employee;

import java.util.HashMap;
import java.util.Map;

public class StructureAnalyzer {
    
    private final Map<Integer, Employee> employeesById = new HashMap<>();

    public StructureAnalyzer(Map<Integer, Employee> employeeMap) {
        buildHierarchy(employeeMap);
    }

    private void buildHierarchy(Map<Integer, Employee> employeeMap) {
        for (Employee employee : employeeMap.values()) {
            if (employee.getManagerId() == null || employee.getManagerId() == -1) {
                System.out.println("CEO: " + employee.getFullName());
            } else {
                Employee manager = employeeMap.get(employee.getManagerId());
                if (manager != null) {
                    manager.getSubordinates().add(employee);
                }
            }
        }
        employeesById.putAll(employeeMap);
    }


    public void printManagerSalaryCompliance(){
        Map<Integer, String> managersWithLessSalary = new HashMap<>();
        Map<Integer, String> managersWithMoreSalary = new HashMap<>();
        for (Employee emp : employeesById.values()) {
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
        System.out.println("\nEmployees with long reporting chains (>4 levels):");
        for (Employee employee : employeesById.values()) {
            int depth = 0;
            Employee current = employee;
            while (current.getManagerId() != -1) {
                Employee manager = employeesById.get(current.getManagerId());
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
                        employee.getFullName(), depth, depth - 4);
            }
        }
    }
}
