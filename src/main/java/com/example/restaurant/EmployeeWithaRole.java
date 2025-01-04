package com.example.restaurant;



public class EmployeeWithaRole extends WelcomeEmployee {

    public EmployeeWithaRole(String employeeId, String name, String role) {
        super(employeeId, name); // Call to the parent class constructor
        setRole(role); // Set the role for the employee
    }

    // Override the displayRole method from the parent class to show additional role info
    @Override
    public void displayRole() {
        // You can customize how you display the role
        System.out.println("Employee: " + getName() + " | Role: " + getRole());
    }
}