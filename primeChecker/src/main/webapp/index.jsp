
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Prime Number Checker</title>

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background: #f1f5f9;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }

        .container {
            background: white;
            padding: 35px;
            width: 400px;
            border-radius: 12px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #1e293b;
        }

        input {
            width: 100%;
            padding: 12px;
            margin-top: 20px;
            border: 1px solid #cbd5e1;
            border-radius: 6px;
            font-size: 16px;
        }

        button {
            width: 100%;
            padding: 12px;
            margin-top: 15px;
            background: #2563eb;
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            cursor: pointer;
        }

        button:hover {
            background: #1d4ed8;
        }

        #result {
            margin-top: 20px;
            padding: 15px;
            border-radius: 6px;
            text-align: center;
            font-weight: bold;
            display: none;
        }

        .prime {
            background: #dcfce7;
            color: #166534;
        }

        .not-prime {
            background: #fee2e2;
            color: #991b1b;
        }

        .error {
            background: #fef3c7;
            color: #92400e;
        }
    </style>

</head>

<body>

<div class="container">

    <h1>Prime Number Checker</h1>

    <input
            type="number"
            id="number"
            placeholder="Enter a number"
    >

    <button onclick="checkPrime()">
        Check Prime
    </button>

    <!-- Result will appear here -->
    <div id="result"></div>

</div>

<script>

    async function checkPrime() {

        const number = document.getElementById("number").value;

        const resultDiv = document.getElementById("result");

        if (number.trim() === "") {
            showResult("Please enter a number.", "error");
            return;
        }

        resultDiv.style.display = "block";
        resultDiv.className = "";
        resultDiv.innerText = "Checking...";

        try {

            const response = await fetch(
                "<%= request.getContextPath() %>/check-prime?number="
                + encodeURIComponent(number)
            );

            const data = await response.json();

            if (!response.ok) {
                showResult(data.error, "error");
                return;
            }

            if (data.isPrime) {

                showResult(data.message, "prime");

            } else {

                showResult(data.message, "not-prime");

            }

        } catch (error) {

            showResult("Something went wrong. Please try again.", "error");

            console.error(error);
        }
    }

    function showResult(message, className) {

        const resultDiv = document.getElementById("result");

        resultDiv.innerText = message;

        resultDiv.className = className;

        resultDiv.style.display = "block";
    }

</script>

</body>
</html>
