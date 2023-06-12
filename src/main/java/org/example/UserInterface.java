package org.example;

import java.util.Scanner;

public class UserInterface {
    public static void main(String[] args) {
        // SetUp
        Database database = new Database();
        UserLogic userLogic = new UserLogic(database);
        RailwayTicketer railwayTicketer = new RailwayTicketer();
        database.readFromFileOnStartUp();
        database.readUsersFromFileOnStartUp();

        Scanner scanner = new Scanner(System.in);

        RoleType role;

        // Prompt user or admin
        System.out.println("1. Admin");
        System.out.println("2. User");
        System.out.println("--------------");
        System.out.print("input: ");
        role = RoleType.values()[Integer.parseInt(scanner.nextLine())];

        // Functionality depending on the role
        if (RoleType.USER == role) {
            System.out.println();
            System.out.println("Welcome User!");
            System.out.println("-------------");

            // Prompt egn
            System.out.print("Enter your egn: ");
            String egn = scanner.nextLine();

            // Main lifecycle
            userOptions(egn, userLogic, railwayTicketer, database);
        } else if (RoleType.ADMIN == role) {
            System.out.println("Welcome Admin!");
            System.out.println("-------------");
            adminOptions(userLogic, database);
        }
    }

    private static void adminOptions(
            UserLogic userLogic,
            Database database) {
        boolean running = true;
        String userInput;
        Scanner scanner = new Scanner(System.in);

        while (running) {
            printAdminMenu();
            userInput = scanner.nextLine();

            switch (userInput) {
                case "1":
                    // Create a user
                    User user = createUserByAdmin();
                    // Add to list of users
                    userLogic.createUser(user);
                    break;
                case "2":
                    // Update the card-type of a given user
                    System.out.print("Enter a user id: ");
                    int userId = Integer.parseInt(scanner.nextLine());

                    System.out.println("New card type");
                    printDiscountType();
                    CardType newCardType = CardType.values()[Integer.parseInt(scanner.nextLine())];

                    userLogic.updateUserCardType(userId, newCardType);
                    break;
                case "3":
                    // View all users
                    userLogic.viewAllUsers();
                    break;
                case "4":
                    database.writeUsersToFile(database.getUsers());
                    running = false;
                    break;
                default:
                    System.out.println("ERROR choosing an option");
                    break;
            }
        }
    }

    private static void userOptions(
            String egn,
            UserLogic userLogic,
            RailwayTicketer railwayTicketer,
            Database database) {
        boolean running = true;
        String userInput;
        Scanner scanner = new Scanner(System.in);
        User currentUser = userLogic.getSingleUser(egn);

        if (null == currentUser) {
            System.out.println("No user with the given egn!");
            database.writeUsersToFile(database.getUsers());
            return;
        }

        while (running) {
            printUserMenu();
            userInput = scanner.nextLine();

            switch (userInput) {
                case "1":
                    CardType cardType = currentUser.getCard();
                    railwayTicketer.setAvailableCard(cardType);

                    System.out.print("Traveling with a child (true/false): ");
                    boolean haveChild = Boolean.parseBoolean(scanner.nextLine());
                    // Patch so I can do the boundary cases
                    if (haveChild) {
                        System.out.print("Enter the age of the child: ");
                        if (Integer.parseInt(scanner.nextLine()) < 16) {
                            haveChild = false;
                        }
                    }
                    railwayTicketer.setTravelingWithChild(haveChild);

                    System.out.print("Two way ticket (true/false): ");
                    boolean twoWayTicket = Boolean.parseBoolean(scanner.nextLine());
                    railwayTicketer.setTwoWayTicket(twoWayTicket);

                    System.out.print("Person's ticket count: ");
                    int ticketCount = Integer.parseInt(scanner.nextLine());
                    double[] ticketsPrices = new double[ticketCount];
                    for (int i = 0; i < ticketCount; i++) {
                        ticketsPrices[i] = railwayTicketer.calculateTwoWayTickets(
                                railwayTicketer.findAvailableTrainByPriceAndRoute());
                    }
                    Reservation newReservation = new Reservation(cardType, ticketCount);
                    newReservation.setTicketsPrices(ticketsPrices); // should not be here
                    userLogic.addReservationToUser(currentUser.getEgn(), newReservation);
                    break;
                case "2":
                    System.out.print("Input reservation's ID to be updated: ");
                    int reservationIDToBeUpdated = Integer.parseInt(scanner.nextLine());

                    System.out.println("Input new discount type");
                    printDiscountType();
                    CardType newCardType = CardType.values()[Integer.parseInt(scanner.nextLine())];

                    railwayTicketer.setAvailableCard(newCardType); // should not be here
                    userLogic.updateReservationByID(egn, reservationIDToBeUpdated, newCardType);
                    break;
                case "3":
                    System.out.print("Input reservation's ID to be deleted: ");
                    int reservationIDToBeDeleted = Integer.parseInt(scanner.nextLine());
                    userLogic.deleteReservationFromUser(currentUser.getEgn(), reservationIDToBeDeleted);
                    break;
                case "4":
                    userLogic.viewPreviousReservationsForUser(egn);
                    break;
                case "5":
                    printDiscountType();
                    userLogic.updateUserCardType(currentUser.getUserId(),
                            CardType.values()[Integer.parseInt(scanner.nextLine())]);
                    break;
                case "6":
                    running = false;
                    // Write back to the db
                    database.writeUsersToFile(database.getUsers());
                    break;
                default:
                    System.out.println("ERROR choosing an option");
                    break;
            }
        }
    }

    private static void printUserMenu() {
        System.out.println();
        System.out.println("Select an option");
        System.out.println("----------------");
        System.out.println("1. Create a reservation");
        System.out.println("2. Update a reservation");
        System.out.println("3. Delete a reservation");
        System.out.println("4. See previous reservations");
        System.out.println("5. Update user's card type");
        System.out.println("6. Exit");
        System.out.println("-------------------");
        System.out.print("Input: ");
    }

    private static void printAdminMenu() {
        System.out.println();
        System.out.println("Select an option");
        System.out.println("----------------");
        System.out.println("1. Create a user profile");
        System.out.println("2. Update a user profile");
        System.out.println("3. View a user profile");
        System.out.println("4. Exit");
        System.out.println("-------------------");
        System.out.print("Input: ");
    }

    private static User createUserByAdmin() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("EGN: ");
        String egn = scanner.nextLine();
        printDiscountType();
        CardType cardType = CardType.values()[Integer.parseInt(scanner.nextLine())];

        return new User(name, egn, cardType);
    }

    private static void printDiscountType() {
        System.out.println("Card type: ");
        System.out.println("0. No card");
        System.out.println("1. Older than sixty card");
        System.out.println("2. Family card");
    }
}
