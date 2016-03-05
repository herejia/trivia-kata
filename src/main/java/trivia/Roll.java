package trivia;

import trivia.move.OnLeavePlayerMove;

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

    public boolean isOdd() {
        return roll % 2 != 0;
    }

    @Deprecated
    public int intValue() {
        return roll;
    }

    public void detainedPlayerMoves(OnLeavePlayerMove playerMove) {
        if (isOdd()) {
            playerMove.move(this);
        } else {
            playerMove.stayIn();
        }
    }
}
