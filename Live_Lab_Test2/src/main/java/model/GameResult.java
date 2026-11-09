package model;

import java.io.Serializable;

/** Outcome of one finished game: per-question correctness, answers and the total score. */
public class GameResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private String playerName;
    private int score;
    private String difficulty;

    private boolean primeCorrect;
    private boolean modCorrect;
    private boolean trig1Correct;
    private boolean trig2Correct;

    // Text shown on the result page: what the player typed / what Java calculated
    private String primeQuestion, primeYourAnswer, primeCorrectAnswer;
    private String modQuestion, modYourAnswer, modCorrectAnswer;
    private String trig1Question, trig1YourAnswer, trig1CorrectAnswer;
    private String trig2Question, trig2YourAnswer, trig2CorrectAnswer;

    // Filled in by the servlet after the INSERT is attempted
    private boolean saved;
    private String saveError;

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public boolean isPrimeCorrect() { return primeCorrect; }
    public void setPrimeCorrect(boolean primeCorrect) { this.primeCorrect = primeCorrect; }

    public boolean isModCorrect() { return modCorrect; }
    public void setModCorrect(boolean modCorrect) { this.modCorrect = modCorrect; }

    public boolean isTrig1Correct() { return trig1Correct; }
    public void setTrig1Correct(boolean trig1Correct) { this.trig1Correct = trig1Correct; }

    public boolean isTrig2Correct() { return trig2Correct; }
    public void setTrig2Correct(boolean trig2Correct) { this.trig2Correct = trig2Correct; }

    public String getPrimeQuestion() { return primeQuestion; }
    public void setPrimeQuestion(String v) { this.primeQuestion = v; }
    public String getPrimeYourAnswer() { return primeYourAnswer; }
    public void setPrimeYourAnswer(String v) { this.primeYourAnswer = v; }
    public String getPrimeCorrectAnswer() { return primeCorrectAnswer; }
    public void setPrimeCorrectAnswer(String v) { this.primeCorrectAnswer = v; }

    public String getModQuestion() { return modQuestion; }
    public void setModQuestion(String v) { this.modQuestion = v; }
    public String getModYourAnswer() { return modYourAnswer; }
    public void setModYourAnswer(String v) { this.modYourAnswer = v; }
    public String getModCorrectAnswer() { return modCorrectAnswer; }
    public void setModCorrectAnswer(String v) { this.modCorrectAnswer = v; }

    public String getTrig1Question() { return trig1Question; }
    public void setTrig1Question(String v) { this.trig1Question = v; }
    public String getTrig1YourAnswer() { return trig1YourAnswer; }
    public void setTrig1YourAnswer(String v) { this.trig1YourAnswer = v; }
    public String getTrig1CorrectAnswer() { return trig1CorrectAnswer; }
    public void setTrig1CorrectAnswer(String v) { this.trig1CorrectAnswer = v; }

    public String getTrig2Question() { return trig2Question; }
    public void setTrig2Question(String v) { this.trig2Question = v; }
    public String getTrig2YourAnswer() { return trig2YourAnswer; }
    public void setTrig2YourAnswer(String v) { this.trig2YourAnswer = v; }
    public String getTrig2CorrectAnswer() { return trig2CorrectAnswer; }
    public void setTrig2CorrectAnswer(String v) { this.trig2CorrectAnswer = v; }

    public boolean isSaved() { return saved; }
    public void setSaved(boolean saved) { this.saved = saved; }

    public String getSaveError() { return saveError; }
    public void setSaveError(String saveError) { this.saveError = saveError; }
}
