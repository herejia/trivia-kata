package trivia;

import trivia.player.Player;

import java.util.Random;

public final class Roll {

    private final Random random;
    private int roll;

    public Roll(Random random) {
        this.random = random;
    }

    public void roll() {
        roll = random.nextInt(5) + 1;
    }

    @Override
    public String toString() {
        return String.valueOf(roll);
    }

    private boolean isOdd() {
        return roll % 2 != 0;
    }

    @Deprecated
    public int intValue() {
        return roll;
    }

    void detainedPlayerMoves(Player detainedPlayer, GameController game, PenaltyBox penaltyBox) {
        if (isOdd()) {
            penaltyBox.detainedPlayerRolledOdd(detainedPlayer, game, this);
        } else {
            penaltyBox.detainedPlayerRolledEven(detainedPlayer, game);
        }
    }

    void detainedPlayerAnswersCorrectly(Game game) {
        if (isOdd()) {
            game.detainedPlayerAnswersCorrectlyAndRolledOdd();
        } else {
            game.detainedPlayerAnswersCorrectlyButRolledEven();
        }
    }
}
