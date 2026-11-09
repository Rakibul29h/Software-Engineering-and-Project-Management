package service;

import model.GameQuestions;
import model.GameResult;

import java.util.Locale;
import java.util.Random;

/**
 * All the mathematics of the game. Every correct answer is calculated here with real
 * Java code - nothing is stored or hard-coded.
 */
public class MathService {

    public static final String DIFFICULTY = "NORMAL";

    /** Allowed difference when comparing decimal (trigonometric) answers. */
    public static final double TOLERANCE = 0.01;

    public static final int PRIME_MARKS = 2;
    public static final int MOD_MARKS = 2;
    public static final int TRIG_MARKS = 3;

    private static final String[] TRIG_FUNCTIONS = {"sin", "cos", "tan"};
    private static final int[] ANGLES = {0, 30, 45, 60, 90, 120, 135, 150, 180,
            210, 225, 240, 270, 300, 315, 330, 360};

    private static final Random RANDOM = new Random();

    // ------------------------------------------------------------------
    // TASK 03 - Prime number
    // ------------------------------------------------------------------

    /** Returns 1 if number is prime, otherwise 0. Numbers less than 2 are not prime. */
    public static int isPrime(int number) {
        if (number < 2) {
            return 0;
        }
        if (number == 2) {
            return 1;
        }
        if (number % 2 == 0) {
            return 0;
        }
        for (int i = 3; (long) i * i <= number; i += 2) {
            if (number % i == 0) {
                return 0;
            }
        }
        return 1;
    }

    // ------------------------------------------------------------------
    // TASK 04 - Modulus
    // ------------------------------------------------------------------

    /**
     * Returns a % b.
     *
     * @throws IllegalArgumentException if the divisor is zero
     */
    public static int calculateMod(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Divisor must not be zero.");
        }
        return a % b;
    }

    // ------------------------------------------------------------------
    // TASK 05 - Trigonometry (angles are given in degrees)
    // ------------------------------------------------------------------

    public static double calculateSin(double degree) {
        return Math.sin(Math.toRadians(degree));
    }

    public static double calculateCos(double degree) {
        return Math.cos(Math.toRadians(degree));
    }

    /**
     * Returns tan(degree). For angles where tangent is undefined (90, 270, ...)
     * the method returns Double.NaN instead of a huge meaningless number.
     */
    public static double calculateTan(double degree) {
        double radians = Math.toRadians(degree);
        if (Math.abs(Math.cos(radians)) < 1e-10) {
            return Double.NaN;
        }
        return Math.tan(radians);
    }

    /** Calls calculateSin / calculateCos / calculateTan by name ("sin", "cos", "tan"). */
    public static double calculateTrig(String function, double degree) {
        switch (function.toLowerCase(Locale.ROOT)) {
            case "sin":
                return calculateSin(degree);
            case "cos":
                return calculateCos(degree);
            case "tan":
                return calculateTan(degree);
            default:
                throw new IllegalArgumentException("Unknown trigonometric function: " + function);
        }
    }

    /** True when the player's decimal answer is within TOLERANCE of the expected value. */
    public static boolean isCloseEnough(double expected, double actual) {
        if (Double.isNaN(expected) || Double.isNaN(actual)) {
            return false;
        }
        return Math.abs(expected - actual) <= TOLERANCE;
    }

    // ------------------------------------------------------------------
    // TASK 06 - Game logic
    // ------------------------------------------------------------------

    /** Generates one prime, one modulus and two trigonometric questions. */
    public static GameQuestions generateQuestions() {
        // Q1: roughly half of the time pick a real prime so both answers occur
        int primeNumber = RANDOM.nextBoolean() ? randomPrime() : 2 + RANDOM.nextInt(99);

        // Q2: integer operands, divisor is never zero
        int modA = 10 + RANDOM.nextInt(90);   // 10..99
        int modB = 2 + RANDOM.nextInt(11);    // 2..12

        // Q3 and Q4: two different trig questions with a defined answer
        String f1, f2;
        int a1, a2;
        do {
            f1 = TRIG_FUNCTIONS[RANDOM.nextInt(TRIG_FUNCTIONS.length)];
            a1 = ANGLES[RANDOM.nextInt(ANGLES.length)];
        } while (Double.isNaN(calculateTrig(f1, a1)));
        do {
            f2 = TRIG_FUNCTIONS[RANDOM.nextInt(TRIG_FUNCTIONS.length)];
            a2 = ANGLES[RANDOM.nextInt(ANGLES.length)];
        } while (Double.isNaN(calculateTrig(f2, a2)) || (f2.equals(f1) && a2 == a1));

        return new GameQuestions(primeNumber, modA, modB, f1, a1, f2, a2);
    }

    private static int randomPrime() {
        int n;
        do {
            n = 2 + RANDOM.nextInt(99);   // 2..100
        } while (isPrime(n) == 0);
        return n;
    }

    /** Evaluates the four answers with the Java methods above and calculates the score. */
    public static GameResult evaluate(String playerName, GameQuestions q,
                                      int primeAnswer, int modAnswer,
                                      double trig1Answer, double trig2Answer) {
        int expectedPrime = isPrime(q.getPrimeNumber());
        int expectedMod = calculateMod(q.getModA(), q.getModB());
        double expectedTrig1 = calculateTrig(q.getTrig1Function(), q.getTrig1Angle());
        double expectedTrig2 = calculateTrig(q.getTrig2Function(), q.getTrig2Angle());

        boolean primeCorrect = primeAnswer == expectedPrime;
        boolean modCorrect = modAnswer == expectedMod;
        boolean trig1Correct = isCloseEnough(expectedTrig1, trig1Answer);
        boolean trig2Correct = isCloseEnough(expectedTrig2, trig2Answer);

        int totalScore = 0;
        if (primeCorrect) totalScore += PRIME_MARKS;
        if (modCorrect)   totalScore += MOD_MARKS;
        if (trig1Correct) totalScore += TRIG_MARKS;
        if (trig2Correct) totalScore += TRIG_MARKS;

        GameResult r = new GameResult();
        r.setPlayerName(playerName);
        r.setScore(totalScore);
        r.setDifficulty(DIFFICULTY);

        r.setPrimeCorrect(primeCorrect);
        r.setModCorrect(modCorrect);
        r.setTrig1Correct(trig1Correct);
        r.setTrig2Correct(trig2Correct);

        r.setPrimeQuestion("Is " + q.getPrimeNumber() + " prime?");
        r.setPrimeYourAnswer(String.valueOf(primeAnswer));
        r.setPrimeCorrectAnswer(String.valueOf(expectedPrime));

        r.setModQuestion(q.getModA() + " % " + q.getModB());
        r.setModYourAnswer(String.valueOf(modAnswer));
        r.setModCorrectAnswer(String.valueOf(expectedMod));

        r.setTrig1Question(trigText(q.getTrig1Function(), q.getTrig1Angle()));
        r.setTrig1YourAnswer(format(trig1Answer));
        r.setTrig1CorrectAnswer(format(expectedTrig1));

        r.setTrig2Question(trigText(q.getTrig2Function(), q.getTrig2Angle()));
        r.setTrig2YourAnswer(format(trig2Answer));
        r.setTrig2CorrectAnswer(format(expectedTrig2));
        return r;
    }

    /** Text such as "sin(30°)". */
    public static String trigText(String function, int angle) {
        return function + "(" + angle + "°)";
    }

    /** Four decimals, with "-0.0000" cleaned up to "0.0000". */
    public static String format(double value) {
        String s = String.format(Locale.ROOT, "%.4f", value);
        return s.equals("-0.0000") ? "0.0000" : s;
    }
}
