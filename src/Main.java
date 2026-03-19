/**
 * BookMyStayApp - Hotel Booking Application
 * UC9: Structured validation and error handling.
 *
 * @author blazevineet
 * @version 1.0
 */

import java.util.HashMap;

class InvalidRoomTypeException extends Exception {
    InvalidRoomTypeException(String roomType) {
        super("Invalid room type requested: " + roomType);
    }
}

class RoomNotAvailableException extends Exception {
    RoomNotAvailableException(String roomType) {
        super("No availability for room type: " + roomType);
    }
}

class InvalidNightsException extends Exception {
    InvalidNightsException(int nights) {
        super("Invalid number of nights: " + nights + ". Must be greater than zero.");
    }
}

class InvalidGuestNameException extends Exception {
    InvalidGuestNameException(String name) {
        super("Invalid guest name: '" + name + "'. Name cannot be empty.");
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, -1);
    }

    boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }

    void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

class InvalidBookingValidator {
    private RoomInventory inventory;

    InvalidBookingValidator(RoomInventory inventory) {
        this.inventory = inventory;
    }

    void validate(String guestName, String roomType, int nights)
            throws InvalidGuestNameException, InvalidRoomTypeException,
            InvalidNightsException, RoomNotAvailableException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidGuestNameException(guestName);
        }

        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidRoomTypeException(roomType);
        }

        if (nights <= 0) {
            throw new InvalidNightsException(nights);
        }

        if (inventory.getAvailability(roomType) <= 0) {
            throw new RoomNotAvailableException(roomType);
        }
    }

    void processBooking(String guestName, String roomType, int nights) {
        try {
            validate(guestName, roomType, nights);
            inventory.decrement(roomType);
            System.out.println("CONFIRMED: " + guestName + " -> " + roomType + " for " + nights + " night(s).");
        } catch (InvalidGuestNameException | InvalidRoomTypeException |
                 InvalidNightsException | RoomNotAvailableException e) {
            System.out.println("FAILED: " + e.getMessage());
        }
    }
}

public class Main {

    /**
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        InvalidBookingValidator validator = new InvalidBookingValidator(inventory);

        System.out.println("===== Processing Booking Requests =====\n");

        validator.processBooking("Alice", "Single Room", 2);
        validator.processBooking("", "Double Room", 1);
        validator.processBooking("Charlie", "Penthouse", 3);
        validator.processBooking("Diana", "Single Room", -1);
        validator.processBooking("Eve", "Suite Room", 2);
        validator.processBooking("Frank", "Double Room", 1);
        validator.processBooking("Grace", "Double Room", 2);

    }
}