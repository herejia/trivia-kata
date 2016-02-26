package trivia.player;

import trivia.announcement.AnnouncePrinter;
import trivia.announcement.Message;
import trivia.announcement.MessageFactory;

public class PlayersAnnouncer {
    private final Players players;

    public PlayersAnnouncer(Players players) {
        this.players = players;
    }

    public void announcePlayerCount(AnnouncePrinter announcePrinter) {
        Message message = MessageFactory.createPlayerCount(players.count());
        announcePrinter.print(message);
    }

    public void announceLastAddedPlayer(AnnouncePrinter announcePrinter) {
        Message message = MessageFactory.createPlayerAdded(players.getLastPlayer());
        announcePrinter.print(message);
    }
}
