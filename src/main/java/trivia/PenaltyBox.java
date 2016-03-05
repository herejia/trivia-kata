package trivia;

import trivia.move.DetainedPlayerMove;
import trivia.move.FreePlayerMove;
import trivia.move.PlayerMove;
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

    public void playerMoves(Roll roll, Player player,
                            DetainedPlayerMove detainedPlayerMove,
                            FreePlayerMove freePlayerMove) {
        if (detains(player)) {
            detainedPlayerMove.move(roll);
        } else {
            freePlayerMove.move(roll);
        }
    }
}
