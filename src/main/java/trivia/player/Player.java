package trivia.player;

import trivia.gold.GoldAmount;
import trivia.gold.GoldFactory;
import trivia.place.Place;
import trivia.place.PlaceFactory;

public class Player {
    private final String name;
    private Place place;
    private PlaceFactory placeFactory;
    private GoldAmount goldAmount;
    private GoldFactory goldFactory;

    Player(String name) {
        this.name = name;
        this.placeFactory = new PlaceFactory();
        this.goldFactory = new GoldFactory();
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

    public void setGoldAmount(GoldAmount goldAmount) {
        this.goldAmount = goldAmount;
    }

    public void incrementGoldAmount() {
        GoldAmount newAmount = this.goldAmount.plus(1);
        this.goldAmount = newAmount;
    }

    public boolean hasWinningGoldAmount() {
        GoldAmount winningGoldAmount = goldFactory.createWinningGoldAmount();
        return goldAmount.equals(winningGoldAmount);
    }

    public GoldAmount getGoldAmount() {
        return goldAmount;
    }
}
