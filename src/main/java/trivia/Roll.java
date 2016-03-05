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

    void detainedPlayerMoves(Player detainedPlayer, Game game, PenaltyBox penaltyBox) {
        if (isOdd()) {
            penaltyBox.detainedPlayerRolledOdd(detainedPlayer, game, this);
        } else {
            penaltyBox.detainedPlayerRolledEven(detainedPlayer, game);
        }
    }

    boolean detainedPlayerAnswersCorrectly(Game game) {
        if (isOdd()) {
            return game.detainedPlayerAnswersCorrectlyAndRolledOdd();
        } else {
            return game.detainedPlayerAnswersCorrectlyButRolledEven();
        }
    }
}
