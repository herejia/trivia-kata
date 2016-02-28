package trivia.player;

import trivia.gold.GoldAmount;
import trivia.gold.GoldFactory;
import trivia.place.Place;
import trivia.place.PlaceFactory;

public class PlayerFactory {
    private final PlaceFactory placeFactory;
    private final GoldFactory goldFactory;

    public PlayerFactory() {
        this.placeFactory = new PlaceFactory();
        this.goldFactory = new GoldFactory();
    }

    public Player create(String playerName) {
        GoldAmount startingAmount = goldFactory.create(0);
        Place startingPlace = placeFactory.createStartingPlace();
        return new Player(playerName, startingPlace, startingAmount);
    }
}
