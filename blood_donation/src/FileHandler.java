
import java.io.*;

public class FileHandler {


    // Method to write data to a file
    public static void writeToFile(String fileName, String data) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName, true)); // Open the file in append mode
            writer.write(data + "\n"); // Write the data followed by a newline character
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close(); // Close the file writer
                }
            } catch (IOException e) {
                System.out.println("An error occurred while closing the file: " + e.getMessage());
            }
        }
    }


    // Method to read data from a file
    public static String readFromFile(String fileName) {
        BufferedReader reader = null;
        StringBuilder result = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(fileName)); // Open the file for reading
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n"); // Read each line and append to the result
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file was not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close(); // Close the file reader
                }
            } catch (IOException e) {
                System.out.println("An error occurred while closing the file: " + e.getMessage());
            }
        }
        return result.toString(); // Return the content read from the file
    }


    // Method to clear data in a file
    public static void clearFile(String fileName) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName); // Open the file for writing (existing content will be cleared)
            writer.print(""); // Clear the content by writing an empty string
        } catch (FileNotFoundException e) {
            System.out.println("The file was not found: " + e.getMessage());
        } finally {
            if (writer != null) {
                writer.close(); // Close the file writer
            }
        }
    }


}
