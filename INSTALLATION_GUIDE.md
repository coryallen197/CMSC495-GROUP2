# AI IT Help Desk Assistant
## Installation Guide

**CMSC 495 – Group 2**

## 1. Purpose

This guide provides the steps required to configure and run the AI IT Help Desk Assistant in a local development environment.

The application requires a Java development environment, a MySQL/MariaDB database, the MySQL Connector/J JDBC driver, and an OpenAI API key for AI-assisted ticket triage.

The installation process consists of:

1. Installing the required software.
2. Configuring the MySQL/MariaDB database.
3. Importing the Help Desk database.
4. Configuring MySQL Connector/J.
5. Opening and configuring the Java project.
6. Testing the database connection.
7. Configuring the OpenAI API key.
8. Testing the AI integration.
9. Launching the application.

---

## 2. System Requirements

### Required Software

The following software is required to build and run the application:

- **Java Development Kit (JDK)** — Required to compile and run the Java application.
- **IntelliJ IDEA or compatible Java IDE** — Used to open, build, and run the project.
- **MySQL or MariaDB** — Provides the application's relational database.
- **MySQL Connector/J** — JDBC driver used by Java to communicate with the database.
- **Git** — Recommended for cloning and updating the project repository.

### AI Triage Requirement

AI-assisted ticket triage requires:

- An OpenAI API key
- API access associated with that key
- Internet connectivity

The remainder of the Help Desk application can be configured independently of the AI service.

### Project Database

The repository includes:

```text
helpdesk.sql
```

This SQL script creates the application's `helpdesk_db` database and its required tables, including:

- `tickets`
- `ticket_history`
- `users`

---

## 3. Obtain the Project

Clone the project repository using Git or download the repository from GitHub.

If using Git:

```text
git clone <repository-url>
```

After obtaining the project, open the project directory in IntelliJ IDEA or another compatible Java IDE.

Do not place API keys, passwords, or other credentials in files that will be committed to the repository.

## 4. Database Setup

The AI IT Help Desk Assistant requires a local MySQL or MariaDB database. XAMPP can be used to provide the required database server.

### 4.1 Start the Database Server

If using XAMPP:

1. Open the XAMPP Control Panel.
2. Locate **MySQL**.
3. Click **Start**.
4. Verify that MySQL is running.

The application is configured to connect to the database using:

```text
Host: localhost
Port: 3306
Database: helpdesk_db
User: root
Password: [blank]
```

These settings correspond to the default local development configuration used by the project.

### 4.2 Create the Help Desk Database

The repository contains the following database script:

```text
helpdesk.sql
```

The script creates the `helpdesk_db` database and the tables required by the application.

The database can be imported using phpMyAdmin or the MySQL/MariaDB command-line client.

#### Option A — phpMyAdmin

1. Start **Apache** and **MySQL** from the XAMPP Control Panel.
2. Open phpMyAdmin.
3. Select the **Import** option.
4. Choose the project's `helpdesk.sql` file.
5. Run the import.
6. Verify that `helpdesk_db` was created.

#### Option B — MariaDB/MySQL Command Line

Open the XAMPP Shell and enter:

```text
mysql -u root
```

At the MariaDB/MySQL prompt, the project SQL file can be imported or executed using the database client.

After installation, verify the database with:

```sql
USE helpdesk_db;
SHOW TABLES;
```

The database should contain the primary application tables:

```text
ticket_history
tickets
users
```

### 4.3 Database Connection Configuration

Database connectivity is configured in `DatabaseConnection.java`.

The default project configuration is:

```java
private static final String URL =
        "jdbc:mysql://localhost:3306/helpdesk_db";

private static final String USER = "root";

private static final String PASSWORD = "";
```

If the local database server uses a different port, username, or password, update these values to match the local environment.

Do not commit personal or production database credentials to the repository.

## 5. MySQL Connector/J Setup

The Java application communicates with the MySQL/MariaDB database through JDBC. MySQL Connector/J provides the JDBC driver required for this connection.

Without the driver, the application may report an error similar to:

```text
No suitable driver found for jdbc:mysql://localhost:3306/helpdesk_db
```

### 5.1 Obtain MySQL Connector/J

