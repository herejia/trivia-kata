package trivia;

import java.io.PrintStream;
import java.util.Random;


public class GameRunner {

    public static final int SEED = 42;
    private static boolean notAWinner;
    private final PrintStream printStream;

    public GameRunner(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void run() {
        Game aGame = new Game(printStream);

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        Random randomizer = new Random(SEED);

        do {

            Roll roll1 = new Roll(randomizer);
            roll1.roll();
            aGame.roll(roll1);

            boolean isAnswerCorrect = randomizer.nextInt(9) != 7;
            if (isAnswerCorrect) {
                notAWinner = aGame.wasCorrectlyAnswered(roll1);
            } else {
                notAWinner = aGame.wrongAnswer();
            }


        } while (notAWinner);

    }
}
