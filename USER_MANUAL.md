# AI IT Help Desk Assistant
## User Manual

**CMSC 495 – Group 2**

## 1. Introduction

The AI IT Help Desk Assistant is a desktop application designed to help users submit IT support requests and assist support personnel with reviewing and managing those requests.

The application provides tools for:

- Creating IT support tickets.
- Viewing submitted tickets.
- Reviewing individual ticket details.
- Tracking ticket priority and status.
- Updating ticket status.
- Maintaining ticket history.
- Using AI-assisted ticket triage to help classify and prioritize support requests.

AI-generated recommendations are intended to assist support personnel. Final ticket-management decisions remain with the user.

---

## 2. Starting the Application

Before launching the application, the Help Desk database must be available.

Launch the application by starting the **Ticket Dashboard**.

When the application opens, the Ticket Dashboard displays the support tickets currently stored in the system.

The dashboard contains the following columns:

| Column | Description |
|---|---|
| Ticket ID | Unique identification number assigned to the ticket |
| Title | Title of the reported support issue |
| Category | Type of IT problem reported |
| Priority | Urgency assigned to the ticket |
| Status | Current state of the ticket |
| Created Date | Date and time the ticket was created |

Tickets are displayed with the most recently created tickets first.

---

## 3. Ticket Dashboard

The Ticket Dashboard is the primary navigation screen for the Help Desk application.

From the dashboard, users can:

- Review existing support tickets.
- Refresh the displayed ticket information.
- Create a new ticket.
- Open an existing ticket.

### Refreshing the Dashboard

Select:

```text
Refresh
```

to reload ticket information from the database.

Refreshing the dashboard is useful after creating or modifying a ticket so that the latest information is displayed.

### Opening a Ticket

To view an existing ticket:

1. Locate the desired ticket in the dashboard.
2. Double-click the ticket row.
3. The Ticket Details window will open for the selected ticket.

### Creating a New Ticket

Select:

```text
Create New Ticket
```

to open the Create Ticket interface.

## 4. Creating a Support Ticket

The Create Ticket window allows a user to enter information about a new IT support request.

### 4.1 Ticket Information

Enter the following information:

**Ticket Title**

Enter a short title that identifies the problem.

Example:

```text
Unable to Connect to Wi-Fi
```

**Description**

Enter a description of the technical problem. Include enough information for support personnel to understand the issue.

Example:

```text
My laptop cannot connect to the company Wi-Fi network.
Other devices appear to be working normally.
```

**Category**

Select the category that best represents the problem:

- Hardware
- Software
- Network
- Account
- Login
- Other

**Priority**

Select the appropriate priority:

- Low
- Medium
- High
- Critical

### 4.2 Using AI Triage

After entering the ticket title and description, the user may select **AI Triage** before submitting the ticket.

The AI service analyzes the title and description and provides recommendations for:

- Category
- Priority
- Summary
- Department

The recommended category and priority are applied to the corresponding fields, while the AI summary and recommended department are displayed for review.

Review the AI-generated recommendations before submitting the ticket. Category and priority can be adjusted if necessary.

If the AI service is unavailable, the ticket can still be completed by selecting the category and priority manually.

### 4.3 Submitting the Ticket

After entering the ticket information, select the button used to submit the ticket.

The application verifies that the required information has been entered.

The **Ticket Title** and **Description** fields cannot be left blank.

When the ticket is successfully created:

- The ticket is stored in the Help Desk database.
- The initial ticket status is set to `Open`.
- A ticket creation entry is added to the ticket history.
- A confirmation message is displayed.

After creating the ticket, return to or refresh the Ticket Dashboard to view the newly created ticket.

---

## 5. Viewing and Managing Ticket Details

Existing tickets can be opened from the Ticket Dashboard by double-clicking the desired ticket.

The Ticket Details window provides additional information and management options for the selected support request.

### 5.1 Reviewing Ticket Information

Use the Ticket Details window to review the information associated with the selected ticket.

Depending on the ticket, this information may include:

- Ticket identification information
- Description of the reported issue
- Category
- Priority
- Current status
- Creation information
- Ticket history

Review the ticket information before making changes to its status or acting on an AI-assisted recommendation.

### 5.2 Updating Ticket Status

The Ticket Details interface allows the current ticket status to be changed as the support request progresses.

When changing a ticket's status:

1. Open the ticket from the Ticket Dashboard.
2. Select the appropriate new status.
3. Save or apply the change using the available status control.
4. Verify that the updated status is displayed.

Status changes are recorded so that the progression of the support request can be reviewed later.

### 5.3 Ticket History

Ticket history provides a record of activity associated with the support request.

A history record is created when a new ticket is submitted. Additional history information is recorded when supported ticket changes occur.

This provides support personnel with a record of how the ticket has progressed after its initial creation.

## 6. AI-Assisted Ticket Triage

The AI-assisted triage feature helps evaluate an IT support request before the ticket is submitted..

The feature analyzes the ticket title and description and provides a structured recommendation.

