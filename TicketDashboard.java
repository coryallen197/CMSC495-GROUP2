package Week8;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class TicketDashboard extends JFrame {

    private JTable ticketTable;
    private DefaultTableModel tableModel;

    public TicketDashboard() {

        // Window settings
        setTitle("Helpdesk - Ticket Dashboard");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel
        JPanel panel = new JPanel(new BorderLayout());

        // Column names
        String[] columnNames = {
                "Ticket ID",
                "Title",
                "Category",
                "Priority",
                "Status",
                "Created Date"
        };

        // Table model
        tableModel = new DefaultTableModel(columnNames, 0) {

            // Prevent users from editing table cells
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Create table
        ticketTable = new JTable(tableModel);
        
        ticketTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {

                    int selectedRow =
                            ticketTable.getSelectedRow();

                    if (selectedRow != -1) {

                        int ticketId =
                                (int) tableModel.getValueAt(
                                        selectedRow,
                                        0
                                );

                        new ticketDetails(ticketId);
                    }
                }
            }
        });
        

        // Add scrolling
        JScrollPane scrollPane = new JScrollPane(ticketTable);

        // Add table to panel
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();

        JButton refreshButton = new JButton("Refresh");

        JButton createTicketButton =
                new JButton("Create New Ticket");

        buttonPanel.add(refreshButton);
        buttonPanel.add(createTicketButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Refresh button
        refreshButton.addActionListener(e -> loadTickets());

        // Create Ticket button
        createTicketButton.addActionListener(e -> {

            new CreateTicketForm();

        });

        // Add panel
        add(panel);

        // Load tickets from database
        loadTickets();

        // Display window
        setVisible(true);
    }


    /**
     * Loads tickets from MySQL into the JTable.
     */
    private void loadTickets() {

        // Remove existing rows
        tableModel.setRowCount(0);

        String sql =
                "SELECT ticket_id, title, category, " +
                "priority, status, created_at " +
                "FROM tickets " +
                "ORDER BY created_at DESC";
        
        
        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet resultSet =
                     statement.executeQuery()) {

            // Read each ticket
            while (resultSet.next()) {

                int ticketId =
                        resultSet.getInt("ticket_id");

              //  String title =
              //          resultSet.getString("ticket_title");

                String category =
                        resultSet.getString("category");

                String priority =
                        resultSet.getString("priority");

                String status =
                        resultSet.getString("status");

                java.sql.Timestamp createdDate =
                        resultSet.getTimestamp("created_at");


                // Add ticket to table
                tableModel.addRow(new Object[] {
                        ticketId,
                      //  title,
                        category,
                        priority,
                        status,
                        createdDate
                });
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading tickets:\n" +
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                TicketDashboard::new
        );
    }
}