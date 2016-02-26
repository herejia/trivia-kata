package trivia.player;

import trivia.announcement.AnnouncePrinter;

import java.util.ArrayList;
import java.util.List;

public class Players {
    private final List<Player> players;

    public Players() {
        this.players = new ArrayList<>();
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
