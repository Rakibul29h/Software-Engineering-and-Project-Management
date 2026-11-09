<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${initParam.gameTitle}"/> - Leaderboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h1 class="title"><c:out value="${initParam.gameTitle}"/></h1>

    <div class="card">
        <h2>Top 10 Leaderboard</h2>

        <c:if test="${not empty error}">
            <div class="error"><c:out value="${error}"/></div>
        </c:if>

        <c:choose>
            <c:when test="${empty scores}">
                <c:if test="${empty error}">
                    <p>No scores yet &ndash; be the first to play!</p>
                </c:if>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                    <tr><th>#</th><th>Player</th><th>Score</th><th>Difficulty</th><th>Date / Time</th></tr>
                    </thead>
                    <tbody>
                    <c:forEach var="s" items="${scores}" varStatus="st">
                        <tr class="${st.first ? 'rank-1' : ''}">
                            <td>${st.count}</td>
                            <td><c:out value="${s.playerName}"/></td>
                            <td>${s.score} / 10</td>
                            <td>${s.difficulty}</td>
                            <td><fmt:formatDate value="${s.playedAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>

        <div class="links">
            <a class="btn" href="${pageContext.request.contextPath}/index.jsp">Play Game</a>
        </div>
    </div>
</div>
</body>
</html>
