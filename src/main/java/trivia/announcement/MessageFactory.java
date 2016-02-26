package trivia.announcement;

import trivia.player.Player;
import trivia.player.PlayerCount;

public class MessageFactory {
    public static Message createPlayerAdded(Player player) {
        String text = player.getName() + " was added";
        return createForText(text);
    }

    public static Message createPlayerCount(PlayerCount count) {
        String text = "They are player number " + count;
        return createForText(text);
    }

    private static Message createForText(String text) {
        return new Message(text);
    }
}
