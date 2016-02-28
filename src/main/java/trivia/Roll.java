package trivia;

import java.util.Random;

final class Roll {

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
}
