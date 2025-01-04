package com.example.restaurant;

public class WelcomeEmployee {
    private String employeeId;
    private String name;
    private String role;

    public WelcomeEmployee(String employeeId, String name) {
        this.employeeId = employeeId;
        this.name = name;
    }

    public void displayRole() {
        System.out.println("Employee Name: " + name + ", Role: " + role);
    }

    // Getter and setter methods for employeeId, name, and role
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
