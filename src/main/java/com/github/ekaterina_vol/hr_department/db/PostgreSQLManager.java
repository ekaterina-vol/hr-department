package com.github.ekaterina_vol.hr_department.db;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class PostgreSQLManager {

    @Getter(lazy = true)
    private static final PostgreSQLManager instance = new PostgreSQLManager();

    @Setter
    private static String resourceName = "application";

    @Getter
    private Connection connection;

    private PostgreSQLManager() {
        try {
            ResourceBundle rb = ResourceBundle.getBundle(resourceName);
            String url = rb.getString("DATABASE_URL");
            String user = rb.getString("DATABASE_USER");
            String password = rb.getString("DATABASE_PASSWORD");

            connection = DriverManager.getConnection(url, user, password);
            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("CREATE TYPE IF NOT EXISTS GENDER_TYPE AS ENUM ('male', 'female')");
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS employee ( " +
                            "employee_id SERIAL PRIMARY KEY, " +
                            "first_name VARCHAR(100) NOT NULL, " +
                            "last_name VARCHAR(100) NOT NULL, " +
                            "birth_date DATE NOT NULL, " +
                            "gender GENDER_TYPE, " +
                            "created_at DATE NOT NULL " +
                            ")"
            );
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS post ( " +
                            "post_id SERIAL PRIMARY KEY, " +
                            "title VARCHAR(100) NOT NULL, " +
                            "department VARCHAR(100) NOT NULL " +
                            ")"
            );
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS storage_location ( " +
                            "location_id SERIAL PRIMARY KEY, " +
                            "rack_num INTEGER NOT NULL CHECK (rack_num > 0), " +
                            "shelf_num INTEGER NOT NULL CHECK (shelf_num > 0) " +
                            ")"
            );
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS employment ( " +
                            "employment_id SERIAL PRIMARY KEY, " +
                            "employee_id INTEGER NOT NULL, " +
                            "post_id INTEGER NOT NULL, " +
                            "start_date DATE NOT NULL, " +
                            "end_date DATE, " +
                            "CONSTRAINT fk_employment_employee " +
                            "FOREIGN KEY (employee_id) REFERENCES employee(employee_id) " +
                            "ON DELETE CASCADE " +
                            "ON UPDATE CASCADE, " +
                            "CONSTRAINT fk_employment_post " +
                            "FOREIGN KEY (post_id) REFERENCES post(post_id) " +
                            "ON DELETE CASCADE " +
                            "ON UPDATE CASCADE, " +
                            "CONSTRAINT chk_date CHECK (start_date <= end_date) " +
                            ")"
            );
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS employee_history ( " +
                            "history_id SERIAL PRIMARY KEY, " +
                            "change_date DATE NOT NULL, " +
                            "employee_id INTEGER NOT NULL, " +
                            "last_name VARCHAR(100) NOT NULL, " +
                            "FOREIGN KEY (employee_id) REFERENCES employee(employee_id) " +
                            "ON DELETE CASCADE " +
                            "ON UPDATE CASCADE " +
                            ")"
            );
        } catch (java.util.MissingResourceException e) {
            throw new RuntimeException("Cannot load database configuration: resource '" + resourceName + "' not found");
        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to database and preparing tables: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error initializing DB manager: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Exception during closing connection");
        }
    }

}
