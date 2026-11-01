package com.prime;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter n: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid integer.");
            scanner.close();
            return;
        }

        int n = scanner.nextInt();

        if (n <= 0) {
            System.out.println("Please enter a positive integer.");
            scanner.close();
            return;
        }

        // Find nth prime number
        long prime = PrimeUtil.findNthPrime(n);

        // Display result
        System.out.println(
                "The " + n + "th prime number is: " + prime
        );

        // Save result to database
        try {

            PrimeDAO.savePrime(n, prime);

        } catch (SQLException e) {

            System.out.println("Database error: " + e.getMessage());

        }

        scanner.close();
    }
}
