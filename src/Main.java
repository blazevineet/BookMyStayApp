/**
 * BookMyStayApp - Hotel Booking Application
 * UC6: Room allocation with double-booking prevention.
 *
 * @author blazevineet
 * @version 1.0
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

class Reservation {
    String guestName;
    String roomType;
    int nights;

    Reservation(String guestName, String roomType, int nights) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 3);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    void displayInventory() {
        System.out.println("\n===== Current Inventory =====");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + ": " + inventory.get(roomType) + " available");
        }
    }
}

class BookingService {
    private RoomInventory inventory;
    private HashMap<String, Set<String>> allocatedRooms;
    private HashMap<String, Integer> roomCounter;

    BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRooms = new HashMap<>();
        roomCounter = new HashMap<>();
    }

    void processRequest(Reservation reservation) {
        String roomType = reservation.roomType;

        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("FAILED: No availability for " + roomType + " -> Guest: " + reservation.guestName);
            return;
        }

        roomCounter.put(roomType, roomCounter.getOrDefault(roomType, 0) + 1);
        String roomId = roomType.replace(" ", "_").toUpperCase() + "_" + roomCounter.get(roomType);

        allocatedRooms.putIfAbsent(roomType, new HashSet<>());

        if (allocatedRooms.get(roomType).contains(roomId)) {
            System.out.println("ERROR: Duplicate room ID detected -> " + roomId);
            return;
        }

        allocatedRooms.get(roomType).add(roomId);
        inventory.decrement(roomType);

        System.out.println("CONFIRMED: " + reservation.guestName + " -> " + roomType + " | Room ID: " + roomId + " | Nights: " + reservation.nights);
    }

    void displayAllocatedRooms() {
        System.out.println("\n===== Allocated Rooms =====");
        for (String roomType : allocatedRooms.keySet()) {
            System.out.println(roomType + ": " + allocatedRooms.get(roomType));
        }
    }
}

public class Main {

    /**
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        Queue<Reservation> queue = new LinkedList<>();
        queue.add(new Reservation("Alice", "Single Room", 2));
        queue.add(new Reservation("Bob", "Suite Room", 3));
        queue.add(new Reservation("Charlie", "Double Room", 1));
        queue.add(new Reservation("Diana", "Single Room", 4));
        queue.add(new Reservation("Eve", "Suite Room", 2));

        System.out.println("===== Processing Booking Requests =====\n");

        while (!queue.isEmpty()) {
            bookingService.processRequest(queue.poll());
        }

        bookingService.displayAllocatedRooms();
        inventory.displayInventory();

    }
}