package trivia.player;

public class PlayerCount {
    private final int count;

    public PlayerCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return String.valueOf(count);
    }

    @Deprecated
    public int intValue() {
        return count;
    }
}
