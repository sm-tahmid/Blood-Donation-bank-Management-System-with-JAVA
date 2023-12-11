import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;

public class User {
    private final JFrame userFrame;

    public User() {
        // Constructor for the User class
        userFrame = new JFrame("User Dashboard");
        initializeGUI();
    }

    private void initializeGUI() {
        // Initialize the user interface for the User dashboard
        userFrame.setSize(900, 550);
        userFrame.setResizable(false);

        // Create buttons for various user actions
        JButton registerButton = createStyledButton("Register as Donor");
        JButton profileButton = createStyledButton("My Profile");
        JButton updateDetailsButton = createStyledButton("Update Donor Details");
        JButton searchBloodButton = createStyledButton("Search for Blood");
        JButton requestBloodButton = createStyledButton("Request for Blood");
        JButton viewDonorsButton = createStyledButton("View Available Donors");
        JButton bloodStockButton = createStyledButton("View Blood Stock Report");
        JButton deactivateAccountButton = createStyledButton("List Of Age");
        JButton deleteAccountButton = createStyledButton("All Donors");
        JButton logoutButton = createStyledButton("Exit");

        // Add action listeners to buttons
        registerButton.addActionListener(e -> registerDonor());
        profileButton.addActionListener(e -> viewProfile());
        updateDetailsButton.addActionListener(e -> updateDetails());
        searchBloodButton.addActionListener(e -> searchBlood());
        requestBloodButton.addActionListener(e -> requestBlood());
        viewDonorsButton.addActionListener(e -> viewAvailableDonors());
        bloodStockButton.addActionListener(e -> viewBloodStock());
        deactivateAccountButton.addActionListener(e -> ageList());
        deleteAccountButton.addActionListener(e -> allDonors());
        logoutButton.addActionListener(e -> {
            userFrame.dispose();
            new MainApp(); // Return to the main application screen
        });

        // Load image from resources
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("picture-3.jpg"));
        JLabel imageLabel = new JLabel("", JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(950, 550));
        imageLabel.setIcon(imageIcon);

        // Create a panel to hold buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(10, 1, 8, 8)); // 10 rows, 1 column, with gaps
        buttonPanel.add(registerButton);
        buttonPanel.add(profileButton);
        buttonPanel.add(updateDetailsButton);
        buttonPanel.add(searchBloodButton);
        buttonPanel.add(requestBloodButton);
        buttonPanel.add(viewDonorsButton);
        buttonPanel.add(bloodStockButton);
        buttonPanel.add(deactivateAccountButton);
        buttonPanel.add(deleteAccountButton);
        buttonPanel.add(logoutButton);
        buttonPanel.setBackground(new Color(29, 106, 250));

        // Create the main panel and arrange components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(imageLabel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        mainPanel.setBackground(new Color(29, 106, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        userFrame.setContentPane(mainPanel);
        userFrame.setLocationRelativeTo(null); // Center the frame on the screen
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userFrame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        // Create and style a button
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


