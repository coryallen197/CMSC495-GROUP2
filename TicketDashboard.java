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

        JButton aiTriageButton =
                new JButton("AI Triage");

        buttonPanel.add(refreshButton);
        buttonPanel.add(createTicketButton);
        buttonPanel.add(aiTriageButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Refresh button
        refreshButton.addActionListener(e -> loadTickets());

        // Create Ticket button
        createTicketButton.addActionListener(e -> {

            new CreateTicketForm();

        });

        // AI Triage button
        aiTriageButton.addActionListener(e ->
                analyzeSelectedTicketWithAI());

        // Add panel
        add(panel);

        // Load tickets from database
        loadTickets();

        // Display window
        setVisible(true);
    }

    /**
     * Runs AI triage on the selected ticket.
     */
    private void analyzeSelectedTicketWithAI() {

        int selectedRow = ticketTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a ticket from the dashboard first.",
                    "No Ticket Selected",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int ticketId =
                (int) tableModel.getValueAt(selectedRow, 0);

        String title =
                String.valueOf(tableModel.getValueAt(selectedRow, 1));

        String ticketDescription = "";

        String sql =
                "SELECT description FROM tickets WHERE ticket_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, ticketId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    ticketDescription =
                            resultSet.getString("description");
                }
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading ticket description:\n" +
                            e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
            return;
        }

        if (ticketDescription == null ||
                ticketDescription.trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "This ticket does not contain a description.",
                    "AI Triage",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        final String description = ticketDescription;

        SwingWorker<TriageResult, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected TriageResult doInBackground()
                            throws Exception {

                        TicketTriageService service =
                                new TicketTriageService();

                        return service.analyzeTicket(
                                title,
                                description
                        );
                    }

                    @Override
                    protected void done() {

                        try {

                            TriageResult result = get();

                            String message =
                                    "AI TRIAGE RESULTS\n" +
                                            "========================\n\n" +
                                            "Ticket ID: " + ticketId + "\n" +
                                            "Title: " + title + "\n\n" +
                                            "Category: " +
                                            result.getCategory() + "\n" +
                                            "Priority: " +
                                            result.getPriority() + "\n" +
                                            "Summary: " +
                                            result.getSummary() + "\n" +
                                            "Department: " +
                                            result.getDepartment();

                            JTextArea textArea =
                                    new JTextArea(message);

                            textArea.setEditable(false);
                            textArea.setLineWrap(true);
                            textArea.setWrapStyleWord(true);
                            textArea.setFont(
                                    new Font("Arial", Font.PLAIN, 14)
                            );

                            JScrollPane scrollPane =
                                    new JScrollPane(textArea);

                            scrollPane.setPreferredSize(
                                    new Dimension(550, 300)
                            );

                            JOptionPane.showMessageDialog(
                                    TicketDashboard.this,
                                    scrollPane,
                                    "AI Triage - Ticket #" + ticketId,
                                    JOptionPane.INFORMATION_MESSAGE
                            );

                        } catch (Exception e) {

                            Throwable cause =
                                    e.getCause() != null
                                            ? e.getCause()
                                            : e;

                            JOptionPane.showMessageDialog(
                                    TicketDashboard.this,
                                    "AI triage failed:\n\n" +
                                            cause.getMessage(),
                                    "AI Triage Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                            cause.printStackTrace();
                        }
                    }
                };

        worker.execute();
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

                String title =
                        resultSet.getString("title");

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
                        title,
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