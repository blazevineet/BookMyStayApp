/**
 * BookMyStayApp - Hotel Booking Application
 * UC5: Booking request queue using FIFO principle.
 *
 * @author blazevineet
 * @version 1.0
 */

import java.util.LinkedList;
import java.util.Queue;

class Reservation {
    String guestName;
    String roomType;
    int nights;

    Reservation(String guestName, String roomType, int nights) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    void displayRequest() {
        System.out.println("Guest: " + guestName + " | Room: " + roomType + " | Nights: " + nights);
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue;

    BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Request added: " + reservation.guestName + " -> " + reservation.roomType);
    }

    void displayQueue() {
        System.out.println("\n===== Current Booking Queue (FIFO) =====");
        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }
        int position = 1;
        for (Reservation r : queue) {
            System.out.print("Position " + position + ": ");
            r.displayRequest();
            position++;
        }
    }

    int getQueueSize() {
        return queue.size();
    }
}

public class Main {

    /**
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        System.out.println("===== Guests Submitting Booking Requests =====\n");

        bookingQueue.addRequest(new Reservation("Alice", "Single Room", 2));
        bookingQueue.addRequest(new Reservation("Bob", "Suite Room", 3));
        bookingQueue.addRequest(new Reservation("Charlie", "Double Room", 1));
        bookingQueue.addRequest(new Reservation("Diana", "Single Room", 4));

        bookingQueue.displayQueue();

        System.out.println("\nTotal requests in queue: " + bookingQueue.getQueueSize());
        System.out.println("\nNo inventory updated. Requests awaiting processing.");

    }
}