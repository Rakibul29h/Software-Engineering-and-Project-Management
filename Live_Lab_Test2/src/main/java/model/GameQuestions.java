package model;

import java.io.Serializable;

/**
 * The four questions of one game round. Only the question data is stored here;
 * the correct answers are always calculated by MathService when the player submits.
 */
public class GameQuestions implements Serializable {
    private static final long serialVersionUID = 1L;

    // Q1 - prime number
    private final int primeNumber;

    // Q2 - modulus
    private final int modA;
    private final int modB;

    // Q3 and Q4 - trigonometry (function is "sin", "cos" or "tan")
    private final String trig1Function;
    private final int trig1Angle;
    private final String trig2Function;
    private final int trig2Angle;

    public GameQuestions(int primeNumber, int modA, int modB,
                         String trig1Function, int trig1Angle,
                         String trig2Function, int trig2Angle) {
        this.primeNumber = primeNumber;
        this.modA = modA;
        this.modB = modB;
        this.trig1Function = trig1Function;
        this.trig1Angle = trig1Angle;
        this.trig2Function = trig2Function;
        this.trig2Angle = trig2Angle;
    }

    public int getPrimeNumber() { return primeNumber; }
    public int getModA() { return modA; }
    public int getModB() { return modB; }
    public String getTrig1Function() { return trig1Function; }
    public int getTrig1Angle() { return trig1Angle; }
    public String getTrig2Function() { return trig2Function; }
    public int getTrig2Angle() { return trig2Angle; }
}
