package trivia.player;

import trivia.announcement.AnnouncePrinter;
import trivia.announcement.Message;
import trivia.announcement.MessageFactory;

public class PlayerAnnouncer {
    private final Player currentPlayer;

    public PlayerAnnouncer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void announceIsGettingOutOfPenaltyBox(AnnouncePrinter announcePrinter) {
        Message message = MessageFactory.createOutOfPenaltyBox(currentPlayer);
        announcePrinter.print(message);
    }

    public void announceIsNotGettingOutOfThePenaltyBox(AnnouncePrinter announcePrinter) {
        Message message = MessageFactory.createNotOutOfPenaltyBox(currentPlayer);
        announcePrinter.print(message);
    }

    public void announcePlayerAsCurrent(AnnouncePrinter announcePrinter) {
        Message message = MessageFactory.createCurrentPlayer(currentPlayer);
        announcePrinter.print(message);
    }

    public void announceSentInPenaltyBox(AnnouncePrinter announcePrinter) {
        Message message = MessageFactory.createSentInPenaltyBox(currentPlayer);
        announcePrinter.print(message);
    }

    public void announceGoldAmount(AnnouncePrinter announcePrinter) {
        Message message = MessageFactory.createGoldAmount(currentPlayer);
        announcePrinter.print(message);
    }
}