### 6.1 Triage Recommendations

The AI-assisted triage process can provide the following information:

- **Category** – Recommended classification of the reported problem.
- **Priority** – Recommended urgency of the ticket.
- **Summary** – A concise summary of the reported issue.
- **Department** – Recommended department or support group for handling the issue.

An example recommendation may appear as:

```text
Category: Network
Priority: High
Summary: User cannot connect a laptop to the company wireless network.
Department: Network Support
```

### 6.2 Using AI Triage

To use AI-assisted triage:

1. Open the **Create Ticket** interface.
2. Enter the ticket title and description.
3. Select **AI Triage**.
4. Allow the application to analyze the ticket.
5. Review the returned category, priority, summary, and department recommendations.
6. Adjust the category or priority if necessary.
7. Submit the ticket when the information has been reviewed.

The AI feature requires access to the configured external AI service. If the service is unavailable or not configured, other Help Desk functions can still be used independently.

### 6.3 Reviewing AI Recommendations

AI-generated results are recommendations and should be reviewed before they are used to make ticket-management decisions.

Support personnel should consider the original ticket information along with the AI recommendation.

If an AI recommendation appears incorrect or inappropriate, the support user should rely on the ticket information and their own judgment rather than treating the recommendation as authoritative.

---

## 7. Typical User Workflow

A typical Help Desk workflow is:

```text
Launch Application
       |
       v
View Ticket Dashboard
       |
       +---------------------+
       |                     |
       v                     v
Create New Ticket       Open Existing Ticket
       |                     |
       v                     v
Enter Information       Review Ticket Details
       |                     |
       +----> AI Triage      +----> Update Ticket Status
       |                     |
       v                     +----> Review Ticket History
Review Recommendations
       |
       v
Submit Ticket
       |
       v
Refresh Dashboard
```

For a new support request:

1. Open the Create Ticket window.
2. Enter the ticket title and description.
3. Use AI-assisted triage when appropriate.
4. Review the AI recommendations.
5. Adjust the category or priority if necessary.
6. Submit the ticket.
7. Refresh the Ticket Dashboard if necessary.
8. Open the ticket to review its details.
9. Update the ticket as the support request progresses.

## 8. Troubleshooting

### Application Cannot Connect to the Database

If the application reports a database connection error:

1. Verify that the MySQL/MariaDB database server is running.
2. Confirm that the `helpdesk_db` database is available.
3. Contact the application administrator or development team if the problem continues.

Database installation and configuration procedures are provided in `INSTALLATION_GUIDE.md`.

### Tickets Do Not Appear on the Dashboard

If a recently created or modified ticket is not displayed:

1. Select **Refresh** on the Ticket Dashboard.
2. Verify that the ticket was successfully submitted.
3. If the problem continues, verify that the application is connected to the database.

### AI Triage Is Unavailable

AI-assisted triage requires access to the configured external AI service.

If AI triage does not return a recommendation:

1. Verify that an Internet connection is available.
2. Try the operation again.
3. If the problem continues, contact the application administrator or development team.

The Help Desk's non-AI ticket functions can still be used independently when the AI service is unavailable.

### Ticket Cannot Be Submitted

If a ticket cannot be submitted, verify that both the **Ticket Title** and **Description** fields contain information.

These fields are required before a new support ticket can be created.

---

## 9. User Best Practices

When creating and managing support tickets:

- Use a short, descriptive ticket title.
- Provide enough detail in the description to explain the problem.
- Select the category that most closely represents the issue.
- Select a priority that reflects the actual urgency of the problem.
- Review existing ticket information before making changes.
- Refresh the dashboard after changes when necessary.
- Review AI-generated recommendations before acting on them.
- Do not treat AI recommendations as replacements for human judgment.

Clear and complete ticket information helps both support personnel and the AI-assisted triage feature evaluate the reported problem more effectively.

---

## 10. Quick Reference

| Task | Action |
|---|---|
| View tickets | Open the Ticket Dashboard |
| Refresh tickets | Select **Refresh** |
| Create a ticket | Select **Create New Ticket** |
| Open a ticket | Double-click its dashboard row |
| Review details | Open the selected ticket |
| Update status | Use the status controls in Ticket Details |
| Review history | View the history associated with the ticket |
| Request AI assistance | Select **AI Triage** while creating a ticket |
| Review AI results | Review category, priority, summary, and department recommendations |

---

## 11. Additional Documentation

For information intended for developers or administrators, refer to:

- `README.md` – Project overview, architecture, features, and development information.
- `INSTALLATION_GUIDE.md` – Development environment, database, JDBC, and AI configuration instructions.
- `API_DOCUMENTATION.md` – Internal Java interfaces and external AI API integration.

---

## 12. Conclusion

The AI IT Help Desk Assistant provides a centralized interface for creating, reviewing, and managing IT support tickets while incorporating AI-assisted ticket triage.

The application is designed so that AI recommendations support the ticket-management process while human users retain responsibility for reviewing information and making final support decisions.