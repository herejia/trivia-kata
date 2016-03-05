package trivia.announcement;

import trivia.player.Player;
import trivia.player.PlayerCount;
import trivia.questions.Category;

public class MessageFactory {
    private static Message createForText(String text) {
        return new Message(text);
    }

    public static Message createPlayerAdded(Player player) {
        String text = player.getName() + " was added";
        return createForText(text);
    }

    public static Message createPlayerCount(PlayerCount count) {
        String text = "They are player number " + count;
        return createForText(text);
    }

    public static Message createOutOfPenaltyBox(Player player) {
        return createForText(player.getName() + " is getting out of the penalty box");
    }

    public static Message createNotOutOfPenaltyBox(Player player) {
        return createForText(player.getName() + " is not getting out of the penalty box");
    }

    public static Message createCurrentPlayer(Player player) {
        return createForText(player.getName() + " is the current player");
    }

    public static Message createSentInPenaltyBox(Player currentPlayer) {
        return createForText(currentPlayer.getName() + " was sent to the penalty box");
    }

    public static Message createGoldAmount(Player player) {
        return createForText(player.getName() + " now has " + player.getGoldAmount() + " Gold Coins.");
    }

    public static Message createCategory(Category category) {
        return createForText("The category is " + category);
    }

    public static Message createNewPlace(Player player) {
        return createForText(player.getName() + "'s new location is " + player.getPlace().intValue());
    }
}
