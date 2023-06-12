package org.example;

import java.util.Scanner;

public class RailwayTicketer {
    private boolean rushHour = false;
    private CardType cardType = CardType.NOT_SET;
    private boolean travelingWithChild = false;
    private static final double INIT_PRICE = 70.0;
    private boolean twoWayTicket = false;


    public void setRushHour(boolean isRushHour) {
        this.rushHour = isRushHour;
    }

    public boolean getRushHour() {
        return rushHour;
    }

    public void setAvailableCard(CardType type) {
        cardType = type;
    }

    public CardType getAvailableCard() {
        return this.cardType;
    }

    public boolean getTravelingWithChild() {
        return travelingWithChild;
    }

    public void setTravelingWithChild(boolean travelingWithChild) {
        this.travelingWithChild = travelingWithChild;
    }

    public void setTwoWayTicket(boolean twoWayTicket) {
        this.twoWayTicket = twoWayTicket;
    }

    public boolean getTwoWayTicket() {
        return this.twoWayTicket;
    }

    public double calculateDiscountedPrice(double initialPriceBasedOnDistance) {
        // get the registered card type
        CardType card = getAvailableCard();
        double discountedPrice = initialPriceBasedOnDistance;
        double discountPercentage = 0;

        // rush hour check
        if (!this.getRushHour()) {
            // discount 5 percent
            discountPercentage = 0.05;
        }

        // family cards check
        if (CardType.FAMILY_CARD == card) {
            if (getTravelingWithChild()) {
                // discount 50 percent
                discountPercentage = 0.5;
            } else {
                // discount 10 percent
                discountPercentage = 0.1;
            }
        }
        // railway cards check
        else if (CardType.OLDER_THAN_SIXTY_CARD == card) {
            discountPercentage = 0.34;
        }

        discountedPrice = discountedPrice - (discountedPrice * discountPercentage);
        return discountedPrice;
    }

    public double calculateTwoWayTickets(double initialPriceBasedOnDistance) {
        if (this.twoWayTicket)
            return 2 * calculateDiscountedPrice(initialPriceBasedOnDistance);
        return calculateDiscountedPrice(initialPriceBasedOnDistance);
    }

    public double findAvailableTrainByPriceAndRoute() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input starting point: ");
        String startingPoint = scanner.nextLine();

        System.out.println("Input destination: ");
        String destination = scanner.nextLine();

        System.out.println("Choose time: ");
        System.out.println("1. 08:00"); // rush hour
        System.out.println("2. 13:30");
        System.out.println("3. 15:45");
        System.out.println("4. 18:30"); // rush hour
        String departureTime = scanner.nextLine();

        System.out.println("Successfully selected a ticket with details:");
        System.out.print(startingPoint + " - " + destination + " at: ");
        switch (departureTime) {
            case "1" -> {
                System.out.println("08:00");
                rushHour = true;
            }
            case "2" -> {
                System.out.println("13:30");
                rushHour = false;
            }
            case "3" -> {
                System.out.println("15:45");
                rushHour = false;
            }
            case "4" -> {
                System.out.println("18:30");
                rushHour = true;
            }
            default -> System.out.println("unknown time");
        }
        System.out.println("Initial price: " + INIT_PRICE);

        return INIT_PRICE;
    }
}