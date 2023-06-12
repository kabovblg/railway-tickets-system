package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RailwayTicketerTest {
    @Test
    @DisplayName(
            "The calculated discount price should equal to 50, " +
                    "with a family card and a child")
    void calculatedDiscountPriceShouldBe50() {
        RailwayTicketer railwayTicketer = new RailwayTicketer();
        railwayTicketer.setAvailableCard(CardType.FAMILY_CARD);
        railwayTicketer.setTwoWayTicket(false);
        railwayTicketer.setTravelingWithChild(true);

        Assertions.assertEquals(50, railwayTicketer.calculateTwoWayTickets(100));
    }

    @Test
    @DisplayName(
            "The calculated discount price should equal to 66, " +
                    "with an older than sixty card")
    void calculatedDiscountPriceShouldBe66() {
        RailwayTicketer railwayTicketer = new RailwayTicketer();
        railwayTicketer.setAvailableCard(CardType.OLDER_THAN_SIXTY_CARD);
        railwayTicketer.setTwoWayTicket(false);
        railwayTicketer.setTravelingWithChild(true);

        Assertions.assertEquals(66, railwayTicketer.calculateTwoWayTickets(100));
    }

    @Test
    @DisplayName(
            "The calculated discount price should equal to 90, " +
                    "with a family card without a child")
    void calculatedDiscountPriceShouldBe90() {
        RailwayTicketer railwayTicketer = new RailwayTicketer();
        railwayTicketer.setAvailableCard(CardType.FAMILY_CARD);
        railwayTicketer.setTwoWayTicket(false);
        railwayTicketer.setTravelingWithChild(false);

        Assertions.assertEquals(90, railwayTicketer.calculateTwoWayTickets(100));
    }


    private RailwayTicketer setUpHelperMethod(boolean twoWayTicket, boolean travelWithChild, boolean rushHour){
        RailwayTicketer railwayTicketer = new RailwayTicketer();
        railwayTicketer.setAvailableCard(CardType.NOT_SET);
        railwayTicketer.setTwoWayTicket(twoWayTicket);
        railwayTicketer.setTravelingWithChild(travelWithChild);
        railwayTicketer.setRushHour(rushHour);
        return railwayTicketer;
    }
    @Test
    @DisplayName(
            "The calculated discount price should equal to 95, " +
                    "without a card rush hour discount only")
    void calculatedDiscountPriceShouldBe95() {
        Assertions.assertEquals(95, setUpHelperMethod(false, true, false).calculateTwoWayTickets(100));
    }

    @Test
    @DisplayName(
            "The calculated discount price should equal to 100, " +
                    "without a card and no rush hour discount")
    void calculatedDiscountPriceShouldBe100() {
        Assertions.assertEquals(100, setUpHelperMethod(false, false, true).calculateTwoWayTickets(100));
    }

    @Test
    @DisplayName(
            "The calculated discount price should equal to 200, " +
                    "without a card and no rush hour discount, but two way ticket")
    void calculatedDiscountPriceShouldBe200() {
        Assertions.assertEquals(200, setUpHelperMethod(true, false, true).calculateTwoWayTickets(100));
    }

}
