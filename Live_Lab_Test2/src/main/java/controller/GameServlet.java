package controller;

import dao.ScoreDAO;
import model.GameQuestions;
import model.GameResult;
import model.GameScore;
import service.MathService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Front controller of the game.
 *
 *   POST /game  action=start        -> validate name, create 4 questions
 *   GET  /game  action=game         -> show the four questions
 *   POST /game  action=submit       -> validate answers, evaluate, save score
 *   GET  /game  action=result       -> show the final score
 *   GET  /game  action=leaderboard  -> show the top 10 scores
 */
@WebServlet(name = "GameServlet", urlPatterns = "/game")
public class GameServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String SESSION_NAME = "playerName";
    private static final String SESSION_QUESTIONS = "questions";
    private static final String SESSION_RESULT = "result";

    private final ScoreDAO scoreDAO = new ScoreDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "game":
                showGame(req, resp);
                break;
            case "result":
                showResult(req, resp);
                break;
            case "leaderboard":
                showLeaderboard(req, resp);
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if ("start".equals(action)) {
            startGame(req, resp);
        } else if ("submit".equals(action)) {
            submitAnswers(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    // ------------------------------------------------------------------
    // Welcome page -> Start Game
    // ------------------------------------------------------------------
    private void startGame(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("playerName");
        name = name == null ? "" : name.trim();

        if (name.isEmpty()) {
            req.setAttribute("error", "Please enter your name to start the game.");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }
        if (name.length() > 100) {
            req.setAttribute("error", "Name must be 100 characters or fewer.");
            req.setAttribute("enteredName", name.substring(0, 100));
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute(SESSION_NAME, name);
        session.setAttribute(SESSION_QUESTIONS, MathService.generateQuestions());
        session.removeAttribute(SESSION_RESULT);
        resp.sendRedirect(req.getContextPath() + "/game?action=game");
    }

    // ------------------------------------------------------------------
    // Game page
    // ------------------------------------------------------------------
    private void showGame(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute(SESSION_QUESTIONS) == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }
        req.getRequestDispatcher("/game.jsp").forward(req, resp);
    }

    // ------------------------------------------------------------------
    // Submit answers -> validate, evaluate with Java methods, save to DB
    // ------------------------------------------------------------------
    private void submitAnswers(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute(SESSION_QUESTIONS) == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        GameQuestions q = (GameQuestions) session.getAttribute(SESSION_QUESTIONS);
        String playerName = (String) session.getAttribute(SESSION_NAME);

        String primeText = trim(req.getParameter("primeAnswer"));
        String modText = trim(req.getParameter("modAnswer"));
        String trig1Text = trim(req.getParameter("trig1Answer"));
        String trig2Text = trim(req.getParameter("trig2Answer"));

        // ---- validation ----
        StringBuilder errors = new StringBuilder();
        Integer primeAnswer = null, modAnswer = null;
        Double trig1Answer = null, trig2Answer = null;

        try {
            primeAnswer = Integer.valueOf(primeText);
            if (primeAnswer != 0 && primeAnswer != 1) {
                errors.append("Q1: enter 1 (prime) or 0 (not prime). ");
                primeAnswer = null;
            }
        } catch (NumberFormatException e) {
            errors.append("Q1: enter 1 (prime) or 0 (not prime). ");
        }

        try {
            modAnswer = Integer.valueOf(modText);   // integers only
        } catch (NumberFormatException e) {
            errors.append("Q2: the remainder must be a whole number. ");
        }

        trig1Answer = parseDecimal(trig1Text);
        if (trig1Answer == null) {
            errors.append("Q3: enter a decimal number such as 0.5. ");
        }
        trig2Answer = parseDecimal(trig2Text);
        if (trig2Answer == null) {
            errors.append("Q4: enter a decimal number such as 0.5. ");
        }

        if (errors.length() > 0) {
            req.setAttribute("error", errors.toString().trim());
            req.setAttribute("primeAnswer", primeText);
            req.setAttribute("modAnswer", modText);
            req.setAttribute("trig1Answer", trig1Text);
            req.setAttribute("trig2Answer", trig2Text);
            req.getRequestDispatcher("/game.jsp").forward(req, resp);
            return;
        }

        // ---- evaluate using the Java math methods ----
        GameResult result;
        try {
            result = MathService.evaluate(playerName, q,
                    primeAnswer, modAnswer, trig1Answer, trig2Answer);
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", "Validation error: " + e.getMessage());
            req.getRequestDispatcher("/game.jsp").forward(req, resp);
            return;
        }

        // ---- save to the database (a DB problem must not lose the score screen) ----
        try {
            scoreDAO.save(new GameScore(playerName, result.getScore(),
                    result.getDifficulty(), new Timestamp(System.currentTimeMillis())));
            result.setSaved(true);
        } catch (SQLException e) {
            result.setSaved(false);
            result.setSaveError("Score could not be saved: " + e.getMessage());
            log("Could not save score", e);
        }

        // Post/Redirect/Get so that refreshing the result page does not re-submit
        session.setAttribute(SESSION_RESULT, result);
        session.removeAttribute(SESSION_QUESTIONS);
        resp.sendRedirect(req.getContextPath() + "/game?action=result");
    }

    // ------------------------------------------------------------------
    // Result page
    // ------------------------------------------------------------------
    private void showResult(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute(SESSION_RESULT) == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }
        req.getRequestDispatcher("/result.jsp").forward(req, resp);
    }

    // ------------------------------------------------------------------
    // Leaderboard
    // ------------------------------------------------------------------
    private void showLeaderboard(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<GameScore> top10 = scoreDAO.findTop10();
            req.setAttribute("scores", top10);
        } catch (SQLException e) {
            log("Could not load leaderboard", e);
            req.setAttribute("error", "Could not load the leaderboard: " + e.getMessage());
        }
        req.getRequestDispatcher("/leaderboard.jsp").forward(req, resp);
    }

    // ------------------------------------------------------------------
    // helpers
    // ------------------------------------------------------------------
    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }

    private static Double parseDecimal(String text) {
        try {
            double d = Double.parseDouble(text);
            return (Double.isNaN(d) || Double.isInfinite(d)) ? null : d;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
