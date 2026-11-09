<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${initParam.gameTitle}"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h1 class="title"><c:out value="${initParam.gameTitle}"/></h1>

    <div class="card">
        <h2>Welcome!</h2>
        <p>Answer four maths challenges &ndash; one prime number, one modulus and two
            trigonometry questions &ndash; and score up to <strong>10 marks</strong>.
            Difficulty: <strong>NORMAL</strong>.</p>

        <c:if test="${not empty error}">
            <div class="error"><c:out value="${error}"/></div>
        </c:if>

        <form action="${pageContext.request.contextPath}/game" method="post">
            <input type="hidden" name="action" value="start">
            <label for="playerName">Player Name</label>
            <input type="text" id="playerName" name="playerName" maxlength="100"
                   placeholder="Enter your name" value="<c:out value='${enteredName}'/>" required autofocus>
            <button type="submit" class="btn">Start Game</button>
        </form>

        <div class="links">
            <a class="btn secondary" href="${pageContext.request.contextPath}/game?action=leaderboard">View Leaderboard</a>
        </div>
    </div>
</div>
</body>
</html>
