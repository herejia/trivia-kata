package trivia.player;

public class PlayerFactory {
    public Player create(String playerName) {
        return new Player(playerName);
    }
}
