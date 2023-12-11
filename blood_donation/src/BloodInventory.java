
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class BloodInventory {
    private static BloodInventory instance = null;

    private final Map<String, Integer> bloodStock;

    // Constructor to initialize the BloodInventory
    public BloodInventory() {
        bloodStock = new HashMap<>();

        // Initialize default values for blood types
        String[] bloodTypes = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        for (String type : bloodTypes) {
            bloodStock.put(type, 0);
        }

        // Read blood inventory data from a file
        loadFromFile();
    }


    // Singleton pattern: Get the instance of BloodInventory
    public static BloodInventory getInstance() {
        if (instance == null) {
            instance = new BloodInventory();
        }
        return instance;
    }


    // Load blood inventory data from a file
    private void loadFromFile() {
        try {
            for (String line : Files.readAllLines(Paths.get("bloodInventory.txt"))) {
                String[] parts = line.split(":");
                bloodStock.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
            }
        } catch (Exception e) {
            System.out.println("Error reading inventory file. Using default values.");
        }
    }


    // Save blood inventory data to a file
    public void saveToFile() {
        StringBuilder data = new StringBuilder();
        for (Map.Entry<String, Integer> entry : bloodStock.entrySet()) {
            data.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        try {
            Files.write(Paths.get("bloodInventory.txt"), data.toString().getBytes());
        } catch (Exception e) {
            System.out.println("Error writing to inventory file.");
        }
    }


    // Get the stock for a specific blood type
    public int getStockForBloodType(String bloodType) {
        return bloodStock.getOrDefault(bloodType, -1);
    }


    // Add blood units to the inventory
    public void addBlood(String bloodType, int units) {
        bloodStock.put(bloodType, bloodStock.get(bloodType) + units);
        saveToFile(); // Save the updated inventory to the file
    }


    // Deduct blood units from the inventory
    public void deductBlood(String bloodType, int units) {
        bloodStock.put(bloodType, bloodStock.get(bloodType) - units);
        saveToFile(); // Save the updated inventory to the file
    }


    // Get a string representation of the entire blood inventory
    public String getAllStock() {
        StringBuilder stock = new StringBuilder();
        for (Map.Entry<String, Integer> entry : bloodStock.entrySet()) {
            stock.append(entry.getKey()).append(": ").append(entry.getValue()).append(" units\n");
        }
        return stock.toString();
    }


}
