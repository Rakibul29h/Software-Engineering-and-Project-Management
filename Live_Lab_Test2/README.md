# RAKIBUL – MATH WHIZ

A web-based mathematical game built with **Java Servlets, JSP and JDBC (MySQL)**.
Players enter their name, answer four maths challenges (prime number, modulus and two trigonometry
questions), see their final score out of 10, and compete on a Top 10 leaderboard stored in a database.



## Project Objectives

- Develop reusable Java methods for mathematical calculations.
- Calculate every answer with real Java code (no stored answers).
- Handle requests and responses with a Java Servlet.
- Connect to a database with JDBC and `PreparedStatement`.
- Save scores and display a sorted Top 10 leaderboard.

## Technologies Used

| Area | Technology |
|------|------------|
| Language | Java 17 |
| Web layer | Jakarta Servlet 6.0, JSP, JSTL 3.0 |
| Server | Apache Tomcat 10.1.57 |
| Database | MySQL 8 via JDBC (`mysql-connector-j`) |
| Build | Maven (WAR packaging) |
| Tests | JUnit 4 |

## Software Requirements

- JDK 17
- Apache Tomcat **10.1.57** (uses `jakarta.servlet`; Tomcat 9 or older will not work with this code)
- MySQL Server 8+
- Intellij Idea

## Project Structure

```
MathWhiz/
├── pom.xml
├── database/math_whiz_db.sql
├── src/main/java/
│   ├── controller/GameServlet.java     request handling (start / game / submit / result / leaderboard)
│   ├── model/GameScore.java            one database row
│   ├── model/GameQuestions.java        the four generated questions
│   ├── model/GameResult.java           per-question outcome + total score
│   ├── service/MathService.java        isPrime, calculateMod, calculateSin/Cos/Tan, game logic
│   └── dao/ScoreDAO.java               JDBC INSERT and Top 10 SELECT
├── src/main/resources/db.properties    JDBC URL, user and password
├── src/main/webapp/
│   ├── index.jsp  game.jsp  result.jsp  leaderboard.jsp
│   ├── css/style.css
│   └── WEB-INF/web.xml
├── src/test/java/service/MathServiceTest.java
└── README.md
```

## Database Configuration

```properties
db.driver=com.mysql.cj.jdbc.Driver
db.url=jdbc:mysql://localhost:3306/math_whiz_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
db.user=root
db.password=root
```

## Database Creation SQL

```sql
CREATE DATABASE IF NOT EXISTS math_whiz_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE math_whiz_db;

CREATE TABLE IF NOT EXISTS game_scores (
    id          INT           NOT NULL AUTO_INCREMENT,
    player_name VARCHAR(100)  NOT NULL,
    score       INT           NOT NULL,
    difficulty  VARCHAR(20)   NOT NULL DEFAULT 'NORMAL',
    played_at   TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
```




## Game Rules and Scoring System

| Question | Area | Marks |
|----------|------|-------|
| Q1 | Prime number (answer 1 = prime, 0 = not prime) | 2 |
| Q2 | Modulus (integers only, divisor never 0) | 2 |
| Q3 | Trigonometry – sin / cos / tan | 3 |
| Q4 | Trigonometry – sin / cos / tan | 3 |
| **Total** | | **10** |

- Numbers less than 2 are not prime.
- Trigonometric angles are in **degrees** and converted to radians before `Math.sin/cos/tan`.
- Decimal answers are accepted within a tolerance of **±0.01**.
- Undefined tangent values (e.g. 90°) are never asked; `calculateTan` returns `NaN` for them.
- Difficulty is **NORMAL** and is stored in the database with every score.
- Every correct answer is calculated by `MathService` – nothing is hard-coded.

```java
int totalScore = 0;
if (primeCorrect) totalScore += 2;
if (modCorrect)   totalScore += 2;
if (trig1Correct) totalScore += 3;
if (trig2Correct) totalScore += 3;
```

### Leaderboard query

```sql
SELECT id, player_name, score, difficulty, played_at
FROM game_scores
ORDER BY score DESC, played_at ASC
LIMIT 10;
```

## Testing

`mvn test` runs `MathServiceTest`, which covers the lab test cases:

| Test case | Expected |
|-----------|----------|
| Prime 7 / 8 / 1 | 1 / 0 / 0 |
| 17 % 5 | 2 |
| sin 30° | 0.5 |
| cos 60° | 0.5 |
| tan 45° | 1.0 |
| Divisor 0 | Validation error |
| All answers correct | 10 marks |
| Incorrect answers | Matching marks not awarded |

## Application Screenshots

### Welcome Page
<img width="1209" height="712" alt="image" src="https://github.com/user-attachments/assets/e9da9409-89d9-488e-8dbe-faea7be72fa0" />


### Game Page
<img width="1035" height="894" alt="{CA06A1AC-7137-4E03-B9DE-C22E4B1F5C53}" src="https://github.com/user-attachments/assets/6fc18a2e-1192-4644-90c5-5b47b16008cb" />


### Result Page
<img width="1100" height="879" alt="{66C15FE9-7A41-46E2-9DEA-2033DD3021F4}" src="https://github.com/user-attachments/assets/3d1b74c0-b5c5-4a35-a8f8-718463ab792f" />


### Leaderboard
<img width="1179" height="842" alt="{61EBABD0-8508-4D34-A756-EEA9F5E06C2B}" src="https://github.com/user-attachments/assets/5947d070-aaf8-41f7-af25-3874f9a74f49" />


## GitHub Project Directory Link

```
https://github.com/Rakibul29h/Software-Engineering-and-Project-Management/edit/main/Live_Lab_Test2
```
