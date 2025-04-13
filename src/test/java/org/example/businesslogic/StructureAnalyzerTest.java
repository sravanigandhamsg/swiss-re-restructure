package org.example.businesslogic;

import org.example.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StructureAnalyzerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    Map<Integer, Employee> mockEmployees;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));

        mockEmployees = new HashMap<>();

        // CEO
        Employee ceo = new Employee(1, "Alice", "Smith", 100000, -1);

        // Managers
        Employee manager1 = new Employee(2, "Bob", "Johnson", 40000, 1);
        Employee manager2 = new Employee(3, "Carol", "White", 90000, 1); // Overpaid

        // Subordinates
        Employee emp1 = new Employee(4, "Dave", "Brown", 40000, 2);
        Employee emp2 = new Employee(5, "Eve", "Davis", 38000, 2);
        Employee emp3 = new Employee(6, "Frank", "Moore", 42000, 3);

        // Deep hierarchy
        Employee emp4 = new Employee(7, "George", "Hall", 35000, 4);
        Employee emp5 = new Employee(8, "Hannah", "Adams", 30000, 7);
        Employee emp6 = new Employee(9, "Ian", "Green", 29000, 8);

        List<Employee> all = Arrays.asList(ceo, manager1, manager2, emp1, emp2, emp3, emp4, emp5, emp6);
        for (Employee e : all) {
            mockEmployees.put(e.getId(), e);
        }
    }

    @Test
    void testPrintManagerSalaryCompliance() {
        StructureAnalyzer analyzer = new StructureAnalyzer(mockEmployees);
        analyzer.printManagerSalaryCompliance();
        String output = outContent.toString();
        System.out.println(output);

        assertTrue(output.contains("Manager: Bob Johnson, Underpaid by:"), "Should detect underpaid manager");
        assertTrue(output.contains("Manager: Carol White, Overpaid by:"), "Should detect overpaid manager");
    }

    @Test
    void testPrintEmployeesWithLongReportingLines() {
        StructureAnalyzer analyzer = new StructureAnalyzer(mockEmployees);
        analyzer.printEmployeesWithLongReportingLines();
        String output = outContent.toString();

        assertTrue(output.contains("Employee: Ian Green, Managers Above: 5, Exceeds By: 1"),
                "Should detect long reporting line for Ian Green");
    }

    @Test
    void testNoManagersOutOfRange() {
        // CEO with only one manager under them, no subordinates
        Map<Integer, Employee> smallTeam = new HashMap<>();
        smallTeam.put(1, new Employee(1, "Solo", "CEO", 90000, -1));
        smallTeam.put(2, new Employee(2, "Mini", "Manager", 70000, 1));

        StructureAnalyzer analyzer = new StructureAnalyzer(smallTeam);
        analyzer.printManagerSalaryCompliance();
        String output = outContent.toString();

        assertTrue(output.contains("- None") || !output.contains("Manager Name:"),
                "No managers should be flagged in simple setup");
    }

    @AfterEach
    void restoreSystemOutStream() {
        System.setOut(originalOut);
    }
}
