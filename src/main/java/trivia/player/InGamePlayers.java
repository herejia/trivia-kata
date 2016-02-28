package trivia.player;

public class InGamePlayers {
    private final Players players;

    public InGamePlayers() {
        this.players = new Players();
    }

    public void add(Player player) {
        players.add(player);
    }

    public PlayerCount count() {
        return new PlayerCount(players.size());
    }

    public Player getPlayerByIndex(int index) {
        return players.get(index);
    }

    Player getLastPlayer() {
        int lastIndex = players.size() - 1;
        return players.get(lastIndex);
    }
}
