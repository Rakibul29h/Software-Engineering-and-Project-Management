<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${initParam.gameTitle}"/> - Game</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h1 class="title"><c:out value="${initParam.gameTitle}"/></h1>

    <div class="card">
        <h2>Good luck, <c:out value="${sessionScope.playerName}"/>!</h2>

        <c:if test="${not empty error}">
            <div class="error"><c:out value="${error}"/></div>
        </c:if>

        <form action="${pageContext.request.contextPath}/game" method="post" autocomplete="off">
            <input type="hidden" name="action" value="submit">

            <div class="question">
                <div class="qhead"><span>Question 1 &ndash; Prime Number</span><span class="marks">2 marks</span></div>
                <div class="text">Is <strong>${sessionScope.questions.primeNumber}</strong> a prime number?</div>
                <div class="hint">Enter 1 if it is prime, 0 if it is not.</div>
                <input type="text" name="primeAnswer" value="<c:out value='${primeAnswer}'/>"
                       placeholder="1 or 0" inputmode="numeric" required>
            </div>

            <div class="question">
                <div class="qhead"><span>Question 2 &ndash; Modulus</span><span class="marks">2 marks</span></div>
                <div class="text">What is <strong>${sessionScope.questions.modA} % ${sessionScope.questions.modB}</strong> ?</div>
                <div class="hint">Enter the remainder as a whole number.</div>
                <input type="text" name="modAnswer" value="<c:out value='${modAnswer}'/>"
                       placeholder="Remainder" inputmode="numeric" required>
            </div>

            <div class="question">
                <div class="qhead"><span>Question 3 &ndash; Trigonometry</span><span class="marks">3 marks</span></div>
                <div class="text">Calculate <strong>${sessionScope.questions.trig1Function}(${sessionScope.questions.trig1Angle}&deg;)</strong></div>
                <div class="hint">Angle is in degrees. Answer as a decimal (within &plusmn;0.01), e.g. 0.5</div>
                <input type="text" name="trig1Answer" value="<c:out value='${trig1Answer}'/>"
                       placeholder="Decimal value" inputmode="decimal" required>
            </div>

            <div class="question">
                <div class="qhead"><span>Question 4 &ndash; Trigonometry</span><span class="marks">3 marks</span></div>
                <div class="text">Calculate <strong>${sessionScope.questions.trig2Function}(${sessionScope.questions.trig2Angle}&deg;)</strong></div>
                <div class="hint">Angle is in degrees. Answer as a decimal (within &plusmn;0.01), e.g. 0.5</div>
                <input type="text" name="trig2Answer" value="<c:out value='${trig2Answer}'/>"
                       placeholder="Decimal value" inputmode="decimal" required>
            </div>

            <button type="submit" class="btn">Submit Answers</button>
        </form>
    </div>
</div>
</body>
</html>
