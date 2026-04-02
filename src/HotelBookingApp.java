/**
 * Book My Stay Application
 * Use Case 4: Room Search & Availability Check
 *
 * Demonstrates read-only search on inventory without modifying system state.
 *
 * @author YourName
 * @version 4.0
 */

import java.util.HashMap;

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
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

// ROOM TYPES
class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 1, 1000);
    }
}

class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 2, 2000);
    }
}

class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 3, 5000);
    }
}

// INVENTORY CLASS (UNCHANGED CORE LOGIC)
class RoomInventory {

    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // intentionally unavailable
        inventory.put("Suite Room", 2);
    }

    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// 🔥 NEW CLASS — SEARCH SERVICE (READ-ONLY)
class RoomSearchService {

    void searchAvailableRooms(Room[] rooms, RoomInventory inventory) {

        System.out.println("---- Available Rooms ----");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.type);

            // FILTER ONLY AVAILABLE ROOMS
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

// MAIN CLASS (SAME)
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 4.0\n");

        // ROOM OBJECTS
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        Room[] rooms = {single, doubleRoom, suite};

        // INVENTORY
        RoomInventory inventory = new RoomInventory();

        // SEARCH SERVICE
        RoomSearchService searchService = new RoomSearchService();

        // 🔍 SEARCH (READ ONLY)
        searchService.searchAvailableRooms(rooms, inventory);
    }
}
