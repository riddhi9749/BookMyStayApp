/**
 * Book My Stay Application
 * Use Case 3: Centralized Room Inventory Management
 *
 * Demonstrates use of HashMap for centralized inventory control.
 *
 * @author YourName
 * @version 3.0
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

// 🔥 NEW CLASS — CENTRALIZED INVENTORY
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor → initialize inventory
    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Display inventory
    void displayInventory() {
        System.out.println("---- Room Availability ----");
        for (String key : inventory.keySet()) {
            System.out.println(key + ": " + inventory.get(key));
        }
    }
}

// MAIN CLASS (SAME)
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 3.0\n");

        // ROOM OBJECTS
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // INVENTORY OBJECT
        RoomInventory inventory = new RoomInventory();

        // DISPLAY ROOM DETAILS
        System.out.println("---- Room Details ----");
        single.displayDetails();
        System.out.println();

        doubleRoom.displayDetails();
        System.out.println();

        suite.displayDetails();
        System.out.println();

        // DISPLAY INVENTORY
        inventory.displayInventory();

        // SAMPLE UPDATE
        System.out.println("\nUpdating Single Room availability...\n");
        inventory.updateAvailability("Single Room", 4);

        inventory.displayInventory();
    }
}

