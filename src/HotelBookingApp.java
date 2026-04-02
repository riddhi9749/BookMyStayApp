/**
 * Book My Stay Application
 * Use Case 12: Data Persistence & System Recovery
 *
 * Demonstrates saving and loading system state using serialization.
 *
 * @author YourName
 * @version 12.0
 */

import java.io.*;
import java.util.*;

// 🔥 SERIALIZABLE INVENTORY
class RoomInventory implements Serializable {

    private static final long serialVersionUID = 1L;

    HashMap<String, Integer> inventory = new HashMap<>();

    RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    void display() {
        System.out.println("Inventory: " + inventory);
    }
}

// 🔥 SERIALIZABLE RESERVATION
class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    String id;
    String guest;
    String type;

    Reservation(String id, String guest, String type) {
        this.id = id;
        this.guest = guest;
        this.type = type;
    }

    public String toString() {
        return id + " | " + guest + " | " + type;
    }
}

// 🔥 PERSISTENCE SERVICE
class PersistenceService {

    private static final String FILE_NAME = "data.ser";

    // SAVE
    void save(RoomInventory inventory, List<Reservation> bookings) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(inventory);
            out.writeObject(bookings);

            System.out.println("Data saved successfully!");

        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // LOAD
    Object[] load() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            RoomInventory inventory = (RoomInventory) in.readObject();
            List<Reservation> bookings = (List<Reservation>) in.readObject();

            System.out.println("Data loaded successfully!");
            return new Object[]{inventory, bookings};

        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh...");
            return null;
        }
    }
}

// MAIN CLASS
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 12.0\n");

        PersistenceService ps = new PersistenceService();

        RoomInventory inventory;
        List<Reservation> bookings;

        // 🔥 LOAD DATA (RECOVERY)
        Object[] data = ps.load();

        if (data != null) {
            inventory = (RoomInventory) data[0];
            bookings = (List<Reservation>) data[1];
        } else {
            inventory = new RoomInventory();
            bookings = new ArrayList<>();
        }

        // DISPLAY CURRENT STATE
        inventory.display();
        System.out.println("Bookings: " + bookings);

        // SIMULATE NEW BOOKING
        Reservation r1 = new Reservation("RES1", "Akshay", "Single Room");
        bookings.add(r1);

        inventory.inventory.put("Single Room",
                inventory.inventory.get("Single Room") - 1);

        System.out.println("\nNew booking added!");

        // 🔥 SAVE DATA (PERSISTENCE)
        ps.save(inventory, bookings);
    }
}