    //Register as a donor method
    private void registerDonor() {
        // Create input fields for donor registration
        JTextField nameField = new JTextField(20);
        JTextField bloodTypeField = new JTextField(10);
        JTextField lastDonationDateField = new JTextField(10);
        JTextField addressField = new JTextField(30);
        JTextField contactNumberField = new JTextField(10);
        JTextField emailField = new JTextField(20);
        JTextField ageField = new JTextField(3);  // Age field

        // Create a panel to arrange input fields
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));  // Rows increased to accommodate age
        panel.setBackground(Color.cyan);
        panel.setPreferredSize(new Dimension(600, 370));  // Adjusted panel height

        // Add labels and input fields to the panel
        panel.add(new JLabel("    Name:"));
        panel.add(nameField);
        panel.add(new JLabel("    Blood Type (A+, A-, B+...):"));
        panel.add(bloodTypeField);
        panel.add(new JLabel("    Last Donation Date (yyyy-mm-dd):"));
        panel.add(lastDonationDateField);
        panel.add(new JLabel("    Address:"));
        panel.add(addressField);
        panel.add(new JLabel("    Contact Number:"));
        panel.add(contactNumberField);
        panel.add(new JLabel("    Email:"));
        panel.add(emailField);
        panel.add(new JLabel("    Age:"));
        panel.add(ageField);  // Age field added to the panel

        // Set UIManager properties for dialog appearance
        UIManager.put("OptionPane.background", Color.darkGray);
        UIManager.put("Panel.background", Color.cyan);

        // Display the registration dialog
        int result = JOptionPane.showConfirmDialog(userFrame, panel, "Register as Donor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);

        // Custom font for JOptionPane messages
        Font messageFont = new Font("Tahoma", Font.PLAIN, 18);  // Adjust font family, style, and size as required

        if (result == JOptionPane.OK_OPTION) {
            // Retrieve user inputs
            String name = nameField.getText();
            String bloodType = bloodTypeField.getText();
            String lastDonationDate = lastDonationDateField.getText();
            String address = addressField.getText();
            String contactNumber = contactNumberField.getText();
            String email = emailField.getText();
            String age = ageField.getText();  // Age value retrieved

            if (name != null && !name.isEmpty() && bloodType != null && !bloodType.isEmpty() &&
                    lastDonationDate != null && !lastDonationDate.isEmpty() &&
                    address != null && !address.isEmpty() &&
                    contactNumber != null && !contactNumber.isEmpty() &&
                    email != null && !email.isEmpty() && age != null && !age.isEmpty()) {

                // Prepare donor details
                String donorDetails = String.join(",", name, bloodType, lastDonationDate, address, contactNumber, email, age);  // Age added
                FileHandler.writeToFile("donors.txt", donorDetails);

                // Set message font for success message
                UIManager.put("OptionPane.messageFont", messageFont);
                JOptionPane.showMessageDialog(userFrame, "! Successfully Registered As A Donor !");
                UIManager.put("OptionPane.messageFont", null);  // Reset to default after using
            } else {
                // Set message font for failure message
                UIManager.put("OptionPane.messageFont", messageFont);
                JOptionPane.showMessageDialog(userFrame, "Failed to register. Please provide all details.");
                UIManager.put("OptionPane.messageFont", null);  // Reset to default after using
            }
        }

        // Reset UIManager to default after using
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
    }


    // View profile method
    private void viewProfile() {
        // Set UIManager properties for input dialog appearance
        UIManager.put("OptionPane.background", Color.darkGray);
        UIManager.put("Panel.background", Color.cyan);

        // Increase font size for input dialog
        Font inputFont = new Font("Tahoma", Font.BOLD, 18);
        UIManager.put("OptionPane.messageFont", inputFont);

        // Prompt the user for their name to view the profile
        String name = JOptionPane.showInputDialog(userFrame, "Enter your name to view profile:    ");

        // Resetting the font size for subsequent dialogs
        UIManager.put("OptionPane.messageFont", null);

        String data = FileHandler.readFromFile("donors.txt");
        for (String donor : data.split("\n")) {
            if (donor.startsWith(name + ",")) {
                // Extract and display donor profile details
                String[] details = donor.split(",");
                JTextArea textArea = new JTextArea(10, 30);

                textArea.setText("\n----- PROFILE DETAILS -----\n\n\n" +
                        "     Name :  " + details[0] + "\n\n" +
                        "     Blood Type :  " + details[1] + "\n\n" +
                        "     Last Donation Date :  " + details[2] + "\n\n" +
                        "     Address :  " + details[3] + "\n\n" +
                        "     Contact Number :  " + details[4] + "\n\n" +
                        "     Email :  " + details[5] + "\n\n" +
                        "     Age :  " + details[6] + "\n\n" +   // Age displayed here
                        "     Active Status :  " + (details.length > 7 && "deactivated".equals(details[7]) ? "Deactivated" : "Active"));

                textArea.setWrapStyleWord(true);
                textArea.setLineWrap(true);
                textArea.setEditable(false);
                textArea.setBackground(Color.cyan);
                textArea.setForeground(Color.BLACK);
                textArea.setFont(new Font("Tahoma", Font.BOLD, 15));

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(600, 400));

                // Maintain the same color setup for the profile display
                JOptionPane.showMessageDialog(userFrame, scrollPane, "Profile Details", JOptionPane.INFORMATION_MESSAGE);

                // Reset UIManager to default after using
                UIManager.put("OptionPane.background", null);
                UIManager.put("Panel.background", null);

                return;
            }
        }

        // Increase font size for "Donor Not Found!" dialog
        UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.BOLD, 18));
        JOptionPane.showMessageDialog(userFrame, "Donor Not Found ! ");

        // Reset UIManager to default after using
        UIManager.put("OptionPane.messageFont", null);
    }


    //update details method
    private void updateDetails() {
        // Set UIManager properties for dialog appearance
        UIManager.put("OptionPane.background", Color.darkGray);
        UIManager.put("Panel.background", Color.cyan);
        UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.BOLD, 18));

        // Prompt the user to enter their name to update details
        String name = JOptionPane.showInputDialog(userFrame, "Enter your name to update details:    ");
        ArrayList<String> donors = new ArrayList<>(java.util.Arrays.asList(FileHandler.readFromFile("donors.txt").split("\n")));
        boolean found = false;

        for (String donor : donors) {
            if (donor.startsWith(name + ",")) {
                found = true;
                break;
            }
        }

        if (found) {
            // Create input fields for the updated details
            JTextField updatedNameField = new JTextField(20);
            JTextField updatedBloodTypeField = new JTextField(10);
            JTextField updatedLastDonationDateField = new JTextField(10);
            JTextField updatedAddressField = new JTextField(30);
            JTextField updatedContactNumberField = new JTextField(10);
            JTextField updatedEmailField = new JTextField(20);
            JTextField updatedAgeField = new JTextField(5);

            // Create a panel to arrange input fields
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(7, 2, 10, 10));
            panel.setBackground(Color.cyan);
            panel.setPreferredSize(new Dimension(600, 370));

            // Add labels and input fields to the panel
            panel.add(new JLabel("    Name:"));
            panel.add(updatedNameField);
            panel.add(new JLabel("    Blood Type (A+, A-, B+...):"));
            panel.add(updatedBloodTypeField);
            panel.add(new JLabel("    Last Donation Date (yyyy-mm-dd):"));
            panel.add(updatedLastDonationDateField);
            panel.add(new JLabel("    Address:"));
            panel.add(updatedAddressField);
            panel.add(new JLabel("    Contact Number:"));
            panel.add(updatedContactNumberField);
            panel.add(new JLabel("    Email:"));
            panel.add(updatedEmailField);
            panel.add(new JLabel("    Age:"));
            panel.add(updatedAgeField);

            // Display the dialog to update details
            int result = JOptionPane.showConfirmDialog(userFrame, panel, "Update Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);

            if (result == JOptionPane.OK_OPTION) {
                // Retrieve updated details from input fields
                String updatedName = updatedNameField.getText();
                String updatedBloodType = updatedBloodTypeField.getText();
                String updatedLastDonationDate = updatedLastDonationDateField.getText();
                String updatedAddress = updatedAddressField.getText();
                String updatedContactNumber = updatedContactNumberField.getText();
                String updatedEmail = updatedEmailField.getText();
                String updatedAge = updatedAgeField.getText();

                // Prepare updated donor details including age
                String updatedDetails = String.join(",", updatedName, updatedBloodType, updatedLastDonationDate, updatedAddress, updatedContactNumber, updatedEmail, updatedAge);

                for (int i = 0; i < donors.size(); i++) {
                    if (donors.get(i).startsWith(name + ",")) {
                        donors.set(i, updatedDetails);
                        break;
                    }
                }

                // Clear and rewrite the donors file with updated data
                FileHandler.clearFile("donors.txt");
                for (String donor : donors) {
                    FileHandler.writeToFile("donors.txt", donor);
                }

                // Display the updated details to the user
                JTextArea textArea = new JTextArea(11, 30);
                textArea.setText("\n---- UPDATED DETAILS ----\n\n\n" +
                        "    Name: " + updatedName + "\n\n" +
                        "    Blood Type: " + updatedBloodType + "\n\n" +
                        "    Last Donation Date: " + updatedLastDonationDate + "\n\n" +
                        "    Address: " + updatedAddress + "\n\n" +
                        "    Contact Number: " + updatedContactNumber + "\n\n" +
                        "    Email: " + updatedEmail + "\n\n" +
                        "    Age: " + updatedAge);

                // Customize the text area for display
                textArea.setWrapStyleWord(true);
                textArea.setLineWrap(true);
                textArea.setEditable(false);
                textArea.setBackground(Color.cyan);
                textArea.setForeground(Color.BLACK);
                textArea.setFont(new Font("Tahoma", Font.BOLD, 15));

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(600, 440));  // Adjusted for added age

                // Show the updated profile details to the user
                JOptionPane.showMessageDialog(userFrame, scrollPane, "Updated Profile Details", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            // Donor not found
            JOptionPane.showMessageDialog(userFrame, "Donor not found !  ");
        }

        // Reset UIManager to default after using
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageFont", null);
    }


    //Search blood method
    private void searchBlood() {
        // Set UIManager properties for dialog appearance
        UIManager.put("OptionPane.background", Color.darkGray);
        UIManager.put("Panel.background", Color.cyan);
        Font inputFont = new Font("Tahoma", Font.BOLD, 18);
        UIManager.put("OptionPane.messageFont", inputFont);

        // Prompt the user to enter the blood type to search
        String bloodType = JOptionPane.showInputDialog(userFrame, "Enter blood type to search (A+, A-, B+...):    ");

        if (bloodType == null || bloodType.trim().isEmpty()) {
            UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.BOLD, 18));
            JOptionPane.showMessageDialog(userFrame, "You didn't enter any blood type!");
            return;
        }

        // Get the blood stock count from the BloodInventory class
        BloodInventory inventory = BloodInventory.getInstance();
        int count = inventory.getStockForBloodType(bloodType.trim());

        // Prepare the message for the user
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setBackground(Color.cyan);
        textArea.setForeground(Color.BLACK);
        textArea.setFont(new Font("Tahoma", Font.BOLD, 16));

        if (count != -1) {
            textArea.setText("\n----- BLOOD INVENTORY -----\n\n\n" +
                    "    Blood Type :  " + bloodType + "\n\n" +
                    "    Count :  " + count + " Units\n\n\n" +
                    "    Contact the blood bank manager if there\n    is an urgent need for blood !!\n    Name : S.M. Tahmid Abir\n    Phone No : 01905055664\n\n");
        } else {
            textArea.setText("\n\n\n\n\n\n\n                           !!! Blood type not found in inventory !!!   ");
        }

        // Customize the text area for display
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        // Show the blood inventory information to the user
        JOptionPane.showMessageDialog(userFrame, scrollPane, "Blood Inventory", JOptionPane.INFORMATION_MESSAGE);

        // Reset UIManager to default after using
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageFont", null);
    }


    //Request for blood method
    private void requestBlood() {
        // Set UIManager properties for dialog appearance
        UIManager.put("OptionPane.background", Color.darkGray); // Set background color for the option pane
        UIManager.put("Panel.background", Color.cyan); // Set background color for panels
        Font inputFont = new Font("Tahoma", Font.BOLD, 18); // Create a font for input fields
        Font labelFont = new Font("Tahoma", Font.PLAIN, 18); // Create a font for labels
        UIManager.put("OptionPane.messageFont", inputFont); // Set the font for the message

        // Create input fields for blood type and location
        JTextField bloodTypeField = new JTextField(15); // Create a text field for blood type
        bloodTypeField.setFont(inputFont); // Set the font for the blood type field
        JTextField locationField = new JTextField(25); // Create a text field for location
        locationField.setFont(inputFont); // Set the font for the location field

        // Create panels to organize input fields and labels
        JPanel panel = new JPanel(); // Create a panel to hold input fields and labels
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Set the layout to vertical
        panel.setBackground(Color.cyan); // Set the background color of the panel

        JPanel bloodTypePanel = new JPanel(); // Create a panel for blood type
        bloodTypePanel.setBackground(Color.cyan); // Set the background color for the blood type panel
        bloodTypePanel.add(new JLabel("Blood Type (e.g., A+):")).setFont(labelFont); // Add a label for blood type
        bloodTypePanel.add(bloodTypeField); // Add the blood type input field
        panel.add(bloodTypePanel); // Add the blood type panel to the main panel

        JPanel locationPanel = new JPanel(); // Create a panel for location
        locationPanel.setBackground(Color.cyan); // Set the background color for the location panel
        locationPanel.add(new JLabel("Location:")).setFont(labelFont); // Add a label for location
        locationPanel.add(locationField); // Add the location input field
        panel.add(locationPanel); // Add the location panel to the main panel

        // Display the blood request dialog
        int result = JOptionPane.showConfirmDialog(userFrame, panel, "Blood Request", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null); // Show the input dialog

        if (result == JOptionPane.OK_OPTION) {
            // Retrieve blood type and location from input fields
            String bloodType = bloodTypeField.getText().trim();
            String location = locationField.getText().trim();

            if (!bloodType.isEmpty() && !location.isEmpty()) {
                ArrayList<String> donors = new ArrayList<>(java.util.Arrays.asList(FileHandler.readFromFile("donors.txt").split("\n"))); // Read donors from a file
                StringBuilder matchedDonors = new StringBuilder(); // Create a string builder to store matched donors

                for (String donor : donors) {
                    String[] details = donor.split(","); // Split donor information
                    if (details.length >= 5 && details[1].equalsIgnoreCase(bloodType) && details[3].equalsIgnoreCase(location)) {
                        // Append donor information to the matchedDonors StringBuilder
                        matchedDonors.append("<b>Name:</b> ").append(details[0])
                                .append("<br><b>Location:</b> ").append(details[3])
                                .append("<br><b>Phone No:</b> ").append(details[4])
                                .append("<br><b>Last Donate Date:</b> ").append(details[2])
                                .append("<br><hr>");
                    }
                }

                // Create a JTextPane to display matched donors as HTML
                JTextPane textPane = new JTextPane();
                textPane.setContentType("text/html"); // Set content type to HTML
                textPane.setBackground(Color.cyan); // Set the background color
                textPane.setForeground(Color.BLACK); // Set the text color
                textPane.setFont(new Font("Tahoma", Font.BOLD, 15)); // Set the font

                if (!matchedDonors.isEmpty()) {
                    textPane.setText("<html><body><h2>Matched Donors:</h2>" + matchedDonors.toString() + "</body></html>"); // Set the text content with matched donors
                } else {
                    textPane.setText("<html><body><h2>No donors found for the given blood type in the specified location.</h2></body></html>"); // Set a message for no matching donors
                }
                textPane.setEditable(false); // Make the text pane non-editable

                JScrollPane scrollPane = new JScrollPane(textPane); // Create a scroll pane for the text pane
                scrollPane.setPreferredSize(new Dimension(500, 400)); // Set the preferred size

                // Show the matched donors to the user
                JOptionPane.showMessageDialog(userFrame, scrollPane, "Matched Donors", JOptionPane.INFORMATION_MESSAGE); // Show the result dialog
            } else {
                // Show an error message if blood type or location is not provided
                JOptionPane.showMessageDialog(userFrame, "Please provide all details.", "Error", JOptionPane.ERROR_MESSAGE); // Show an error message
            }
        }

        // Reset UIManager to default after using
        UIManager.put("OptionPane.background", null); // Reset option pane background
        UIManager.put("Panel.background", null); // Reset panel background
        UIManager.put("OptionPane.messageFont", null); // Reset message font
    }



    // View available Donors method
    private void viewAvailableDonors() {
        try {
            // Setup UIManager for colors and font
            UIManager.put("OptionPane.background", Color.darkGray); // Set background color for the option pane
            UIManager.put("Panel.background", Color.cyan); // Set background color for panels
            Font boldFont = new Font("Arial", Font.BOLD, 14); // Create a bold font
            UIManager.put("Table.font", boldFont); // Set font for JTable
            UIManager.put("Label.font", boldFont); // Set font for labels

            // Read data and filter donors
            String data = FileHandler.readFromFile("donors.txt"); // Read data from a file
            if (data.isEmpty()) return; // Return if no data is available

            String[] donors = data.split("\n"); // Split data into individual donors
            String[][] donorData = new String[donors.length][3]; // Create a 2D array to store donor data
            int index = 0;

            for (String donor : donors) {
                String[] details = donor.split(","); // Split donor details
                if (details.length >= 5) {
                    LocalDate lastDonationDate;
                    try {
                        lastDonationDate = LocalDate.parse(details[2].trim()); // Parse the last donation date
                    } catch (Exception e) {
                        continue; // Skip to the next donor if date parsing fails
                    }

                    LocalDate currentDate = LocalDate.now(); // Get the current date
                    long monthsBetween = ChronoUnit.MONTHS.between(lastDonationDate, currentDate); // Calculate months since last donation

                    if (monthsBetween >= 4) {
                        donorData[index][0] = details[0].trim(); // Set the donor's name
                        donorData[index][1] = details[1].trim(); // Set the blood type
                        donorData[index][2] = details[4].trim(); // Set the phone number
                        index++;
                    }
                }
            }

            donorData = Arrays.copyOf(donorData, index); // Resize the donor data array

            // Define column names for the table
            String[] columnNames = {"Name", "Blood Type", "Phone No"};

            // Create a JTable with donor data
            JTable table = new JTable(donorData, columnNames); // Create a table with data and column names
            table.setFont(boldFont); // Set the font for the table
            table.setRowHeight(25); // Set the row height
            table.setEnabled(false); // Disable table editing

            // Create a scrollable pane for the table
            JScrollPane scrollPane = new JScrollPane(table); // Create a scroll pane for the table
            scrollPane.setPreferredSize(new Dimension(700, 400)); // Set the preferred size

            // Create a top panel with a headline label
            JPanel topPanel = new JPanel(new BorderLayout()); // Create a panel with BorderLayout
            JLabel headline = new JLabel("The Donors Who Had Not Donated Blood In Last Four Months ", SwingConstants.CENTER); // Create a headline label
            headline.setFont(boldFont); // Set the font for the headline label
            topPanel.add(headline, BorderLayout.NORTH); // Add the headline label to the top of the panel
            topPanel.add(scrollPane, BorderLayout.CENTER); // Add the scroll pane to the center of the panel

            // Display the available donors using a dialog
            JOptionPane.showMessageDialog(
                    userFrame, // Parent frame
                    topPanel, // Content to display
                    "Available Donors", // Dialog title
                    JOptionPane.INFORMATION_MESSAGE // Message type
            );

            // Reset UIManager to default after using
            UIManager.put("OptionPane.background", null); // Reset option pane background
            UIManager.put("Panel.background", null); // Reset panel background
            UIManager.put("Table.font", null); // Reset table font
            UIManager.put("Label.font", null); // Reset label font
        } catch (Exception e) {
            e.printStackTrace(); // Print the error for debugging purposes
            JOptionPane.showMessageDialog(
                    userFrame, // Parent frame
                    "An error occurred. Please check your data and try again.", // Error message
                    "Error", // Dialog title
                    JOptionPane.ERROR_MESSAGE // Error message type
            );
        }
    }



    //view blood stock method
    private void viewBloodStock() {
        try {
            // Setting up UIManager for color and font styling
            UIManager.put("OptionPane.background", Color.cyan); // Set option pane background color
            UIManager.put("Panel.background", Color.cyan); // Set panel background color
            Font boldFont = new Font("Arial", Font.BOLD, 16); // Create a bold font
            UIManager.put("Table.font", boldFont); // Set font for JTable
            UIManager.put("Label.font", boldFont); // Set font for labels

            // Getting the data from the BloodInventory class
            BloodInventory bloodInventory = BloodInventory.getInstance(); // Retrieve BloodInventory instance
            String[][] bloodData = {
                    {"A+", String.valueOf(bloodInventory.getStockForBloodType("A+"))},
                    {"A-", String.valueOf(bloodInventory.getStockForBloodType("A-"))},
                    {"B+", String.valueOf(bloodInventory.getStockForBloodType("B+"))},
                    {"B-", String.valueOf(bloodInventory.getStockForBloodType("B-"))},
                    {"AB+", String.valueOf(bloodInventory.getStockForBloodType("AB+"))},
                    {"AB-", String.valueOf(bloodInventory.getStockForBloodType("AB-"))},
                    {"O+", String.valueOf(bloodInventory.getStockForBloodType("O+"))},
                    {"O-", String.valueOf(bloodInventory.getStockForBloodType("O-"))}
            };

            // Creating a table and setting its properties
            String[] columnNames = {"Blood Type", "Count"};
            JTable table = new JTable(bloodData, columnNames); // Create a JTable with data and column names
            table.setFont(boldFont); // Set the font for the table
            table.setRowHeight(25); // Set the row height
            table.setEnabled(false); // Disable table editing

            // Colorful table
            table.setBackground(Color.pink); // Set the background color of the table
            table.setForeground(Color.BLUE); // Set the text color of the table
            table.getTableHeader().setBackground(Color.RED); // Set the background color of the table header
            table.getTableHeader().setForeground(Color.BLACK); // Set the text color of the table header

            // Wrapping the table with a scroll pane
            JScrollPane scrollPane = new JScrollPane(table); // Create a scroll pane for the table
            scrollPane.setPreferredSize(new Dimension(480, 300)); // Set the preferred size of the scroll pane

            // Creating a top panel for the headline message
            JPanel topPanel = new JPanel(new BorderLayout()); // Create a panel with BorderLayout
            JLabel headline = new JLabel("Blood Stock Report", SwingConstants.CENTER); // Create a headline label
            headline.setFont(boldFont); // Set the font for the headline label
            topPanel.add(headline, BorderLayout.NORTH); // Add the headline label to the top of the panel
            topPanel.add(scrollPane, BorderLayout.CENTER); // Add the scroll pane to the center of the panel

            // Using JDialog for better control over the title bar
            JDialog dialog = new JDialog(); // Create a dialog
            dialog.setTitle("Blood Stock Report"); // Set the dialog title
            dialog.setFont(new Font("Arial", Font.BOLD, 20)); // Set the font for the dialog
            dialog.setModal(true); // Make the dialog modal
            dialog.add(topPanel); // Add the top panel to the dialog
            dialog.pack(); // Pack the dialog to fit its components
            dialog.setLocationRelativeTo(null); // Center the dialog on the screen
            dialog.setVisible(true); // Make the dialog visible

            // Reset UIManager to default after using
            UIManager.put("OptionPane.background", null); // Reset option pane background
            UIManager.put("Panel.background", null); // Reset panel background
            UIManager.put("Table.font", null); // Reset table font
            UIManager.put("Label.font", null); // Reset label font
        } catch (Exception e) {
            e.printStackTrace(); // Print any exception for debugging purposes
            JOptionPane.showMessageDialog(
                    null,
                    "An error occurred. Please check your data and try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }



    //Age list method
    private void ageList() {
        try {
            // Set up UIManager for colors and fonts
            UIManager.put("OptionPane.background", Color.darkGray); // Set the option pane background color
            UIManager.put("Panel.background", Color.cyan); // Set the panel background color
            UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.BOLD, 24)); // Increase the font size

            // Age ranges
            int[] lowerBounds = {18, 31, 41, 51, 61, 71, 81};
            int[] upperBounds = {30, 40, 50, 60, 70, 80, 100};
            int[] counts = new int[lowerBounds.length];

            ArrayList<String> donors = new ArrayList<String>(java.util.Arrays.asList(FileHandler.readFromFile("donors.txt").split("\n")));

            // Count donors in age ranges
            for (String donor : donors) {
                String[] details = donor.split(",");
                int age = Integer.parseInt(details[6].trim());
                for (int i = 0; i < lowerBounds.length; i++) {
                    if (age >= lowerBounds[i] && age <= upperBounds[i]) {
                        counts[i]++;
                        break;
                    }
                }
            }

            // Creating table with increased row height and font
            String[] columnNames = {"Age Range", "Number of Donors"};
            Object[][] data = new Object[lowerBounds.length][2];
            for (int i = 0; i < lowerBounds.length; i++) {
                data[i][0] = lowerBounds[i] + "-" + upperBounds[i];
                data[i][1] = counts[i];
            }

            JTable table = new JTable(data, columnNames);
            table.setFont(new Font("Tahoma", Font.BOLD, 18)); // Set table font size and make it bold
            table.setRowHeight(30); // Increase row height
            table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 20)); // Set table header font size and make it bold
            table.getTableHeader().setPreferredSize(new Dimension(450, 40)); // Increase header height
            table.setPreferredScrollableViewportSize(new Dimension(500, 215)); // Increase table size
            table.setFillsViewportHeight(true);
            JScrollPane scrollPane = new JScrollPane(table);

            // Displaying the table with increased panel size
            JPanel panel = new JPanel(new BorderLayout()); // Create a panel with BorderLayout
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add empty border for spacing
            panel.add(scrollPane, BorderLayout.CENTER);

            JOptionPane.showMessageDialog(userFrame, panel, "Donors Age List", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(userFrame, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Print any exceptions for debugging purposes
        } finally {
            // Reset UIManager to default settings
            UIManager.put("OptionPane.background", null); // Reset option pane background
            UIManager.put("Panel.background", null); // Reset panel background
            UIManager.put("OptionPane.messageFont", null); // Reset message font
        }
    }


    //All donors detail method
    private void allDonors() {
        try {
            // Set up UIManager for colors
            UIManager.put("OptionPane.background", Color.darkGray); // Set the option pane background color
            UIManager.put("Panel.background", Color.cyan); // Set the panel background color
            Font boldFont = new Font("Arial", Font.BOLD, 14); // Create a bold font
            UIManager.put("Table.font", boldFont); // Set font for JTable
            UIManager.put("Label.font", boldFont); // Set font for labels

            // Read data from donors.txt
            String allDonorsData = FileHandler.readFromFile("donors.txt"); // Read data from the "donors.txt" file
            String[] donorLines = allDonorsData.split("\n"); // Split data into individual donor lines

            // Define the column names for your JTable
            String[] columnNames = {"Name", "Age", "Blood Group", "Address"};

            // Create a list to store the donor data
            ArrayList<ArrayList<String>> data = new ArrayList<>();

            // Extract and process donor data
            for (String donorData : donorLines) {
                String[] donorDetails = donorData.split(","); // Split donor details
                if (donorDetails.length == 7) {  // Ensure data is complete
                    ArrayList<String> rowData = new ArrayList<>();
                    rowData.add(donorDetails[0].trim()); // Name
                    rowData.add(donorDetails[6].trim()); // Age
                    rowData.add(donorDetails[1].trim()); // Blood Group
                    rowData.add(donorDetails[3].trim()); // Address
                    data.add(rowData); // Add the donor data to the list
                }
            }

            // Create a DefaultTableModel with the column names
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

            // Add the data from the ArrayList to the table model
            for (ArrayList<String> rowData : data) {
                tableModel.addRow(rowData.toArray()); // Add rows to the table model
            }

            // Create a JTable with the populated table model
            JTable table = new JTable(tableModel); // Create a JTable with the table model
            table.setFont(boldFont); // Set the font for the table
            table.setRowHeight(25); // Set the row height
            table.setEnabled(false); // Disable editing

            JScrollPane scrollPane = new JScrollPane(table); // Create a scroll pane for the table
            scrollPane.setPreferredSize(new Dimension(700, 400)); // Set the preferred size

            // Create a top panel for the headline message
            JPanel topPanel = new JPanel(new BorderLayout()); // Create a panel with BorderLayout
            JLabel headline = new JLabel("List of All Donors", SwingConstants.CENTER); // Create a headline label
            headline.setFont(boldFont); // Set the font for the headline label
            topPanel.add(headline, BorderLayout.NORTH); // Add the headline label to the top of the panel
            topPanel.add(scrollPane, BorderLayout.CENTER); // Add the scroll pane to the center of the panel

            // Output in a styled dialog box
            JOptionPane.showMessageDialog(userFrame, topPanel, "All Donors", JOptionPane.INFORMATION_MESSAGE);

            // Reset UIManager to default after using
            UIManager.put("OptionPane.background", null); // Reset option pane background
            UIManager.put("Panel.background", null); // Reset panel background
            UIManager.put("Table.font", null); // Reset table font
            UIManager.put("Label.font", null); // Reset label font
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions for debugging purposes
            JOptionPane.showMessageDialog(userFrame, "An error occurred. Please check your data and try again.", "Error", JOptionPane.ERROR_MESSAGE); // Display an error message
        }
    }


    public static void userLogin() {
        // Entry point for the User class
        new User(); // Display the user dashboard
    }


}
