
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class Admin {
    private final JFrame adminFrame;

    public Admin() {
        adminFrame = new JFrame("Admin Dashboard");
        initializeGUI();
    }


    private void initializeGUI() {
        adminFrame.setSize(900, 550);
        adminFrame.setResizable(false);

        // Create buttons for different actions
        JButton deactivateButton = createStyledButton("Deactivate Donor Account");
        JButton deleteButton = createStyledButton("Delete Donor Account");
        JButton listDonorsButton = createStyledButton("List All Donors");
        JButton manageInventoryButton = createStyledButton("Manage Blood Inventory");
        JButton logoutButton = createStyledButton("Exit");

        // Add action listeners to the buttons
        deactivateButton.addActionListener(e -> deactivateDonor());
        deleteButton.addActionListener(e -> deleteDonor());
        listDonorsButton.addActionListener(e -> listAllDonors());
        manageInventoryButton.addActionListener(e -> manageInventory());
        logoutButton.addActionListener(e -> {
            adminFrame.dispose();
            new MainApp();
        });

        // Load image from resources
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("picture-2.jpg"));
        JLabel imageLabel = new JLabel("", JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(950, 550));
        imageLabel.setIcon(imageIcon);


        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 8, 8));
        buttonPanel.add(deactivateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(listDonorsButton);
        buttonPanel.add(manageInventoryButton);
        buttonPanel.add(logoutButton);
        buttonPanel.setBackground(new Color(29, 106, 250));

        // Create the main panel to hold the image and buttons
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(imageLabel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        mainPanel.setBackground(new Color(29, 106, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Set the main panel as the content pane of the frame
        adminFrame.setContentPane(mainPanel);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setVisible(true);
    }


    private JButton createStyledButton(String text) {
        // Create and style a button with custom colors and fonts
        JButton button = new JButton(text);
        button.setBackground(new Color(138, 3, 3, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Tahoma", Font.BOLD, 15));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(243, 53, 53, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(138, 3, 3));
            }
        });
        return button;
    }


    // Method to deactivate a donor's account
    private void deactivateDonor() {

        try {
            UIManager.put("OptionPane.background", Color.darkGray);
            UIManager.put("Panel.background", Color.cyan);
            UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.BOLD, 18));

            // Create a panel to input donor details for deactivation
            JPanel panel = new JPanel(new GridLayout(5, 2));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panel.setPreferredSize(new Dimension(600, 370));

            JLabel nameLabel = new JLabel("    Name:");
            JLabel ageLabel = new JLabel("    Age:");
            JLabel phoneLabel = new JLabel("    Phone Number:");
            JLabel bloodGroupLabel = new JLabel("    Blood Group:");

            JTextField nameField = new JTextField(20);
            JTextField ageField = new JTextField(20);
            JTextField phoneField = new JTextField(20);
            JTextField bloodGroupField = new JTextField(20);

            panel.add(nameLabel);
            panel.add(nameField);
            panel.add(ageLabel);
            panel.add(ageField);
            panel.add(phoneLabel);
            panel.add(phoneField);
            panel.add(bloodGroupLabel);
            panel.add(bloodGroupField);

            int result = JOptionPane.showConfirmDialog(adminFrame, panel,
                    "Enter Donor Details to Deactivate",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                // Retrieve and validate donor details
                String donorName = nameField.getText().trim();
                String donorAge = ageField.getText().trim();
                String donorPhone = phoneField.getText().trim();
                String donorBloodGroup = bloodGroupField.getText().trim();

                // Read the list of donors from a file
                ArrayList<String> donors = new ArrayList<String>(
                        Arrays.asList(FileHandler.readFromFile("donors.txt").split("\n"))
                );

                if (donors.isEmpty()) {
                    throw new Exception("The donors file is empty or not formatted correctly.");
                }

                boolean found = false;
                String donorDetails = "";
                int foundIndex = -1;

                // Search for the donor in the list
                for (int i = 0; i < donors.size(); i++) {
                    String donor = donors.get(i);
                    String[] details = donor.split(",");
                    if (details[0].equals(donorName) && details[6].equals(donorAge)
                            && details[4].equals(donorPhone) && details[1].equals(donorBloodGroup)) {
                        found = true;
                        donorDetails = donor;
                        foundIndex = i;
                        break;
                    }
                }

                if (!found) {
                    // Donor not found
                    JOptionPane.showMessageDialog(adminFrame,
                            "Donor not found!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(adminFrame,
                        "Are you sure you want to deactivate this donor?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Mark the donor as deactivated
                    donors.set(foundIndex, donorDetails + ",deactivated");

                    // Clear and rewrite the donors file
                    FileHandler.clearFile("donors.txt");
                    for (String donor : donors) {
                        FileHandler.writeToFile("donors.txt", donor);
                    }

                    String[] donorDetailParts = donorDetails.split(",");
                    StringBuilder formattedDetails = new StringBuilder("\n---- DEACTIVATED DONOR DETAILS ----\n\n");
                    for (String detail : donorDetailParts) {
                        formattedDetails.append(detail).append("\n");
                    }

                    // Display deactivated donor details
                    JTextArea textArea = new JTextArea(16, 30);
                    textArea.setText(formattedDetails.toString());
                    textArea.setWrapStyleWord(true);
                    textArea.setLineWrap(true);
                    textArea.setEditable(false);
                    textArea.setBackground(Color.cyan);
                    textArea.setForeground(Color.BLACK);
                    textArea.setFont(new Font("Tahoma", Font.BOLD, 15));
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(600, 440));
                    JOptionPane.showMessageDialog(adminFrame, scrollPane,
                            "Deactivated Donor Details",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            // Handle exceptions and display error messages
            JOptionPane.showMessageDialog(adminFrame,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            // Reset UI settings
            UIManager.put("OptionPane.background", null);
            UIManager.put("Panel.background", null);
            UIManager.put("OptionPane.messageFont", null);
        }
    }


    // Method to delete a donor's account
    private void deleteDonor() {

        try {
            UIManager.put("OptionPane.background", Color.darkGray);
            UIManager.put("Panel.background", Color.cyan);
            UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.BOLD, 18));

            // Create a panel to input donor details for deletion
            JPanel panel = new JPanel(new GridLayout(5, 2));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panel.setPreferredSize(new Dimension(600, 370));

            JLabel nameLabel = new JLabel("    Name:");
            JLabel ageLabel = new JLabel("    Age:");
            JLabel phoneLabel = new JLabel("    Phone Number:");
            JLabel bloodGroupLabel = new JLabel("    Blood Group:");

            JTextField nameField = new JTextField(20);
            JTextField ageField = new JTextField(20);
            JTextField phoneField = new JTextField(20);
            JTextField bloodGroupField = new JTextField(20);

            panel.add(nameLabel);
            panel.add(nameField);
            panel.add(ageLabel);
            panel.add(ageField);
            panel.add(phoneLabel);
            panel.add(phoneField);
            panel.add(bloodGroupLabel);
            panel.add(bloodGroupField);

            int result = JOptionPane.showConfirmDialog(adminFrame, panel,
                    "Enter Donor Details to Delete",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                // Retrieve and validate donor details
                String donorName = nameField.getText().trim();
                String donorAge = ageField.getText().trim();
                String donorPhone = phoneField.getText().trim();
                String donorBloodGroup = bloodGroupField.getText().trim();

                // Read the list of donors from a file
                ArrayList<String> donors = new ArrayList<String>(
                        Arrays.asList(FileHandler.readFromFile("donors.txt").split("\n"))
                );

                boolean found = false;
                String donorDetails = "";

                // Search for the donor in the list and remove if found
                for (String donor : donors) {
                    String[] details = donor.split(",");
                    if (details[0].equals(donorName) && details[6].equals(donorAge)
                            && details[4].equals(donorPhone) && details[1].equals(donorBloodGroup)) {
                        donorDetails = donor;
                        donors.remove(donor);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    // Donor not found
                    JOptionPane.showMessageDialog(adminFrame,
                            "Donor not found!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(adminFrame,
                        "Are you sure you want to delete this donor?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Clear and rewrite the donors file without the deleted donor
                    FileHandler.clearFile("donors.txt");
                    for (String donor : donors) {
                        FileHandler.writeToFile("donors.txt", donor);
                    }

                    String[] detailsArr = donorDetails.split(",");
                    String formattedDetails = "\n---- DELETED DONOR DETAILS ----\n\n" +
                            "Name: " + detailsArr[0] + "\n" +
                            "Blood Group: " + detailsArr[1] + "\n" +
                            "Phone: " + detailsArr[4] + "\n" +
                            "Age: " + detailsArr[6] + "\n";

                    // Display deleted donor details
                    JTextArea textArea = new JTextArea(11, 30);
                    textArea.setText(formattedDetails);
                    textArea.setWrapStyleWord(true);
                    textArea.setLineWrap(true);
                    textArea.setEditable(false);
                    textArea.setBackground(Color.cyan);
                    textArea.setForeground(Color.BLACK);
                    textArea.setFont(new Font("Tahoma", Font.BOLD, 15));
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(600, 440));
                    JOptionPane.showMessageDialog(adminFrame, scrollPane,
                            "Deleted Donor Details",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            // Handle exceptions and display error messages
            JOptionPane.showMessageDialog(adminFrame,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            // Reset UI settings
            UIManager.put("OptionPane.background", null);
            UIManager.put("Panel.background", null);
            UIManager.put("OptionPane.messageFont", null);
        }
    }


    // Method to list all donors
    private void listAllDonors() {

        try {
            UIManager.put("OptionPane.background", Color.darkGray);
            UIManager.put("Panel.background", Color.cyan);

            // Read donor data from a file
            String allDonorsData = FileHandler.readFromFile("donors.txt");
            String[] donorLines = allDonorsData.split("\n");

            // Define column names for the table
            String[] columnNames = {"Name", "Blood Type", "Last Donation Date", "Address", "Contact Number", "Email", "Age"};

            // Prepare data for the table
            ArrayList<ArrayList<String>> data = new ArrayList<>();

            for (String donorData : donorLines) {
                String[] donorDetails = donorData.split(",");
                if (donorDetails.length == 7) {
                    ArrayList<String> rowData = new ArrayList<>();
                    for (String detail : donorDetails) {
                        rowData.add(detail.trim());
                    }
                    data.add(rowData);
                }
            }

            // Create a table model
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

            for (ArrayList<String> rowData : data) {
                tableModel.addRow(rowData.toArray());
            }

            // Create a table to display the donor data
            JTable table = new JTable(tableModel);
            Font font = new Font("Tahoma", Font.BOLD, 13);
            table.setFont(font);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            table.setFillsViewportHeight(true);
            table.setEnabled(false);

            // Create a scroll pane for the table
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(1000, 600));

            // Customize and show the donor list dialog
            UIManager.put("OptionPane.messageFont", font);
            JOptionPane.showMessageDialog(adminFrame, scrollPane, "All Donors", JOptionPane.PLAIN_MESSAGE);
            UIManager.put("OptionPane.messageFont", null);
        } catch (Exception e) {
            // Handle exceptions and display error messages
            JOptionPane.showMessageDialog(adminFrame,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            // Reset UI settings
            UIManager.put("OptionPane.background", null);
            UIManager.put("Panel.background", null);
        }
    }


    // Method to manage blood inventory
    private void manageInventory() {

        try {
            UIManager.put("OptionPane.background", Color.darkGray);
            UIManager.put("Panel.background", Color.cyan);
            UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.BOLD, 24));

            // Create a panel to select blood type and adjust units
            JPanel selectBloodTypePanel = new JPanel(new GridLayout(2, 1));
            selectBloodTypePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel bloodTypeLabel = new JLabel("    Choose Blood Type:");
            bloodTypeLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
            String[] bloodTypes = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
            JComboBox<String> bloodTypeComboBox = new JComboBox<>(bloodTypes);
            bloodTypeComboBox.setFont(new Font("Tahoma", Font.BOLD, 20));

            selectBloodTypePanel.add(bloodTypeLabel);
            selectBloodTypePanel.add(bloodTypeComboBox);

            int result = JOptionPane.showConfirmDialog(adminFrame, selectBloodTypePanel,
                    "Select Blood Type",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String selectedBloodType = (String) bloodTypeComboBox.getSelectedItem();

                // Create a panel to input action (add/remove) and units
                JPanel adjustUnitsPanel = new JPanel(new GridLayout(3, 2));
                adjustUnitsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                JLabel actionLabel = new JLabel("    Action:");
                actionLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
                String[] actions = {"Add", "Remove"};
                JComboBox<String> actionComboBox = new JComboBox<>(actions);
                actionComboBox.setFont(new Font("Tahoma", Font.BOLD, 20));

                JLabel unitsLabel = new JLabel("    Units:");
                unitsLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
                JTextField unitsField = new JTextField();
                unitsField.setFont(new Font("Tahoma", Font.BOLD, 20));

                adjustUnitsPanel.add(actionLabel);
                adjustUnitsPanel.add(actionComboBox);
                adjustUnitsPanel.add(unitsLabel);
                adjustUnitsPanel.add(unitsField);

                int actionResult = JOptionPane.showConfirmDialog(adminFrame, adjustUnitsPanel,
                        "Adjust Units",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                if (actionResult == JOptionPane.OK_OPTION) {
                    String action = (String) actionComboBox.getSelectedItem();
                    int units = Integer.parseInt(unitsField.getText().trim());
                    BloodInventory inventory = BloodInventory.getInstance();

                    if (Objects.equals(action, "Add")) {
                        inventory.addBlood(selectedBloodType, units);
                    } else {
                        inventory.deductBlood(selectedBloodType, units);
                    }

                    JOptionPane.showMessageDialog(adminFrame, "Inventory updated successfully!");
                }
            }
        } catch (NumberFormatException e) {
            // Handle invalid input (non-numeric units)
            JOptionPane.showMessageDialog(adminFrame, "Invalid number of units entered!");
        } catch (Exception e) {
            // Handle exceptions and display error messages
            JOptionPane.showMessageDialog(adminFrame,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            // Reset UI settings
            UIManager.put("OptionPane.background", null);
            UIManager.put("Panel.background", null);
            UIManager.put("OptionPane.messageFont", null);
        }
    }


    // Entry point for the Admin class
    public static void adminLogin() {

        new Admin();
    }


}
