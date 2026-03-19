/**
 * BookMyStayApp - Hotel Booking Application
 * UC2: Object modeling through inheritance and abstraction.
 *
 * @author blazevineet
 * @version 1.0
 */

abstract class Room {
    String roomType;
    int numberOfBeds;
    double size;
    double price;

    Room(String roomType, int numberOfBeds, double size, double price) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }

    void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Number of Beds: " + numberOfBeds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price: $" + price + " per night");
    }
}

class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 1, 200.0, 100.0);
    }
}

class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 2, 350.0, 180.0);
    }
}

class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 3, 600.0, 350.0);
    }
}

public class Main {

    /**
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        Room single = new SingleRoom();
        Room double1 = new DoubleRoom();
        Room suite = new SuiteRoom();

        boolean singleAvailable = true;
        boolean doubleAvailable = false;
        boolean suiteAvailable = true;

        System.out.println("===== Hotel Room Details =====");

        System.out.println("\n--- Single Room ---");
        single.displayDetails();
        System.out.println("Available: " + singleAvailable);

        System.out.println("\n--- Double Room ---");
        double1.displayDetails();
        System.out.println("Available: " + doubleAvailable);

        System.out.println("\n--- Suite Room ---");
        suite.displayDetails();
        System.out.println("Available: " + suiteAvailable);

    }
}