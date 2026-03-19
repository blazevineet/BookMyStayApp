/**
 * BookMyStayApp - Hotel Booking Application
 * UC7: Optional add-on services for reservations.
 *
 * @author blazevineet
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AddOnService {
    String serviceName;
    double cost;

    AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    void displayService() {
        System.out.println("Service: " + serviceName + " | Cost: $" + cost);
    }
}

class AddOnServiceManager {
    private Map<String, List<AddOnService>> reservationServices;

    AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    void addService(String reservationId, AddOnService service) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);
        System.out.println("Added [" + service.serviceName + "] to Reservation: " + reservationId);
    }

    double calculateTotalCost(String reservationId) {
        double total = 0;
        if (reservationServices.containsKey(reservationId)) {
            for (AddOnService service : reservationServices.get(reservationId)) {
                total += service.cost;
            }
        }
        return total;
    }

    void displayServices(String reservationId) {
        System.out.println("\n===== Add-On Services for " + reservationId + " =====");
        if (!reservationServices.containsKey(reservationId) || reservationServices.get(reservationId).isEmpty()) {
            System.out.println("No services added.");
            return;
        }
        for (AddOnService service : reservationServices.get(reservationId)) {
            service.displayService();
        }
        System.out.println("Total Add-On Cost: $" + calculateTotalCost(reservationId));
    }
}

public class Main {

    /**
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        AddOnService breakfast = new AddOnService("Breakfast", 15.0);
        AddOnService spa = new AddOnService("Spa Access", 50.0);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 30.0);
        AddOnService laundry = new AddOnService("Laundry", 20.0);

        System.out.println("===== Guest Selecting Add-On Services =====\n");

        manager.addService("RES_001", breakfast);
        manager.addService("RES_001", spa);
        manager.addService("RES_001", airportPickup);

        manager.addService("RES_002", breakfast);
        manager.addService("RES_002", laundry);

        manager.displayServices("RES_001");
        manager.displayServices("RES_002");

        System.out.println("\nCore booking and inventory state unchanged.");

    }
}