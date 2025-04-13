package org.example.common;

import org.example.model.Employee;

import java.io.*;
import java.util.*;

public class CSVReader {

    public Map<Integer, Employee> readCSV(String filePath) throws IOException {
        System.out.println("Reading csv from :"+ filePath);

        Map<Integer, Employee> data = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // to skip header

            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                if(row.length>5){
                    System.out.println("row length is not correct for line : "+line);
                }else{
                    if(row.length==4){
                        Employee emp = new Employee(Integer.parseInt(row[0]), row[1],row[2],Double.parseDouble(row[3]), -1);
                        data.put(Integer.parseInt(row[0]),emp);
                    }else{
                        Employee emp = new Employee(Integer.parseInt(row[0]), row[1],row[2],Double.parseDouble(row[3]), Integer.parseInt(row[4]));
                        data.put(Integer.parseInt(row[0]),emp);
                    }
                }
            }
        }
        return data;
    }

}
