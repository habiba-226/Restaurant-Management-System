# Restaurant Management System  
A JavaFX-based application for managing restaurant operations, featuring employee and customer functionalities, database integration, and advanced design patterns for scalability.

---

## Features  

### Login and Signup  
- **Authentication**: Users must sign up before logging in. Credentials are stored in a MySQL database (`restaurant_db`) configured via Connector/J.  
- **Validation**: Alerts notify users of invalid login attempts or mismatched passwords.  
- **Navigation**: Successful login redirects to the main dashboard (`nextScene`).  

---

### Employee Features  
1. **Employee Validation**  
   Employees verify their ID (stored in the `Employee` table) to access their dashboard.  

2. **Welcome Page**  
   Displays the employee’s name and role dynamically fetched from the database.  

3. **Colleague Viewer**  
   A list view of other employees with their names, roles, and profile images, excluding the logged-in employee.  

4. **Dashboard**  
   Presents key statistics and performance metrics.  

5. **Menu Management**  
   Allows employees to add, remove, or update menu items.  

---

### Customer Features  
1. **Customer Dashboard**  
   - Browse menu items with details like price and availability.  
   - Real-time cost updates for selected items.  

2. **Order Management**  
   - Customize orders with options like "Spicy" or "Extra Cheese."  
   - Stock levels are dynamically adjusted.  

3. **Receipt Generation**  
   - Includes timestamp, itemized breakdown, and total cost.  

---

## Design Patterns  

### Decorator Pattern  
- Used for adding customizations (e.g., spicy items or extra cheese) without altering the core structure of menu items.  

#### Components  
- **MenuItem**: Base class for all menu items.  
- **MenuItemDecorator**: Abstract decorator class.  
- **SpicyDecorator & ExtraCheeseDecorator**: Add £2 and £5 charges, respectively.  

---

### Inheritance & Interfaces  
- **Inheritance**:  
  - `MenuItem` (abstract) serves as the base class. Subclasses like `Pizza` and `Burger` define specific prices and behaviors.  

- **Interfaces**:  
  - `MenuOperations` defines operations implemented by the `Menu` class.  

---

## Technical Highlights  

### Database Integration  
- **Database**: MySQL database (`restaurant_db`) managed via XAMPP.  
- **Tables**:  
  - `name` (credentials).  
  - `Employee` (IDs and roles).  

---

### Exception Handling  
Ensures robust error alerts throughout the application.  

---

### Advanced Java Concepts  
- **Method Overloading**: Used in `Alert` methods.  
- **Method Overriding**: Implemented in `Menu` for `MenuOperations`.  

---

## Requirements  
- **Java 11+**  
- **Maven** (Connector/J added to dependencies).  
- **XAMPP** (for MySQL database).  

---

## How to Run  
1. Clone the repository.  
2. Set up the database using XAMPP and import the provided SQL schema.  
3. Add Connector/J to Maven dependencies.  
4. Build and run the application.  

---

## Authors  
- **Seifeldin Walid Abdelmoneum** 
- **Habiba Ahmed Samir** 

---

