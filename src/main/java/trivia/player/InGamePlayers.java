package trivia.player;

import trivia.Game;
import trivia.announcement.AnnouncePrinter;

public class InGamePlayers {
    private final Players players;
    private final PlayerFactory playerFactory;
    private final PlayersAnnouncer playersAnnouncer;
    private final AnnouncePrinter announcePrinter;
    private int activePlayerIndex;
    private Game game;

    public InGamePlayers(Game game, AnnouncePrinter announcePrinter) {
        this.game = game;
        this.announcePrinter = announcePrinter;
        players = new Players();
        playerFactory = new PlayerFactory();
        playersAnnouncer = new PlayersAnnouncer(this);
        activePlayerIndex = 0;
    }

    public void newPlayer(String playerName) {
        Player player = playerFactory.create(playerName, announcePrinter);
        players.add(player);
        playersAnnouncer.announceLastAddedPlayer(announcePrinter);
        playersAnnouncer.announcePlayerCount(announcePrinter);
        game.activePlayerIs(getActivePlayer());
    }

    public void nextPlayer() {
        activePlayerIndex++;
        if (activePlayerIndex == players.size()) {
            activePlayerIndex = 0;
        }
        game.activePlayerIs(getActivePlayer());
    }

    private Player getActivePlayer() {
        return players.get(activePlayerIndex);
    }

    PlayerCount count() {
        return new PlayerCount(players.size());
    }

    Player getLastPlayer() {
        int lastIndex = players.size() - 1;
        return players.get(lastIndex);
    }
}
