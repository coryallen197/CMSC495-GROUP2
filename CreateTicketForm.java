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
    private JTextField departmentField;
    private JTextArea aiSummaryArea;
    private JButton analyzeButton;
    private JButton submitButton;


    // ==========================================
    // CONSTRUCTOR - CREATES THE SWING FORM
    // ==========================================

    public CreateTicketForm() {

        setTitle("Helpdesk - Create Ticket");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

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

        JScrollPane descriptionScrollPane =
                new JScrollPane(descriptionArea);

        panel.add(descriptionScrollPane, gbc);


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
        // DEPARTMENT
        // ==========================================

        gbc.gridx = 0;
        gbc.gridy = 4;

        panel.add(new JLabel("Department:"), gbc);

        gbc.gridx = 1;

        departmentField = new JTextField(30);
        departmentField.setEditable(false);

        panel.add(departmentField, gbc);


        // ==========================================
        // AI SUMMARY
        // ==========================================

        gbc.gridx = 0;
        gbc.gridy = 5;

        gbc.anchor = GridBagConstraints.NORTHWEST;

        panel.add(new JLabel("AI Summary:"), gbc);

        gbc.gridx = 1;

        aiSummaryArea = new JTextArea(4, 30);
        aiSummaryArea.setLineWrap(true);
        aiSummaryArea.setWrapStyleWord(true);
        aiSummaryArea.setEditable(false);

        JScrollPane summaryScrollPane =
                new JScrollPane(aiSummaryArea);

        panel.add(summaryScrollPane, gbc);


        // ==========================================
        // BUTTONS
        // ==========================================

        gbc.gridx = 1;
        gbc.gridy = 6;

        JPanel buttonPanel = new JPanel(new FlowLayout());

        analyzeButton = new JButton("AI Triage");
        submitButton = new JButton("Submit Ticket");

        buttonPanel.add(analyzeButton);
        buttonPanel.add(submitButton);

        panel.add(buttonPanel, gbc);


        // ==========================================
        // BUTTON ACTIONS
        // ==========================================

        analyzeButton.addActionListener(
                e -> analyzeTicketWithAI()
        );

        submitButton.addActionListener(
                e -> submitTicket()
        );


        add(panel);

        setVisible(true);
    }


    // ==========================================
    // ANALYZE TICKET WITH AI
    // ==========================================

    private void analyzeTicketWithAI() {

        String title =
                titleField.getText().trim();

        String description =
                descriptionArea.getText().trim();


        if (title.isEmpty() || description.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a Ticket Title and Description before using AI Triage.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        analyzeButton.setEnabled(false);
        analyzeButton.setText("Analyzing...");


        SwingWorker<TriageResult, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected TriageResult doInBackground()
                            throws Exception {

                        TicketTriageService triageService =
                                new TicketTriageService();

                        return triageService.analyzeTicket(
                                title,
                                description
                        );
                    }


                    @Override
                    protected void done() {

                        try {

                            TriageResult result = get();

                            setCategoryFromAI(
                                    result.getCategory()
                            );

                            setPriorityFromAI(
                                    result.getPriority()
                            );


                            if (result.getDepartment() != null) {

                                departmentField.setText(
                                        result.getDepartment().trim()
                                );
                            }


                            if (result.getSummary() != null) {

                                aiSummaryArea.setText(
                                        result.getSummary().trim()
                                );
                            }


                            analyzeButton.setText(
                                    "AI Triage Complete"
                            );


                            Timer timer = new Timer(
                                    1500,
                                    e -> analyzeButton.setText(
                                            "AI Triage"
                                    )
                            );

                            timer.setRepeats(false);
                            timer.start();


                        } catch (Exception e) {

                            Throwable cause =
                                    e.getCause() != null
                                            ? e.getCause()
                                            : e;


                            JOptionPane.showMessageDialog(
                                    CreateTicketForm.this,
                                    "AI triage failed: " +
                                            cause.getMessage(),
                                    "AI Triage Error",
                                    JOptionPane.ERROR_MESSAGE
                            );


                            cause.printStackTrace();

                            analyzeButton.setText(
                                    "AI Triage"
                            );

                        } finally {

                            analyzeButton.setEnabled(true);
                        }
                    }
                };


        worker.execute();
    }


    // ==========================================
    // APPLY AI CATEGORY
    // ==========================================

    private void setCategoryFromAI(String category) {

        if (category == null) {
            categoryCombo.setSelectedItem("Other");
            return;
        }


        String aiCategory = category.trim();


        for (int i = 0;
             i < categoryCombo.getItemCount();
             i++) {

            String item =
                    categoryCombo.getItemAt(i);

            if (item.equalsIgnoreCase(aiCategory)) {

                categoryCombo.setSelectedIndex(i);
                return;
            }
        }


        if (aiCategory.equalsIgnoreCase("Account")) {

            categoryCombo.setSelectedItem(
                    "Account/Login"
            );

            return;
        }


        categoryCombo.setSelectedItem("Other");
    }


    // ==========================================
    // APPLY AI PRIORITY
    // ==========================================

    private void setPriorityFromAI(String priority) {

        if (priority == null) {
            priorityCombo.setSelectedItem("Medium");
            return;
        }


        String aiPriority = priority.trim();


        for (int i = 0;
             i < priorityCombo.getItemCount();
             i++) {

            String item =
                    priorityCombo.getItemAt(i);

            if (item.equalsIgnoreCase(aiPriority)) {

                priorityCombo.setSelectedIndex(i);
                return;
            }
        }


        priorityCombo.setSelectedItem("Medium");
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
                        departmentField.setText("");
                        aiSummaryArea.setText("");
                    }
                }


            } catch (SQLException e) {

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