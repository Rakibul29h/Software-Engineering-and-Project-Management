package com.prime;




import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/check-prime")
public class HelloServlet extends HttpServlet {

    // Prime number checking method
    private boolean isPrime(int number) {

        if (number <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(number); i++) {

            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String input = request.getParameter("number");

        try {

            if (input == null || input.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Please enter a number.\"}");
                return;
            }

            int number = Integer.parseInt(input.trim());

            boolean result = isPrime(number);

            String message;

            if (result) {
                message = number + " is a Prime Number.";
            } else {
                message = number + " is NOT a Prime Number.";
            }

            out.print("{\"number\":" + number +
                    ",\"isPrime\":" + result +
                    ",\"message\":\"" + message + "\"}");

        } catch (NumberFormatException e) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            out.print("{\"error\":\"Please enter a valid integer.\"}");
        }
    }
}

