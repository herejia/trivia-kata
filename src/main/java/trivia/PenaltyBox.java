package trivia;

import trivia.player.Player;
import trivia.player.Players;

final class PenaltyBox {
    private final Players players;

    PenaltyBox() {
        players = new Players();
    }

    boolean detains(Player player) {
        return players.contains(player);
    }

    void detain(Player currentPlayer) {
        players.add(currentPlayer);
    }
}
