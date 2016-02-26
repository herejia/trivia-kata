package trivia.announcement;

@FunctionalInterface
public interface AnnouncePrinter {
    void print(Message message);
}
