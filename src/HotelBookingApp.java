/**
 * Book My Stay Application
 * Use Case 7: Add-On Service Selection
 *
 * Demonstrates adding optional services to reservations
 * without modifying booking or inventory logic.
 *
 * @author YourName
 * @version 7.0
 */

import java.util.*;

// ROOM CLASS
abstract class Room {
    String type;
    int beds;
    double price;

    Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }
}

// ROOM TYPES
class SingleRoom extends Room {
    SingleRoom() { super("Single Room", 1, 1000); }
}

class DoubleRoom extends Room {
    DoubleRoom() { super("Double Room", 2, 2000); }
}

class SuiteRoom extends Room {
    SuiteRoom() { super("Suite Room", 3, 5000); }
}

// INVENTORY
class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    void reduceAvailability(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

// RESERVATION
class Reservation {
    String guestName;
    String roomType;
    String reservationId;

    Reservation(String guestName, String roomType, String reservationId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.reservationId = reservationId;
    }
}

// BOOKING SERVICE (UNCHANGED CORE)
class BookingService {

    private int idCounter = 1;

    Reservation bookRoom(String guest, String type, RoomInventory inventory) {

        if (inventory.getAvailability(type) > 0) {

            String resId = "RES" + idCounter++;
            inventory.reduceAvailability(type);

            System.out.println("Booking Confirmed for " + guest + " | ID: " + resId);
            return new Reservation(guest, type, resId);

        } else {
            System.out.println("Booking Failed for " + guest);
            return null;
        }
    }
}

// 🔥 ADD-ON SERVICE
class AddOnService {
    String name;
    double price;

    AddOnService(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

// 🔥 SERVICE MANAGER
class AddOnServiceManager {

    private HashMap<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to reservation
    void addService(String reservationId, AddOnService service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added " + service.name + " to " + reservationId);
    }

    // Calculate total cost
    double calculateTotal(String reservationId) {

        double total = 0;

        List<AddOnService> services = serviceMap.getOrDefault(reservationId, new ArrayList<>());

        for (AddOnService s : services) {
            total += s.price;
        }

        return total;
    }

    // Display services
    void showServices(String reservationId) {

        System.out.println("\nServices for " + reservationId + ":");

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnService s : services) {
            System.out.println(s.name + " - ₹" + s.price);
        }

        System.out.println("Total Add-On Cost: ₹" + calculateTotal(reservationId));
    }
}

// MAIN CLASS
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 7.0\n");

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService();

        // BOOK ROOM
        Reservation r1 = bookingService.bookRoom("Akshay", "Single Room", inventory);

        // ADD-ON MANAGER
        AddOnServiceManager manager = new AddOnServiceManager();

        if (r1 != null) {

            // ADD SERVICES
            manager.addService(r1.reservationId, new AddOnService("Breakfast", 200));
            manager.addService(r1.reservationId, new AddOnService("Airport Pickup", 500));
            manager.addService(r1.reservationId, new AddOnService("Extra Bed", 300));

            // SHOW SERVICES
            manager.showServices(r1.reservationId);
        }
    }
}