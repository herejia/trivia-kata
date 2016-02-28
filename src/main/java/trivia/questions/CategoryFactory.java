package trivia.questions;

import trivia.place.Place;

public class CategoryFactory {

    public Category createFrom(Place place) {
        switch (place.intValue()) {
            case 0:
            case 4:
            case 8:
                return createPop();
            case 1:
            case 5:
            case 9:
                return createScience();
            case 2:
            case 6:
            case 10:
                return createSports();
            default:
                return createRock();
        }
    }

    public static Category createPop() {
        return new Category("Pop");
    }

    public static Category createScience() {
        return new Category("Science");
    }

    public static Category createSports() {
        return new Category("Sports");
    }

    public static Category createRock() {
        return new Category("Rock");
    }
}
