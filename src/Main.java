/**
 * BookMyStayApp - Hotel Booking Application
 * UC12: Persistence and recovery using file-based serialization.
 *
 * @author blazevineet
 * @version 1.0
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

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

class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    HashMap<String, Integer> inventory;
    List<Reservation> bookingHistory;

    SystemState(HashMap<String, Integer> inventory, List<Reservation> bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }
}

class PersistenceService {
    private static final String FILE_PATH = "system_state.dat";

    void saveState(HashMap<String, Integer> inventory, List<Reservation> bookingHistory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            SystemState state = new SystemState(inventory, bookingHistory);
            oos.writeObject(state);
            System.out.println("System state saved successfully to " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("ERROR: Failed to save state -> " + e.getMessage());
        }
    }

    SystemState loadState() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("No saved state found. Starting fresh.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            SystemState state = (SystemState) ois.readObject();
            System.out.println("System state restored successfully from " + FILE_PATH);
            return state;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR: Failed to load state -> " + e.getMessage());
            System.out.println("Starting with default state.");
            return null;
        }
    }
}

public class Main {

    /**
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        PersistenceService persistenceService = new PersistenceService();

        // --- SIMULATE FIRST RUN ---
        System.out.println("===== FIRST RUN: Initializing System =====\n");

        HashMap<String, Integer> inventory = new HashMap<>();
        inventory.put("Single Room", 3);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);

        List<Reservation> bookingHistory = new ArrayList<>();
        bookingHistory.add(new Reservation("RES_001", "Alice", "Single Room", "SINGLE_ROOM_1", 2));
        bookingHistory.add(new Reservation("RES_002", "Bob", "Suite Room", "SUITE_ROOM_1", 3));
        bookingHistory.add(new Reservation("RES_003", "Charlie", "Double Room", "DOUBLE_ROOM_1", 1));

        inventory.put("Single Room", 2);
        inventory.put("Suite Room", 0);
        inventory.put("Double Room", 1);

        System.out.println("Current Inventory Before Save:");
        for (String roomType : inventory.keySet()) {
            System.out.println("  " + roomType + ": " + inventory.get(roomType));
        }

        System.out.println("\nShutting down system. Saving state...\n");
        persistenceService.saveState(inventory, bookingHistory);

        // --- SIMULATE RESTART ---
        System.out.println("\n===== SYSTEM RESTART: Recovering State =====\n");

        SystemState restored = persistenceService.loadState();

        if (restored != null) {
            System.out.println("\nRestored Inventory:");
            for (String roomType : restored.inventory.keySet()) {
                System.out.println("  " + roomType + ": " + restored.inventory.get(roomType));
            }

            System.out.println("\nRestored Booking History:");
            for (Reservation r : restored.bookingHistory) {
                System.out.println("----------------------------");
                r.displayReservation();
            }
        } else {
            System.out.println("No previous state found. System starting fresh.");
        }

        System.out.println("\nSystem resumed successfully.");
    }
}