Download MySQL Connector/J and extract the downloaded archive.

The extracted directory should contain a `.jar` file similar to:

```text
mysql-connector-j-[version].jar
```

The exact version may vary.

### 5.2 Add Connector/J to IntelliJ IDEA

To add the JDBC driver to the project:

1. Open the project in IntelliJ IDEA.
2. Select **File → Project Structure**.
3. Select **Modules**.
4. Open the **Dependencies** tab.
5. Click the **+** button.
6. Select **JARs or Directories**.
7. Navigate to the extracted MySQL Connector/J directory.
8. Select the Connector/J `.jar` file.
9. Click **OK** to add the dependency.
10. Apply the changes and close Project Structure.

The Connector/J library should now appear as a project dependency.

### 5.3 Test the Database Connection

Before launching the complete application, run:

```text
DatabaseConnection.java
```

The class attempts to establish a connection using the database settings configured in the project.

A successful connection should produce:

```text
Testing database connection...
Connection time: <time> ms
Database connection successful!
```

If the connection fails, verify:

- The MySQL/MariaDB server is running.
- `helpdesk_db` exists.
- Connector/J has been added to the project.
- The database URL uses the correct host and port.
- The configured database username and password are correct.

The application should not be tested further until the database connection succeeds.

## 6. OpenAI API Configuration

The AI-assisted ticket triage feature communicates with the OpenAI API. The application retrieves the API key from an environment variable rather than storing the key in the Java source code.

The required environment variable is:

```text
OPENAI_API_KEY
```

### 6.1 Obtain an API Key

A valid OpenAI API key with API access is required to use the AI-assisted ticket triage feature.

The API key should be treated as a credential and must not be:

- Added directly to the Java source code.
- Included in documentation.
- Committed to Git or GitHub.
- Shared publicly.

Each developer should configure an authorized API key within their own development environment.

### 6.2 Configure the API Key in IntelliJ IDEA

The API key can be supplied to the application through an IntelliJ Run Configuration.

1. Open **Run → Edit Configurations**.
2. Select the Java class that will use the AI service.
3. Locate **Environment variables**.
4. Add the following variable:

```text
Name:  OPENAI_API_KEY
Value: <API key>
```

5. Save the Run Configuration.

Environment variables in IntelliJ may be specific to an individual Run Configuration. If multiple classes are executed directly, verify that the required configuration has access to `OPENAI_API_KEY`.

### 6.3 Verify API Key Detection

The project includes `ApiKeyTest.java` for verifying that the environment variable is available to the Java application.

Run:

```text
ApiKeyTest.java
```

A successful configuration should report that the API key was found.

If the application reports:

```text
API key was NOT found.
```

verify that `OPENAI_API_KEY` has been added to the Run Configuration being executed.

### 6.4 Test AI-Assisted Triage

After confirming that the API key is available, run:

```text
TriageTest.java
```

The test submits a sample IT support request to the ticket triage service.

A successful AI response should contain four values:

```text
Category: <category>
Priority: <priority>
Summary: <summary>
Department: <department>
```

The ticket triage service expects the priority to be one of:

```text
Low
Medium
High
Critical
```

The service then converts the AI response into a `TriageResult` containing the category, priority, summary, and responsible department.

### 6.5 API Troubleshooting

If the API key is detected but AI triage does not return a recommendation, review the API response for an error.

Possible causes include:

- The API key does not have access to the configured service.
- The API account does not currently have usable API credits.
- Internet connectivity is unavailable.
- The configured API model or endpoint is unavailable.
- The API request returned an error rather than a triage result.

API errors should be resolved before testing AI-assisted triage through the graphical interface.

The Help Desk database and non-AI ticket-management components can be tested separately from the AI integration.

## 7. Launching and Verifying the Application

After the database, JDBC driver, and optional AI service have been configured, the complete Help Desk application can be launched.

### 7.1 Start Required Services

Before launching the application:

1. Open the XAMPP Control Panel.
2. Start **MySQL**.
3. Verify that the database server is running on the port configured in `DatabaseConnection.java`.
4. If phpMyAdmin is needed for database administration, also start **Apache**.

Apache is not required for the Java application itself. It is only needed when using browser-based XAMPP tools such as phpMyAdmin.

