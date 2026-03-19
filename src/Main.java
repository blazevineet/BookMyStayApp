/**
 * BookMyStayApp - Hotel Booking Application
 * UC10: Safe cancellation with rollback using Stack.
 *
 * @author blazevineet
 * @version 1.0
 */

import java.util.HashMap;
import java.util.Stack;

class Reservation {
    String reservationId;
    String guestName;
    String roomType;
    String roomId;
    int nights;
    boolean cancelled;

    Reservation(String reservationId, String guestName, String roomType, String roomId, int nights) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.nights = nights;
        this.cancelled = false;
    }

    void displayReservation() {
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Guest          : " + guestName);
        System.out.println("Room Type      : " + roomType);
        System.out.println("Room ID        : " + roomId);
        System.out.println("Nights         : " + nights);
        System.out.println("Status         : " + (cancelled ? "CANCELLED" : "CONFIRMED"));
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    void increment(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    void displayInventory() {
        System.out.println("\n===== Current Inventory =====");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + ": " + inventory.get(roomType) + " available");
        }
    }
}

class CancellationService {
    private HashMap<String, Reservation> bookingHistory;
    private RoomInventory inventory;
    private Stack<String> rollbackStack;

    CancellationService(HashMap<String, Reservation> bookingHistory, RoomInventory inventory) {
        this.bookingHistory = bookingHistory;
        this.inventory = inventory;
        this.rollbackStack = new Stack<>();
    }

    void cancelBooking(String reservationId) {
        if (!bookingHistory.containsKey(reservationId)) {
            System.out.println("FAILED: Reservation " + reservationId + " does not exist.");
            return;
        }

        Reservation reservation = bookingHistory.get(reservationId);

        if (reservation.cancelled) {
            System.out.println("FAILED: Reservation " + reservationId + " is already cancelled.");
            return;
        }

        rollbackStack.push(reservation.roomId);
        inventory.increment(reservation.roomType);
        reservation.cancelled = true;

        System.out.println("CANCELLED: " + reservationId + " -> " + reservation.guestName
                + " | Room ID released: " + rollbackStack.pop());
    }

    void displayRollbackLog() {
        System.out.println("\n===== Rollback Stack (recent releases) =====");
        if (rollbackStack.isEmpty()) {
            System.out.println("No pending rollback entries.");
        } else {
            for (String roomId : rollbackStack) {
                System.out.println("Released Room ID: " + roomId);
            }
        }
    }
}

public class Main {

    /**
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        HashMap<String, Reservation> bookingHistory = new HashMap<>();
        bookingHistory.put("RES_001", new Reservation("RES_001", "Alice", "Single Room", "SINGLE_ROOM_1", 2));
        bookingHistory.put("RES_002", new Reservation("RES_002", "Bob", "Suite Room", "SUITE_ROOM_1", 3));
        bookingHistory.put("RES_003", new Reservation("RES_003", "Charlie", "Double Room", "DOUBLE_ROOM_1", 1));

        RoomInventory inventory = new RoomInventory();
        CancellationService cancellationService = new CancellationService(bookingHistory, inventory);

        System.out.println("===== Processing Cancellation Requests =====\n");

        cancellationService.cancelBooking("RES_002");
        cancellationService.cancelBooking("RES_001");
        cancellationService.cancelBooking("RES_001");
        cancellationService.cancelBooking("RES_999");

        inventory.displayInventory();

        System.out.println("\n===== Updated Booking Status =====");
        for (Reservation r : bookingHistory.values()) {
            System.out.println("----------------------------");
            r.displayReservation();
        }

    }
}