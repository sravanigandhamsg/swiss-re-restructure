package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private double salary;
    private Integer managerId;
    private List<Employee> subordinates = new ArrayList<>();

    public Employee(int Id, String FirstName, String LastName, double Salary, int ManagerId){
        this.id = Id;
        this.firstName = FirstName;
        this.lastName = LastName;
        this.salary = Salary;
        this.managerId = ManagerId;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    public Integer getManagerId() {
        return managerId;
    }


    public double getSalary() {
        return salary;
    }

    public String getLastName() {
        return lastName;
    }


    public String getFirstName() {
        return firstName;
    }


    public int getId() {
        return id;
    }

    public String getFullName() {
        return this.firstName+" "+this.lastName;
    }
}
