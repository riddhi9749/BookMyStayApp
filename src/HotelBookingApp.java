/**
 * Book My Stay Application
 * Use Case 9: Error Handling & Validation
 *
 * Demonstrates input validation, custom exceptions,
 * and safe system behavior.
 *
 * @author YourName
 * @version 9.0
 */

import java.util.*;

// 🔥 CUSTOM EXCEPTION
class InvalidBookingException extends Exception {
    InvalidBookingException(String message) {
        super(message);
    }
}

// ROOM CLASS
abstract class Room {
    String type;

    Room(String type) {
        this.type = type;
    }
}

class SingleRoom extends Room {
    SingleRoom() { super("Single Room"); }
}

class DoubleRoom extends Room {
    DoubleRoom() { super("Double Room"); }
}

class SuiteRoom extends Room {
    SuiteRoom() { super("Suite Room"); }
}

// INVENTORY
class RoomInventory {

    private HashMap<String, Integer> inventory = new HashMap<>();

    RoomInventory() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    int getAvailability(String type) {
        return inventory.getOrDefault(type, -1);
    }

    void reduceAvailability(String type) throws InvalidBookingException {

        int available = getAvailability(type);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for " + type);
        }

        inventory.put(type, available - 1);
    }
}

// RESERVATION
class Reservation {
    String guestName;
    String roomType;
    String id;

    Reservation(String guestName, String roomType, String id) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.id = id;
    }
}

// 🔥 VALIDATOR
class BookingValidator {

    static void validate(String guest, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        if (guest == null || guest.isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        if (inventory.getAvailability(roomType) == -1) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        if (inventory.getAvailability(roomType) == 0) {
            throw new InvalidBookingException("Room not available: " + roomType);
        }
    }
}

// BOOKING SERVICE
class BookingService {

    private int counter = 1;

    Reservation bookRoom(String guest, String type, RoomInventory inventory)
            throws InvalidBookingException {

        // 🔥 VALIDATE FIRST (FAIL FAST)
        BookingValidator.validate(guest, type, inventory);

        // ALLOCATE
        String id = "RES" + counter++;
        inventory.reduceAvailability(type);

        System.out.println("Booking Confirmed: " + id);
        return new Reservation(guest, type, id);
    }
}

// MAIN CLASS
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 9.0\n");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService();

        try {
            // VALID BOOKING
            service.bookRoom("Akshay", "Single Room", inventory);

            // INVALID ROOM TYPE
            service.bookRoom("Ravi", "Luxury Room", inventory);

            // NO AVAILABILITY
            service.bookRoom("Priya", "Suite Room", inventory);

        } catch (InvalidBookingException e) {

            // 🔥 GRACEFUL ERROR HANDLING
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\nSystem continues running safely...");
    }
}