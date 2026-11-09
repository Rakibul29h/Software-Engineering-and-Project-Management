package dao;

import model.GameScore;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/** JDBC access to the game_scores table (TASK 07 and TASK 08). */
public class ScoreDAO {

    private static final String INSERT_SQL =
            "INSERT INTO game_scores (player_name, score, difficulty, played_at) VALUES (?, ?, ?, ?)";

    private static final String TOP10_SQL =
            "SELECT id, player_name, score, difficulty, played_at "
                    + "FROM game_scores "
                    + "ORDER BY score DESC, played_at ASC "
                    + "LIMIT 10";

    private static final Properties DB = new Properties();

    static {
        try (InputStream in = ScoreDAO.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new IllegalStateException("db.properties not found on the classpath");
            }
            DB.load(in);
            Class.forName(DB.getProperty("db.driver"));
        } catch (IOException | ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /** Opens a new JDBC connection. */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DB.getProperty("db.url"),
                DB.getProperty("db.user"),
                DB.getProperty("db.password"));
    }

    /** Saves player name, score, difficulty and date/time using a PreparedStatement. */
    public void save(GameScore gs) throws SQLException {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {
            ps.setString(1, gs.getPlayerName());
            ps.setInt(2, gs.getScore());
            ps.setString(3, gs.getDifficulty());
            ps.setTimestamp(4, gs.getPlayedAt() != null
                    ? gs.getPlayedAt()
                    : new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();
        }
    }

    /** Returns the 10 highest scores; for equal scores the earlier game ranks higher. */
    public List<GameScore> findTop10() throws SQLException {
        List<GameScore> list = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(TOP10_SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                GameScore gs = new GameScore();
                gs.setId(rs.getInt("id"));
                gs.setPlayerName(rs.getString("player_name"));
                gs.setScore(rs.getInt("score"));
                gs.setDifficulty(rs.getString("difficulty"));
                gs.setPlayedAt(rs.getTimestamp("played_at"));
                list.add(gs);
            }
        }
        return list;
    }
}
