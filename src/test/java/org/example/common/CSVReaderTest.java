package org.example.common;

import org.example.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CSVReaderTest {

    private File tempCsvFile;

    @BeforeEach
    void setUp() throws IOException {
        tempCsvFile = File.createTempFile("test-employees", ".csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempCsvFile))) {
            writer.write("Id,firstName,lastName,salary,managerId\n");
            writer.write("1,Alice,Smith,100000,\n");
            writer.write("2,Bob,Johnson,60000,1\n");
            writer.write("3,Carol,White,90000,1\n");
        }
    }

    @AfterEach
    void tearDown() {
        if (tempCsvFile != null && tempCsvFile.exists()) {
            tempCsvFile.delete();
        }
    }

    @Test
    void testReadCSV_parsesCorrectly() throws IOException {
        CSVReader reader = new CSVReader();
        Map<Integer, Employee> employees = reader.readCSV(tempCsvFile.getAbsolutePath());

        assertEquals(3, employees.size());

        Employee ceo = employees.get(1);
        assertNotNull(ceo);
        assertEquals("Alice", ceo.getFirstName());
        assertEquals(-1, ceo.getManagerId());

        Employee bob = employees.get(2);
        assertNotNull(bob);
        assertEquals("Bob", bob.getFirstName());
        assertEquals(1, bob.getManagerId());
    }

    @Test
    void testReadCSV_ignoresInvalidLines() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempCsvFile))) {
            writer.write("Id,firstName,lastName,salary,managerId\n");
            writer.write("100,Invalid,Row,Too,Many,Fields\n");
            writer.write("101,Valid,One,50000,\n");
        }

        CSVReader reader = new CSVReader();
        Map<Integer, Employee> employees = reader.readCSV(tempCsvFile.getAbsolutePath());

        assertEquals(1, employees.size());
        assertTrue(employees.containsKey(101));
    }
}
