package Week8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/helpdesk_db";

    private static final String USER = "root";

    private static final String PASSWORD = "";


    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }

    //Added performance testing
    public static void main(String[] args) {

        System.out.println("Testing database connection...");

        long startTime = System.nanoTime();

        try (Connection connection = getConnection()) {

            long endTime = System.nanoTime();

            double elapsedMilliseconds =
                    (endTime - startTime) / 1_000_000.0;

            System.out.printf(
                    "Connection time: %.2f ms%n",
                    elapsedMilliseconds
            );

            System.out.println("Database connection successful!");

        } catch (SQLException e) {

            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}