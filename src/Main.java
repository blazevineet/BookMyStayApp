/**
 * BookMyStayApp - Hotel Booking Application
 * UC4: Guest room search with read-only access.
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
        inventory.put("Double Room", 0);
        inventory.put("Suite Room", 2);
    }

    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    HashMap<String, Integer> getInventory() {
        return inventory;
    }
}

class SearchService {
    private RoomInventory inventory;
    private HashMap<String, Room> roomCatalog;

    SearchService(RoomInventory inventory) {
        this.inventory = inventory;
        roomCatalog = new HashMap<>();
        roomCatalog.put("Single Room", new SingleRoom());
        roomCatalog.put("Double Room", new DoubleRoom());
        roomCatalog.put("Suite Room", new SuiteRoom());
    }

    void searchAvailableRooms() {
        System.out.println("===== Available Rooms =====");
        boolean anyAvailable = false;

        for (String roomType : inventory.getInventory().keySet()) {
            int availability = inventory.getAvailability(roomType);
            if (availability > 0) {
                anyAvailable = true;
                System.out.println("\n--- " + roomType + " ---");
                roomCatalog.get(roomType).displayDetails();
                System.out.println("Rooms Available: " + availability);
            }
        }

        if (!anyAvailable) {
            System.out.println("No rooms are currently available.");
        }
    }
}

public class Main {

    /**
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        SearchService searchService = new SearchService(inventory);

        System.out.println("Guest initiating room search...\n");
        searchService.searchAvailableRooms();

        System.out.println("\n===== Inventory Unchanged After Search =====");
        System.out.println("Single Room: " + inventory.getAvailability("Single Room"));
        System.out.println("Double Room: " + inventory.getAvailability("Double Room"));
        System.out.println("Suite Room: " + inventory.getAvailability("Suite Room"));

    }
}