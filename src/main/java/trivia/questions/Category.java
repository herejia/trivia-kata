package trivia.questions;

import trivia.announcement.AnnouncePrinter;

public class Category {
    private final String categoryName;
    private final CategoryAnnouncer categoryAnnouncer;

    Category(String categoryName) {
        this.categoryName = categoryName;
        categoryAnnouncer = new CategoryAnnouncer(this);
    }

    public void announceCategory(AnnouncePrinter announcePrinter) {
        categoryAnnouncer.announceCategory(announcePrinter);
    }

    @Override
    public String toString() {
        return categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return categoryName != null ? categoryName.equals(category.categoryName) : category.categoryName == null;

    }

    @Override
    public int hashCode() {
        return categoryName != null ? categoryName.hashCode() : 0;
    }
}
