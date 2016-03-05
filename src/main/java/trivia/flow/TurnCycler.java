package trivia.flow;

import trivia.player.Player;

public class TurnCycler implements VerdictListener {

    private boolean keepCycling;

    public TurnCycler() {
        this.keepCycling = true;
    }

    @Override
    public void hasWon(Player player) {
        keepCycling = false;
    }

    public void untilAPlayerWins(TurnExecutor turnExecutor) {
        while (keepCycling) {
            turnExecutor.execute();
        }
    }

}
