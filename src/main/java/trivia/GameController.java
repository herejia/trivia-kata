package trivia;

import trivia.player.Player;

interface GameController {
    void freePlayerMoves(Player byPlayer, Roll roll);

    void playerLeavesPenaltyBox(Player detainedPlayer, Roll roll);

    void playerStaysInPenaltyBox(Player detainedPlayer);
}
