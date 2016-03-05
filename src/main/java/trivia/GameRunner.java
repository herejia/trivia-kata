package trivia;

import trivia.flow.TurnCycler;

import java.io.PrintStream;
import java.util.Random;


public class GameRunner {

    public static final int SEED = 42;
    private final PrintStream printStream;

    public GameRunner(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void run() {
        TurnCycler turnCycler = new TurnCycler();
        Game aGame = new Game(printStream, turnCycler);

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        Random randomizer = new Random(SEED);

        Roll roll = new Roll(randomizer);
        turnCycler.untilAPlayerWins(() -> {
            roll.roll();
            aGame.roll(roll);

            boolean isAnswerCorrect = randomizer.nextInt(9) != 7;
            if (isAnswerCorrect) {
                aGame.wasCorrectlyAnswered(roll);
            } else {
                aGame.wrongAnswer();
            }
            aGame.turnIsOver();
        });
    }
}
