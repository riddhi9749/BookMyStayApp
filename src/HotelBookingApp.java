/**
 * Book My Stay Application
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 *
 * Demonstrates multi-threading and synchronization to prevent
 * race conditions and double booking.
 *
 * @author YourName
 * @version 11.0
 */

import java.util.*;

// INVENTORY (THREAD SAFE)
class RoomInventory {

    private HashMap<String, Integer> inventory = new HashMap<>();

    RoomInventory() {
        inventory.put("Single Room", 1); // only 1 room → conflict simulation
    }

    // 🔥 SYNCHRONIZED METHOD (CRITICAL SECTION)
    synchronized boolean bookRoom(String type) {

        int available = inventory.getOrDefault(type, 0);

        if (available > 0) {

            System.out.println(Thread.currentThread().getName() + " booking...");

            // simulate delay (to expose race condition)
            try { Thread.sleep(100); } catch (Exception e) {}

            inventory.put(type, available - 1);

            System.out.println(Thread.currentThread().getName() + " SUCCESS");
            return true;

        } else {
            System.out.println(Thread.currentThread().getName() + " FAILED");
            return false;
        }
    }
}

// BOOKING TASK (THREAD)
class BookingTask implements Runnable {

    private RoomInventory inventory;
    private String guest;

    BookingTask(RoomInventory inventory, String guest) {
        this.inventory = inventory;
        this.guest = guest;
    }

    @Override
    public void run() {
        boolean result = inventory.bookRoom("Single Room");

        if (result) {
            System.out.println("Confirmed for " + guest);
        } else {
            System.out.println("No room for " + guest);
        }
    }
}

// MAIN CLASS
public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App =====");
        System.out.println("Version: 11.0\n");

        RoomInventory inventory = new RoomInventory();

        // 🔥 MULTIPLE THREADS (SIMULTANEOUS USERS)
        Thread t1 = new Thread(new BookingTask(inventory, "Akshay"));
        Thread t2 = new Thread(new BookingTask(inventory, "Ravi"));
        Thread t3 = new Thread(new BookingTask(inventory, "Priya"));

        // START THREADS
        t1.start();
        t2.start();
        t3.start();
    }
}