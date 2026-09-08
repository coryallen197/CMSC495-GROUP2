package Week8;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ticketDetails extends JFrame {

    private int ticketId;

    private JTextField titleField;
    private JTextArea descriptionArea;
    private JTextField categoryField;
    private JTextField priorityField;
    private JComboBox<String> statusCombo;

    private String currentStatus;


    public ticketDetails(int ticketId) {

        this.ticketId = ticketId;

        setTitle("Helpdesk - Ticket Details #" + ticketId);
        setSize(650, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        // ==============================
        // Ticket ID
        // ==============================

        gbc.gridx = 0;
        gbc.gridy = 0;

        panel.add(new JLabel("Ticket ID:"), gbc);

        gbc.gridx = 1;

        JTextField ticketIdField =
                new JTextField(String.valueOf(ticketId));

        ticketIdField.setEditable(false);

        panel.add(ticketIdField, gbc);


        // ==============================
        // Title
        // ==============================

        gbc.gridx = 0;
        gbc.gridy = 1;

        panel.add(new JLabel("Title:"), gbc);

        gbc.gridx = 1;

        titleField = new JTextField(30);

        titleField.setEditable(false);

        panel.add(titleField, gbc);


        // ==============================
        // Description
        // ==============================

        gbc.gridx = 0;
        gbc.gridy = 2;

        gbc.anchor = GridBagConstraints.NORTHWEST;

        panel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;

        descriptionArea = new JTextArea(8, 30);

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);

        JScrollPane scrollPane =
                new JScrollPane(descriptionArea);

        panel.add(scrollPane, gbc);


        // ==============================
        // Category
        // ==============================

        gbc.gridx = 0;
        gbc.gridy = 3;

        gbc.anchor = GridBagConstraints.WEST;

        panel.add(new JLabel("Category:"), gbc);

        gbc.gridx = 1;

        categoryField = new JTextField(30);

        categoryField.setEditable(false);

        panel.add(categoryField, gbc);


        // ==============================
        // Priority
        // ==============================

        gbc.gridx = 0;
        gbc.gridy = 4;

        panel.add(new JLabel("Priority:"), gbc);

        gbc.gridx = 1;

        priorityField = new JTextField(30);

        priorityField.setEditable(false);

        panel.add(priorityField, gbc);


        // ==============================
        // Status
        // ==============================

        gbc.gridx = 0;
        gbc.gridy = 5;

        panel.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1;

        String[] statuses = {
                "Open",
                "In Progress",
                "Closed"
        };

        statusCombo = new JComboBox<>(statuses);

        panel.add(statusCombo, gbc);


        // ==============================
        // SAVE BUTTON
        // ==============================

        gbc.gridx = 1;
        gbc.gridy = 6;

        JButton saveButton =
                new JButton("Update Status");

        panel.add(saveButton, gbc);


        saveButton.addActionListener(
                e -> updateStatus()
        );


        add(panel);

        // Load ticket information
        loadTicket();

        setVisible(true);
    }


    // ==========================================
    // LOAD TICKET FROM DATABASE
    // ==========================================

    private void loadTicket() {

        String sql =
                "SELECT title, description, category, " +
                "priority, status " +
                "FROM tickets " +
                "WHERE ticket_id = ?";


        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {


            statement.setInt(1, ticketId);


            try (ResultSet resultSet =
                         statement.executeQuery()) {


                if (resultSet.next()) {

                    titleField.setText(
                            resultSet.getString("title")
                    );

                    descriptionArea.setText(
                            resultSet.getString("description")
                    );

                    categoryField.setText(
                            resultSet.getString("category")
                    );

                    priorityField.setText(
                            resultSet.getString("priority")
                    );


                    currentStatus =
                            resultSet.getString("status");


                    statusCombo.setSelectedItem(
                            currentStatus
                    );
                }
            }


        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading ticket:\n" +
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }


    // ==========================================
    // UPDATE TICKET STATUS
    // ==========================================

    private void updateStatus() {

        String newStatus =
                (String) statusCombo.getSelectedItem();


        // Don't update if nothing changed
        if (newStatus.equals(currentStatus)) {

            JOptionPane.showMessageDialog(
                    this,
                    "The status has not changed."
            );

            return;
        }


        String updateSQL =
                "UPDATE tickets " +
                "SET status = ? " +
                "WHERE ticket_id = ?";


        String historySQL =
                "INSERT INTO ticket_history " +
                "(ticket_id, action, old_status, new_status, changed_by) " +
                "VALUES (?, ?, ?, ?, ?)";


        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);


            try {

                // ==========================
                // UPDATE TICKET STATUS
                // ==========================

                try (PreparedStatement updateStatement =
                             connection.prepareStatement(updateSQL)) {

                    updateStatement.setString(
                            1,
                            newStatus
                    );

                    updateStatement.setInt(
                            2,
                            ticketId
                    );

                    updateStatement.executeUpdate();
                }


                // ==========================
                // ADD HISTORY RECORD
                // ==========================

                try (PreparedStatement historyStatement =
                             connection.prepareStatement(historySQL)) {

                    historyStatement.setInt(
                            1,
                            ticketId
                    );

                    historyStatement.setString(
                            2,
                            "Status Changed"
                    );

                    historyStatement.setString(
                            3,
                            currentStatus
                    );

                    historyStatement.setString(
                            4,
                            newStatus
                    );

                    historyStatement.setString(
                            5,
                            "System"
                    );

                    historyStatement.executeUpdate();
                }


                // Save both changes
                connection.commit();


                currentStatus = newStatus;


                JOptionPane.showMessageDialog(
                        this,
                        "Ticket status updated successfully!"
                );


            } catch (SQLException e) {

                connection.rollback();

                throw e;
            }


        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error updating ticket:\n" +
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }
}