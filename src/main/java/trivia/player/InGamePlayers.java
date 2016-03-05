package trivia.player;

import trivia.announcement.AnnouncePrinter;

public class InGamePlayers {
    private final Players players;
    private final PlayerFactory playerFactory;
    private final PlayersAnnouncer playersAnnouncer;
    private final AnnouncePrinter announcePrinter;

    public InGamePlayers(AnnouncePrinter announcePrinter) {
        this.announcePrinter = announcePrinter;
        players = new Players();
        playerFactory = new PlayerFactory();
        playersAnnouncer = new PlayersAnnouncer(this);
    }

    public void newPlayer(String playerName) {
        Player player = playerFactory.create(playerName);
        players.add(player);
        playersAnnouncer.announceLastAddedPlayer(announcePrinter);
        playersAnnouncer.announcePlayerCount(announcePrinter);
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
