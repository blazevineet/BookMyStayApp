/**
 * BookMyStayApp - Hotel Booking Application
 * UC3: Centralized inventory management using HashMap.
 *
 * @author blazevineet
 * @version 1.0
 */

import java.util.HashMap;

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

class RoomInventory {
    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    void displayInventory() {
        System.out.println("===== Room Inventory =====");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + ": " + inventory.get(roomType) + " rooms available");
        }
    }
}

public class Main {

    /**
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        System.out.println("===== Initial Inventory =====");
        inventory.displayInventory();

        System.out.println("\n===== Updating Suite Room to 4 =====");
        inventory.updateAvailability("Suite Room", 4);

        System.out.println("\n===== Updated Inventory =====");
        inventory.displayInventory();

        System.out.println("\n===== Availability Check =====");
        System.out.println("Single Room available: " + inventory.getAvailability("Single Room"));
        System.out.println("Double Room available: " + inventory.getAvailability("Double Room"));
        System.out.println("Suite Room available: " + inventory.getAvailability("Suite Room"));

    }
}