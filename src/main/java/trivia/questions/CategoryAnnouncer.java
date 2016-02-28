package trivia.questions;

import trivia.announcement.AnnouncePrinter;
import trivia.announcement.Message;
import trivia.announcement.MessageFactory;

public class CategoryAnnouncer {

    private Category category;

    public CategoryAnnouncer(Category category) {
        this.category = category;
    }

    public void announceCategory(AnnouncePrinter announcePrinter) {
        Message message = MessageFactory.createCategory(category);
        announcePrinter.print(message);
    }
}
