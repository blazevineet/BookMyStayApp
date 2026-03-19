/**
 * BookMyStayApp - Hotel Booking Application
 * UC11: Concurrent booking with thread safety and synchronization.
 *
 * @author blazevineet
 * @version 1.0
 */

import java.util.HashMap;
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
}

class SharedInventory {
    private HashMap<String, Integer> inventory;

    SharedInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 3);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    synchronized boolean allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    synchronized void displayInventory() {
        System.out.println("\n===== Final Inventory =====");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + ": " + inventory.get(roomType) + " available");
        }
    }
}

class SharedBookingQueue {
    private Queue<Reservation> queue;

    SharedBookingQueue() {
        queue = new LinkedList<>();
    }

    synchronized void addRequest(Reservation reservation) {
        queue.add(reservation);
    }

    synchronized Reservation pollRequest() {
        return queue.poll();
    }

    synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

class ConcurrentBookingProcessor extends Thread {
    private SharedBookingQueue bookingQueue;
    private SharedInventory inventory;

    ConcurrentBookingProcessor(String name, SharedBookingQueue bookingQueue, SharedInventory inventory) {
        super(name);
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            Reservation reservation = bookingQueue.pollRequest();
            if (reservation == null) break;

            synchronized (inventory) {
                boolean success = inventory.allocateRoom(reservation.roomType);
                if (success) {
                    System.out.println("[" + getName() + "] CONFIRMED: " + reservation.guestName
                            + " -> " + reservation.roomType + " for " + reservation.nights + " night(s).");
                } else {
                    System.out.println("[" + getName() + "] FAILED: No availability for "
                            + reservation.roomType + " -> Guest: " + reservation.guestName);
                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println(getName() + " interrupted.");
            }
        }
    }
}

public class Main {

    /**
     * @param args command-line arguments
     */
    public static void main(String[] args) throws InterruptedException {

        SharedInventory inventory = new SharedInventory();
        SharedBookingQueue bookingQueue = new SharedBookingQueue();

        bookingQueue.addRequest(new Reservation("Alice", "Single Room", 2));
        bookingQueue.addRequest(new Reservation("Bob", "Suite Room", 1));
        bookingQueue.addRequest(new Reservation("Charlie", "Double Room", 3));
        bookingQueue.addRequest(new Reservation("Diana", "Single Room", 2));
        bookingQueue.addRequest(new Reservation("Eve", "Suite Room", 2));
        bookingQueue.addRequest(new Reservation("Frank", "Double Room", 1));
        bookingQueue.addRequest(new Reservation("Grace", "Single Room", 1));
        bookingQueue.addRequest(new Reservation("Hank", "Single Room", 2));

        System.out.println("===== Starting Concurrent Booking Processors =====\n");

        ConcurrentBookingProcessor t1 = new ConcurrentBookingProcessor("Processor-1", bookingQueue, inventory);
        ConcurrentBookingProcessor t2 = new ConcurrentBookingProcessor("Processor-2", bookingQueue, inventory);
        ConcurrentBookingProcessor t3 = new ConcurrentBookingProcessor("Processor-3", bookingQueue, inventory);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        inventory.displayInventory();

    }
}