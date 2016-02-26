package trivia;

import java.io.OutputStream;
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

        Random rand = new Random(SEED);

        do {

            aGame.roll(rand.nextInt(5) + 1);

            if (rand.nextInt(9) == 7) {
                notAWinner = aGame.wrongAnswer();
            } else {
                notAWinner = aGame.wasCorrectlyAnswered();
            }


        } while (notAWinner);

    }
}
