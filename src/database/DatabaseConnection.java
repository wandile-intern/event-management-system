/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author walter n
 */
public class DatabaseConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/eventmanagementsystem";
    private static final String USER = "root";
    private static final String PASSWORD = "Khanyisile13"; 

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }   
}
