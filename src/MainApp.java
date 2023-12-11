
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MainApp {
    private final JFrame frame;

    public MainApp() {
        frame = new JFrame("Blood Donation & Bank Management System");
        initializeGUI(); // Initialize the graphical user interface
    }


    private void initializeGUI() {
        // Create buttons for different options
        JButton adminButton = createStyledButton("ADMIN OPTIONS");
        JButton userButton = createStyledButton("USER OPTIONS");
        JButton aboutUsButton = createStyledButton("ABOUT US");

        // Add an action listener for the 'About Us' button
        aboutUsButton.addActionListener(e -> {
            showAboutUsDialog(); // Display the 'About Us' information
        });

        // Add an action listener for the 'Admin Options' button
        adminButton.addActionListener(e -> {
            // Set up UI manager for the admin login dialog
            UIManager.put("OptionPane.background", Color.darkGray);
            UIManager.put("Panel.background", Color.cyan);
            UIManager.put("Button.background", new Color(136, 19, 124));
            UIManager.put("Button.foreground", Color.WHITE);

            JPasswordField passwordField = new JPasswordField(20);
            passwordField.setFont(new Font("Tahoma", Font.PLAIN, 20));

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 1));
            JLabel passwordLabel = new JLabel("Enter Admin Password:");
            passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
            panel.add(passwordLabel);
            panel.add(passwordField);

            int result = JOptionPane.showOptionDialog(frame, panel, "Admin Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            if (result == JOptionPane.OK_OPTION) {
                String enteredPassword = new String(passwordField.getPassword());
                if ("admin".equals(enteredPassword)) {
                    Admin.adminLogin(); // Call admin login function
                    frame.dispose(); // Close the main frame
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect Password.");
                }
            }

            // Reset UI manager to default settings
            UIManager.put("OptionPane.background", null);
            UIManager.put("Panel.background", null);
            UIManager.put("Button.background", null);
            UIManager.put("Button.foreground", null);
        });

        // Add an action listener for the 'User Options' button
        userButton.addActionListener(e -> {
            User.userLogin(); // Call user login function
            frame.dispose(); // Close the main frame
        });

        // Load image from resources
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("picture-1.png"));
        JLabel imageLabel = new JLabel("", JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(950, 550));
        imageLabel.setIcon(imageIcon);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 8, 40));
        buttonPanel.add(adminButton);
        buttonPanel.add(userButton);
        buttonPanel.add(aboutUsButton);
        buttonPanel.setBackground(new Color(29, 106, 250));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(imageLabel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        mainPanel.setBackground(new Color(29, 106, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        frame.setContentPane(mainPanel);
        frame.setSize(900, 550);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    // Create a styled button with specific attributes
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(138, 3, 3, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Tahoma", Font.BOLD, 15));

        // Add mouse listeners to change button background on hover
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


    // Display the 'About Us' information in a dialog
    private void showAboutUsDialog() {
        UIManager.put("OptionPane.background", Color.darkGray);
        UIManager.put("Panel.background", Color.cyan);
        String aboutUsText = "\t                    ABOUT US\n"
                + "................................................................................................\n\n"
                + "Welcome to the Blood Donation & Bank Management System.\n"
                + "Our aim is to provide a platform for both donors and seekers\n"
                + "to easily manage and access blood resources. We believe in\n"
                + "saving lives and this platform is our small step towards it.\n\n"
                + "Blood donation is a selfless act that saves lives. Every few seconds, someone, somewhere, needs blood. Be it a road accident victim, a cancer patient, a pregnant woman during childbirth, or countless other scenarios — the need for blood is ubiquitous and ever-present.\n\n"
                + "Developed By:\n\n"
                + "S.M. TAHMID ABIR\n"
                + "ID: 2311015042\n"
                + "Email: sm.abir@northsouth.edu\n"
                + "Phone No: 01905055664\n\n";

        JTextArea aboutUsTextArea = new JTextArea(aboutUsText);
        aboutUsTextArea.setFont(new Font("Tahoma", Font.BOLD, 18));
        aboutUsTextArea.setWrapStyleWord(true);
        aboutUsTextArea.setLineWrap(true);
        aboutUsTextArea.setOpaque(false);
        aboutUsTextArea.setEditable(false);
        aboutUsTextArea.setFocusable(false);
        aboutUsTextArea.setBackground(UIManager.getColor("Label.background"));
        aboutUsTextArea.setBorder(UIManager.getBorder("Label.border"));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(aboutUsTextArea);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(600, 500));

        // Display the 'About Us' dialog
        JOptionPane.showMessageDialog(frame, scrollPane, "About Us", JOptionPane.PLAIN_MESSAGE);

        // Reset UI manager to default after use
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
    }


    public static void main(String[] args) {
        // Create an instance of MainApp
        SwingUtilities.invokeLater(MainApp::new);
    }
}
