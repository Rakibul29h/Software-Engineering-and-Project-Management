package com.prime;

public class PrimeUtil {

    // Check whether a number is prime
    public static boolean isPrime(long number) {

        if (number < 2) {
            return false;
        }

        for (long i = 2; i <= number / i; i++) {

            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    // Find the nth prime number
    public static long findNthPrime(int n) {

        int count = 0;
        long number = 1;

        while (count < n) {

            number++;

            if (isPrime(number)) {
                count++;
            }
        }

        return number;
    }
}

