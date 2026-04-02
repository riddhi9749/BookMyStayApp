/**
 * Book My Stay Application
 * Use Case 8: Booking History & Reporting
 *
 * Demonstrates storing booking history and generating reports.
 *
 * @author YourName
 * @version 8.0
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

    void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

// BOOKING SERVICE
class BookingService {

    private int idCounter = 1;

    Reservation bookRoom(String guest, String type, RoomInventory inventory) {

        if (inventory.getAvailability(type) > 0) {

            String resId = "RES" + idCounter++;
            inventory.reduceAvailability(type);

            System.out.println("Booking Confirmed: " + resId);
            return new Reservation(guest, type, resId);

        } else {
            System.out.println("Booking Failed for " + guest);
            return null;
        }
    }
}

// 🔥 BOOKING HISTORY (LIST)
class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    // Add booking
    void addReservation(Reservation r) {
        if (r != null) {
            history.add(r);
        }
    }

    // Get all bookings
    List<Reservation> getAll() {
        return history;
    }
}

// 🔥 REPORT SERVICE
class BookingReportService {

    // Show all bookings
    void showAllBookings(List<Reservation> history) {

        System.out.println("\n---- Booking History ----");

        for (Reservation r : history) {
            r.display();
        }
    }

    // Summary report
    void showSummary(List<Reservation> history) {

        System.out.println("\n---- Booking Summary ----");

        HashMap<String, Integer> countMap = new HashMap<>();

        for (Reservation r : history) {
            countMap.put(r.roomType, countMap.getOrDefault(r.roomType, 0) + 1);
        }

        for (String type : countMap.keySet()) {
            System.out.println(type + ": " + countMap.get(type));
        }
    }
}

// MAIN CLASS
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 8.0\n");

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService();
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // BOOKINGS
        Reservation r1 = bookingService.bookRoom("Akshay", "Single Room", inventory);
        Reservation r2 = bookingService.bookRoom("Ravi", "Double Room", inventory);
        Reservation r3 = bookingService.bookRoom("Priya", "Suite Room", inventory);

        // STORE HISTORY
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // REPORTS
        reportService.showAllBookings(history.getAll());
        reportService.showSummary(history.getAll());
    }
}