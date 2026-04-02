/**
 * Book My Stay Application
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Demonstrates room allocation with uniqueness and inventory update.
 *
 * @author YourName
 * @version 6.0
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

// INVENTORY SERVICE
class RoomInventory {

    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
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

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// BOOKING QUEUE
class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    void addRequest(Reservation r) {
        queue.add(r);
    }

    Reservation getNext() {
        return queue.poll(); // FIFO
    }

    boolean isEmpty() {
        return queue.isEmpty();
    }
}

// 🔥 BOOKING SERVICE (MAIN LOGIC)
class BookingService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private HashMap<String, Set<String>> roomAllocations = new HashMap<>();

    void processBookings(BookingQueue queue, RoomInventory inventory) {

        int idCounter = 1;

        while (!queue.isEmpty()) {

            Reservation r = queue.getNext();
            String type = r.roomType;

            System.out.println("\nProcessing: " + r.guestName);

            if (inventory.getAvailability(type) > 0) {

                // GENERATE UNIQUE ROOM ID
                String roomId = type.substring(0, 2).toUpperCase() + idCounter++;

                // ENSURE UNIQUE
                if (!allocatedRoomIds.contains(roomId)) {

                    allocatedRoomIds.add(roomId);

                    // MAP ROOM TYPE → ROOM IDs
                    roomAllocations.putIfAbsent(type, new HashSet<>());
                    roomAllocations.get(type).add(roomId);

                    // UPDATE INVENTORY
                    inventory.reduceAvailability(type);

                    System.out.println("Booking Confirmed!");
                    System.out.println("Room Type: " + type);
                    System.out.println("Room ID: " + roomId);
                }

            } else {
                System.out.println("Booking Failed! No rooms available for " + type);
            }
        }
    }
}

// MAIN CLASS
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 6.0\n");

        // INVENTORY
        RoomInventory inventory = new RoomInventory();

        // QUEUE
        BookingQueue queue = new BookingQueue();

        // ADD REQUESTS
        queue.addRequest(new Reservation("Akshay", "Single Room"));
        queue.addRequest(new Reservation("Ravi", "Single Room"));
        queue.addRequest(new Reservation("Priya", "Single Room")); // should fail
        queue.addRequest(new Reservation("Kiran", "Suite Room"));

        // BOOKING SERVICE
        BookingService service = new BookingService();

        // PROCESS BOOKINGS
        service.processBookings(queue, inventory);
    }
}