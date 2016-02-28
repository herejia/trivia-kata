package trivia.player;

import trivia.place.Place;
import trivia.place.PlaceFactory;

public class Player {
    private final String name;
    private Place place;
    private PlaceFactory placeFactory;

    Player(String name) {
        this.name = name;
        this.placeFactory = new PlaceFactory();
    }

    public String getName() {
        return name;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void move(int steps) {
        this.place = placeFactory.create(place, steps);
    }
}
