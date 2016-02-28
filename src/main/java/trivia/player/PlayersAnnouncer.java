package trivia.player;

import trivia.announcement.AnnouncePrinter;
import trivia.announcement.Message;
import trivia.announcement.MessageFactory;

public class PlayersAnnouncer {
    private final InGamePlayers inGamePlayers;

    public PlayersAnnouncer(InGamePlayers inGamePlayers) {
        this.inGamePlayers = inGamePlayers;
    }

    public void announcePlayerCount(AnnouncePrinter announcePrinter) {
        Message message = MessageFactory.createPlayerCount(inGamePlayers.count());
        announcePrinter.print(message);
    }

    public void announceLastAddedPlayer(AnnouncePrinter announcePrinter) {
        Message message = MessageFactory.createPlayerAdded(inGamePlayers.getLastPlayer());
        announcePrinter.print(message);
    }
}
