package trivia.place;

public class PlaceFactory {
    public Place createStartingPlace() {
        return new Place(0);
    }

    public Place create(Place start, int steps) {
        Integer number = start.getPlaceNumber(steps);
        return new Place(number);
    }
}
