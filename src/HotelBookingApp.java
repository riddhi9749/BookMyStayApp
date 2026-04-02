/**
 * Book My Stay Application
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Demonstrates Queue (FIFO) for fair booking request handling.
 *
 * @author YourName
 * @version 5.0
 */

import java.util.*;

// ABSTRACT ROOM CLASS
abstract class Room {
    String type;
    int beds;
    double price;

    Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    void displayDetails() {
        System.out.println(type + " | Beds: " + beds + " | Price: ₹" + price);
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

// INVENTORY (READ ONLY FOR NOW)
class RoomInventory {
    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

// 🔥 NEW CLASS — RESERVATION
class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    void display() {
        System.out.println("Guest: " + guestName + " | Requested: " + roomType);
    }
}

// 🔥 NEW CLASS — BOOKING QUEUE (FIFO)
class BookingQueue {

    private Queue<Reservation> queue;

    BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add request
    void addRequest(Reservation r) {
        queue.add(r);
        System.out.println("Request added for " + r.guestName);
    }

    // Show all requests
    void showQueue() {
        System.out.println("\n---- Booking Requests (FIFO Order) ----");
        for (Reservation r : queue) {
            r.display();
        }
    }
}

// MAIN CLASS
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 5.0\n");

        // INVENTORY (only for reference)
        RoomInventory inventory = new RoomInventory();

        // BOOKING QUEUE
        BookingQueue bookingQueue = new BookingQueue();

        // 🔥 SIMULATE REQUESTS (FIRST COME FIRST SERVED)
        bookingQueue.addRequest(new Reservation("Akshay", "Single Room"));
        bookingQueue.addRequest(new Reservation("Ravi", "Double Room"));
        bookingQueue.addRequest(new Reservation("Priya", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Kiran", "Single Room"));

        // DISPLAY QUEUE
        bookingQueue.showQueue();

        System.out.println("\nNote: No rooms allocated yet (only requests stored).");
    }
}