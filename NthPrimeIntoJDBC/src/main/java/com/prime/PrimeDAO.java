package com.prime;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PrimeDAO {

    public static void savePrime(int n, long prime)
            throws SQLException {

        String sql = "INSERT INTO prime_results (input_n, prime_number) VALUES (?, ?)";

        try (
                Connection connection = DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, n);
            statement.setLong(2, prime);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Successfully saved to database!");
            }
        }
    }
}

