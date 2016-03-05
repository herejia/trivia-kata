package trivia.move;

import trivia.Game;
import trivia.Roll;

public class FreePlayerMove implements PlayerMove {
    private Game game;

    public FreePlayerMove(Game game) {
        this.game = game;
    }

    @Override
    public void move(Roll roll) {
        game.dicesHaveBeenRolled(roll);
    }
}
