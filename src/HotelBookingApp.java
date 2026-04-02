/**
 * Book My Stay Application
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Demonstrates safe cancellation using Stack (LIFO) and
 * inventory restoration.
 *
 * @author YourName
 * @version 10.0
 */

import java.util.*;

// CUSTOM EXCEPTION
class InvalidBookingException extends Exception {
    InvalidBookingException(String msg) {
        super(msg);
    }
}

// INVENTORY
class RoomInventory {

    private HashMap<String, Integer> inventory = new HashMap<>();

    RoomInventory() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
    }

    int getAvailability(String type) {
        return inventory.getOrDefault(type, -1);
    }

    void reduce(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    void increase(String type) {
        inventory.put(type, inventory.get(type) + 1);
    }
}

// RESERVATION
class Reservation {
    String id;
    String guest;
    String type;

    Reservation(String id, String guest, String type) {
        this.id = id;
        this.guest = guest;
        this.type = type;
    }
}

// BOOKING SERVICE
class BookingService {

    private int counter = 1;
    private Map<String, Reservation> activeBookings = new HashMap<>();

    Reservation book(String guest, String type, RoomInventory inv)
            throws InvalidBookingException {

        if (inv.getAvailability(type) <= 0) {
            throw new InvalidBookingException("No rooms available");
        }

        String id = "RES" + counter++;
        inv.reduce(type);

        Reservation r = new Reservation(id, guest, type);
        activeBookings.put(id, r);

        System.out.println("Booked: " + id);
        return r;
    }

    Map<String, Reservation> getBookings() {
        return activeBookings;
    }
}

// 🔥 CANCELLATION SERVICE
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    void cancel(String resId,
                Map<String, Reservation> bookings,
                RoomInventory inv)
            throws InvalidBookingException {

        // VALIDATE
        if (!bookings.containsKey(resId)) {
            throw new InvalidBookingException("Invalid reservation ID");
        }

        Reservation r = bookings.get(resId);

        // PUSH TO STACK (TRACK ROLLBACK)
        rollbackStack.push(resId);

        // RESTORE INVENTORY
        inv.increase(r.type);

        // REMOVE BOOKING
        bookings.remove(resId);

        System.out.println("Cancelled: " + resId);
    }

    void showRollbackHistory() {
        System.out.println("\nRollback Stack (LIFO): " + rollbackStack);
    }
}

// MAIN CLASS
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 10.0\n");

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService();
        CancellationService cancelService = new CancellationService();

        try {

            // BOOKINGS
            Reservation r1 = bookingService.book("Akshay", "Single Room", inventory);
            Reservation r2 = bookingService.book("Ravi", "Double Room", inventory);

            // CANCELLATION
            cancelService.cancel(r1.id, bookingService.getBookings(), inventory);

            // INVALID CANCEL
            cancelService.cancel("RES999", bookingService.getBookings(), inventory);

        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // SHOW STACK
        cancelService.showRollbackHistory();
    }
}