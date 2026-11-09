<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="r" value="${sessionScope.result}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${initParam.gameTitle}"/> - Result</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h1 class="title"><c:out value="${initParam.gameTitle}"/></h1>

    <div class="card">
        <h2>Result</h2>

        <div class="score-box">
            <div class="sub">Player: <strong><c:out value="${r.playerName}"/></strong></div>
            <div class="big">${r.score} / 10</div>
            <div class="sub">Difficulty: ${r.difficulty}</div>
        </div>

        <c:choose>
            <c:when test="${r.saved}">
                <div class="success">Your score has been saved to the leaderboard database.</div>
            </c:when>
            <c:otherwise>
                <div class="warning"><c:out value="${r.saveError}"/></div>
            </c:otherwise>
        </c:choose>

        <table>
            <thead>
            <tr><th>Question</th><th>Your answer</th><th>Correct answer</th><th>Marks</th></tr>
            </thead>
            <tbody>
            <tr>
                <td>Q1: <c:out value="${r.primeQuestion}"/></td>
                <td><c:out value="${r.primeYourAnswer}"/></td>
                <td><c:out value="${r.primeCorrectAnswer}"/></td>
                <td class="${r.primeCorrect ? 'ok' : 'bad'}">${r.primeCorrect ? '2 / 2' : '0 / 2'}</td>
            </tr>
            <tr>
                <td>Q2: <c:out value="${r.modQuestion}"/></td>
                <td><c:out value="${r.modYourAnswer}"/></td>
                <td><c:out value="${r.modCorrectAnswer}"/></td>
                <td class="${r.modCorrect ? 'ok' : 'bad'}">${r.modCorrect ? '2 / 2' : '0 / 2'}</td>
            </tr>
            <tr>
                <td>Q3: <c:out value="${r.trig1Question}"/></td>
                <td><c:out value="${r.trig1YourAnswer}"/></td>
                <td><c:out value="${r.trig1CorrectAnswer}"/></td>
                <td class="${r.trig1Correct ? 'ok' : 'bad'}">${r.trig1Correct ? '3 / 3' : '0 / 3'}</td>
            </tr>
            <tr>
                <td>Q4: <c:out value="${r.trig2Question}"/></td>
                <td><c:out value="${r.trig2YourAnswer}"/></td>
                <td><c:out value="${r.trig2CorrectAnswer}"/></td>
                <td class="${r.trig2Correct ? 'ok' : 'bad'}">${r.trig2Correct ? '3 / 3' : '0 / 3'}</td>
            </tr>
            </tbody>
        </table>

        <div class="links">
            <a class="btn" href="${pageContext.request.contextPath}/index.jsp">Play Again</a>
            <a class="btn secondary" href="${pageContext.request.contextPath}/game?action=leaderboard">View Leaderboard</a>
        </div>
    </div>
</div>
</body>
</html>
