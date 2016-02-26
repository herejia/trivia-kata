package trivia.announcement;

import java.io.PrintStream;

public class OutputStreamAnnouncePrinter implements AnnouncePrinter {
    private final PrintStream printStream;

    public OutputStreamAnnouncePrinter(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void print(Message message) {
        printStream.println(message);
    }
}