### 7.2 Verify the Database

If the environment has not previously been tested, run:

```text
DatabaseConnection.java
```

Verify that the application reports:

```text
Testing database connection...
Database connection successful!
```

### 7.3 Launch the Ticket Dashboard

Run:

```text
TicketDashboard.java
```

The Ticket Dashboard serves as the primary interface for accessing the Help Desk system.

The dashboard displays:

```text
Ticket ID | Title | Category | Priority | Status | Created Date
```

Existing tickets stored in the database should appear automatically when the dashboard opens.

### 7.4 Create a Test Ticket

From the Ticket Dashboard:

1. Select **Create New Ticket**.
2. Enter a ticket title.
3. Enter a description of the problem.
4. If AI access is configured, select **AI Triage**.
5. Review the recommended category, priority, department, and AI summary.
6. Adjust the category or priority if necessary.
7. Select **Submit Ticket**.
8. Verify that the application reports that the ticket was created successfully.
9. Return to the Ticket Dashboard.
10. Select **Refresh**.
11. Verify that the new ticket appears in the dashboard.

A newly created ticket should initially have a status of:

```text
Open
```

The application also creates a corresponding ticket-history record when the ticket is submitted.

### 7.5 Open Ticket Details

From the Ticket Dashboard, double-click a ticket to open its detailed view.

The Ticket Details interface can be used to review ticket information and perform available ticket-management operations.

### 7.6 Verify AI-Assisted Triage

AI-assisted triage is available from the **Create Ticket** interface.

To verify the feature:

1. From the Ticket Dashboard, select **Create New Ticket**.
2. Enter a ticket title and problem description.
3. Select **AI Triage**.
4. Wait for the AI analysis to complete.
5. Verify that the interface displays or applies recommendations for:
    - Category
    - Priority
    - Summary
    - Department
6. Review the recommendations before submitting the ticket.

The AI recommendations are advisory. Users can review the recommended values before the ticket is submitted.

If the external AI service returns an error, the application displays an AI Triage error message rather than terminating the application.

If AI access has not been configured, the database and standard ticket-management features can still be tested independently.

---

## 8. Troubleshooting

### No Suitable JDBC Driver

Example:

```text
No suitable driver found for jdbc:mysql://localhost:3306/helpdesk_db
```

**Resolution:** Verify that MySQL Connector/J has been added to the project's dependencies.

### Database Connection Refused

A connection-refused error normally indicates that the database server cannot be reached.

**Resolution:** Verify that MySQL/MariaDB is running and that the configured host and port are correct.

### Unknown Database

Example:

```text
Unknown database 'helpdesk_db'
```

**Resolution:** Import `helpdesk.sql` and verify that `helpdesk_db` exists.

The database can be checked with:

```sql
USE helpdesk_db;
SHOW TABLES;
```

### API Key Not Found

Example:

```text
OPENAI_API_KEY was not found.
```

**Resolution:** Add `OPENAI_API_KEY` to the environment variables for the Run Configuration that launches the AI functionality.

### API Request Returns an Error

If the API key is detected but no AI recommendation is produced, inspect the API response. Verify that the key has appropriate API access and that the external API service is available.

---

## 9. Installation Verification Checklist

Before considering the installation complete, verify that:

- The Java project builds successfully.
- MySQL/MariaDB is running.
- `helpdesk_db` exists.
- The `tickets`, `ticket_history`, and `users` tables exist.
- MySQL Connector/J is included in the project dependencies.
- `DatabaseConnection.java` connects successfully.
- `TicketDashboard.java` launches successfully.
- Existing tickets appear in the dashboard.
- A new ticket can be created and stored.
- Ticket details can be opened.
- Ticket status changes can be performed and recorded.
- `OPENAI_API_KEY` is configured when AI-assisted triage is required.
- AI-assisted triage returns a structured recommendation when API access is available.

## 10. Installation Complete

After the verification steps have been completed, the AI IT Help Desk Assistant is ready for local use and testing.

For instructions on operating the application, refer to:

```text
USER_MANUAL.md
```

For information about the application's AI integration and software interfaces, refer to:

```text
API_DOCUMENTATION.md
```