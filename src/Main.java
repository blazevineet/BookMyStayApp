/**
 * BookMyStayApp - Hotel Booking Application
 * UC8: Historical tracking of confirmed bookings.
 *
 * @author blazevineet
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;

class Reservation {
    String reservationId;
    String guestName;
    String roomType;
    String roomId;
    int nights;
    double pricePerNight;

    Reservation(String reservationId, String guestName, String roomType, String roomId, int nights, double pricePerNight) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.nights = nights;
        this.pricePerNight = pricePerNight;
    }

    double getTotalCost() {
        return nights * pricePerNight;
    }

    void displayReservation() {
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Guest          : " + guestName);
        System.out.println("Room Type      : " + roomType);
        System.out.println("Room ID        : " + roomId);
        System.out.println("Nights         : " + nights);
        System.out.println("Total Cost     : $" + getTotalCost());
    }
}

class BookingHistory {
    private List<Reservation> history;

    BookingHistory() {
        history = new ArrayList<>();
    }

    void addBooking(Reservation reservation) {
        history.add(reservation);
        System.out.println("Booking recorded: " + reservation.reservationId + " -> " + reservation.guestName);
    }

    List<Reservation> getHistory() {
        return history;
    }

    int getTotalBookings() {
        return history.size();
    }
}

class BookingReportService {
    private BookingHistory bookingHistory;

    BookingReportService(BookingHistory bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    void displayAllBookings() {
        System.out.println("\n===== Full Booking History =====");
        if (bookingHistory.getHistory().isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        for (Reservation r : bookingHistory.getHistory()) {
            System.out.println("----------------------------");
            r.displayReservation();
        }
    }

    void generateSummaryReport() {
        System.out.println("\n===== Booking Summary Report =====");
        int totalBookings = bookingHistory.getTotalBookings();
        double totalRevenue = 0;

        for (Reservation r : bookingHistory.getHistory()) {
            totalRevenue += r.getTotalCost();
        }

        System.out.println("Total Bookings : " + totalBookings);
        System.out.println("Total Revenue  : $" + totalRevenue);
    }
}

public class Main {

    /**
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        System.out.println("===== Confirming Bookings =====\n");

        history.addBooking(new Reservation("RES_001", "Alice", "Single Room", "SINGLE_ROOM_1", 2, 100.0));
        history.addBooking(new Reservation("RES_002", "Bob", "Suite Room", "SUITE_ROOM_1", 3, 350.0));
        history.addBooking(new Reservation("RES_003", "Charlie", "Double Room", "DOUBLE_ROOM_1", 1, 180.0));
        history.addBooking(new Reservation("RES_004", "Diana", "Single Room", "SINGLE_ROOM_2", 4, 100.0));

        BookingReportService reportService = new BookingReportService(history);

        reportService.displayAllBookings();
        reportService.generateSummaryReport();

    }
}