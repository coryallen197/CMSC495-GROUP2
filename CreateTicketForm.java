package Week8;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTicketForm extends JFrame {

    private JTextField titleField;
    private JTextArea descriptionArea;
    private JComboBox<String> categoryCombo;
    private JComboBox<String> priorityCombo;
    private JButton submitButton;


    // ==========================================
    // CONSTRUCTOR - CREATES THE SWING FORM
    // ==========================================

    public CreateTicketForm() {

        setTitle("Helpdesk - Create Ticket");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        // Main panel
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        // ==========================================
        // TICKET TITLE
        // ==========================================

        gbc.gridx = 0;
        gbc.gridy = 0;

        panel.add(new JLabel("Ticket Title:"), gbc);


        gbc.gridx = 1;

        titleField = new JTextField(30);

        panel.add(titleField, gbc);


        // ==========================================
        // PROBLEM DESCRIPTION
        // ==========================================

        gbc.gridx = 0;
        gbc.gridy = 1;

        gbc.anchor = GridBagConstraints.NORTHWEST;

        panel.add(
                new JLabel("Describe Your Problem:"),
                gbc
        );


        gbc.gridx = 1;

        descriptionArea = new JTextArea(8, 30);

        descriptionArea.setLineWrap(true);

        descriptionArea.setWrapStyleWord(true);


        JScrollPane scrollPane =
                new JScrollPane(descriptionArea);

        panel.add(scrollPane, gbc);


        // ==========================================
        // CATEGORY
        // ==========================================

        gbc.gridx = 0;
        gbc.gridy = 2;

        gbc.anchor = GridBagConstraints.WEST;

        panel.add(new JLabel("Category:"), gbc);


        gbc.gridx = 1;

        String[] categories = {
                "Hardware",
                "Software",
                "Network",
                "Account/Login",
                "Other"
        };

        categoryCombo =
                new JComboBox<>(categories);

        panel.add(categoryCombo, gbc);


        // ==========================================
        // PRIORITY
        // ==========================================

        gbc.gridx = 0;
        gbc.gridy = 3;

        panel.add(new JLabel("Priority:"), gbc);


        gbc.gridx = 1;

        String[] priorities = {
                "Low",
                "Medium",
                "High",
                "Critical"
        };

        priorityCombo =
                new JComboBox<>(priorities);

        panel.add(priorityCombo, gbc);


        // ==========================================
        // SUBMIT BUTTON
        // ==========================================

        gbc.gridx = 1;
        gbc.gridy = 4;

        submitButton =
                new JButton("Submit Ticket");

        panel.add(submitButton, gbc);


        // IMPORTANT!
        // Connect the button to submitTicket()
        submitButton.addActionListener(
                e -> submitTicket()
        );


        // Add panel to window
        add(panel);


        // Show window
        setVisible(true);
    }


    // ==========================================
    // SAVE TICKET TO DATABASE
    // ==========================================

    private void submitTicket() {

        String title =
                titleField.getText().trim();

        String description =
                descriptionArea.getText().trim();

        String category =
                (String) categoryCombo.getSelectedItem();

        String priority =
                (String) priorityCombo.getSelectedItem();


        // Validate fields
        if (title.isEmpty() || description.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a Ticket Title and Description.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Temporary user for testing
        int userId = 1;


        String ticketSQL =
                "INSERT INTO tickets " +
                "(user_id, title, ticket_title, description, " +
                "category, priority, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";


        String historySQL =
                "INSERT INTO ticket_history " +
                "(ticket_id, action, old_status, new_status, changed_by) " +
                "VALUES (?, ?, ?, ?, ?)";


        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            // Start transaction
            connection.setAutoCommit(false);


            try {

                // ==========================================
                // INSERT TICKET
                // ==========================================

                try (PreparedStatement ticketStatement =
                             connection.prepareStatement(
                                     ticketSQL,
                                     Statement.RETURN_GENERATED_KEYS
                             )) {

                    ticketStatement.setInt(1, userId);
                    ticketStatement.setString(2, title);
                    ticketStatement.setString(3, title);
                    ticketStatement.setString(4, description);
                    ticketStatement.setString(5, category);
                    ticketStatement.setString(6, priority);
                    ticketStatement.setString(7, "Open");


                    ticketStatement.executeUpdate();


                    // Get generated Ticket ID
                    try (ResultSet generatedKeys =
                                 ticketStatement.getGeneratedKeys()) {

                        if (!generatedKeys.next()) {

                            throw new SQLException(
                                    "Unable to retrieve Ticket ID."
                            );
                        }


                        int ticketId =
                                generatedKeys.getInt(1);


                        // ==========================================
                        // INSERT HISTORY
                        // ==========================================

                        try (PreparedStatement historyStatement =
                                     connection.prepareStatement(
                                             historySQL
                                     )) {

                            historyStatement.setInt(
                                    1,
                                    ticketId
                            );

                            historyStatement.setString(
                                    2,
                                    "Ticket Created"
                            );

                            historyStatement.setNull(
                                    3,
                                    java.sql.Types.VARCHAR
                            );

                            historyStatement.setString(
                                    4,
                                    "Open"
                            );

                            historyStatement.setString(
                                    5,
                                    "System"
                            );


                            historyStatement.executeUpdate();
                        }


                        // Commit both database changes
                        connection.commit();


                        JOptionPane.showMessageDialog(
                                this,
                                "Ticket #" + ticketId +
                                " created successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );


                        // Clear the form
                        titleField.setText("");

                        descriptionArea.setText("");

                        categoryCombo.setSelectedIndex(0);

                        priorityCombo.setSelectedIndex(0);
                    }
                }


            } catch (SQLException e) {

                // Roll back both inserts
                connection.rollback();

                throw e;
            }


        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error:\n" +
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }


    // ==========================================
    // MAIN METHOD
    // ==========================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                CreateTicketForm::new
        );
    }
}