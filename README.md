# AI IT Help Desk Assistant

**CMSC 495 – Group 2**

**Team Members**
- Cory Allen
- Kayden Ayers
- Rich Sterchele

## Project Overview

The AI IT Help Desk Assistant is a Java-based desktop application designed to improve the process of submitting, reviewing, and managing IT support requests.

The application provides a centralized Help Desk system where users can create support tickets and IT personnel can review, manage, and update those tickets. Ticket information is stored in a MySQL/MariaDB database and accessed through a Java Swing graphical user interface.

The project also integrates an AI-assisted ticket triage feature. The AI analyzes a ticket's title and description and generates recommendations for the ticket's category, priority, summary, and responsible department.

AI-generated recommendations are intended to assist IT personnel rather than replace human decision-making. Support personnel remain responsible for reviewing tickets and determining the appropriate course of action.

## Core Features

The AI IT Help Desk Assistant provides the following functionality:

- Create and submit IT support tickets.
- Store ticket information in a MySQL/MariaDB database.
- View submitted tickets through a centralized Ticket Dashboard.
- Display ticket ID, title, category, priority, status, and creation date.
- Open individual tickets to review detailed information.
- Update ticket status.
- Record ticket status changes in ticket history.
- Use database transactions to maintain consistency between ticket updates and history records.
- Analyze ticket titles and descriptions using an AI service.
- Generate AI-assisted recommendations for:
  - Category
  - Priority
  - Summary
  - Responsible department
- Maintain human review of AI-generated recommendations.

## Technology Stack

- **Java** — Core application logic.
- **Java Swing** — Desktop graphical user interface.
- **JDBC** — Database connectivity.
- **MySQL/MariaDB** — Ticket, user, and ticket-history storage.
- **MySQL Connector/J** — JDBC driver used by the Java application.
- **OpenAI API** — AI-assisted ticket analysis and triage.
- **XAMPP** — Local MariaDB/MySQL development environment.
- **Git** — Source code version control.
- **GitHub** — Repository hosting and team collaboration.
- **GitHub Actions** — Continuous integration and automated build/testing.

## Application Components

The application is divided into several components that work together to provide Help Desk ticket management and AI-assisted triage.

### Create Ticket

The Create Ticket interface allows users to create and submit a new IT support request.

Users enter:

- Ticket title
- Problem description

Before submitting the ticket, users can run **AI Triage** to analyze the title and description. The AI service generates recommendations for:

- Category
- Priority
- Summary
- Responsible department

The recommended category and priority are applied to the corresponding ticket fields, while the department and AI-generated summary are displayed for review.

Users may review the recommendations before submitting the ticket. Available categories include Hardware, Software, Network, Account/Login, and Other. Available priorities are Low, Medium, High, and Critical.

Before a ticket is submitted, the application verifies that a title and description have been entered. New tickets are assigned an initial status of **Open** and stored in the database. A corresponding ticket-history record is also created.

### Ticket Dashboard

The Ticket Dashboard provides an overview of tickets stored in the Help Desk database.

The dashboard displays:

- Ticket ID
- Title
- Category
- Priority
- Status
- Created Date

Tickets are displayed with the most recently created tickets first. Users can refresh the dashboard, create a new ticket, double-click an existing ticket to open its detailed view, or select an existing ticket and run AI Triage.
Dashboard AI Triage analyzes the selected ticket's existing title and description and displays recommended category, priority, summary, and responsible department. These recommendations are presented for review and do not automatically modify the existing ticket.

### Ticket Details

The Ticket Details interface provides additional information about an individual support ticket.

From this interface, support personnel can review ticket information and manage the ticket's status.

### Ticket History

The system maintains a history of ticket activity to support tracking and accountability.

When a ticket is created, an initial history record is generated. Status changes are also recorded so that changes to a ticket can be tracked over time.

Database transactions are used when related ticket and history records are modified so that the operations can be processed together.

## AI-Assisted Ticket Triage

The application includes an AI-assisted triage component designed to help IT personnel evaluate incoming support requests.

The ticket's title and description are submitted to the AI service for analysis. The system requests four structured recommendations:

- **Category** — The type of IT issue.
- **Priority** — Low, Medium, High, or Critical.
- **Summary** — A concise summary of the reported problem.
- **Department** — The recommended department responsible for handling the issue.

The application processes the AI response and stores the recommendations in a `TriageResult` object for presentation to the user. 

AI recommendations are advisory. Support personnel remain responsible for reviewing the recommendation and making ticket-management decisions.
AI Triage is available during ticket creation and from the Ticket Dashboard. During ticket creation, recommendations can be reviewed before the ticket is submitted. From the dashboard, 
AI Triage can analyze a selected existing ticket and display recommendations without modifying the stored ticket.

### Create Ticket AI Triage Workflow

    Create Ticket
        |
        v
    Ticket Title + Description
        |
        v
    TicketTriageService
        |
        v
    OpenAIService
        |
        v
    OpenAI API
        |
        v
    TriageResult
        |
        +----> Category
        +----> Priority
        +----> Summary
        +----> Department
        |
        v
    User Reviews Recommendations
        |
        v
    Submit Ticket

## System Workflow

The primary application workflow connects the user interface, database, ticket-management components, and AI-assisted triage service.


    Create Ticket
        |
        +----> Run AI-Assisted Triage
        |              |
        |              v
        |         OpenAI API
        |              |
        |              v
        |       AI Recommendations
        |              |
        |              v
        |         User Review
        |
        v
    Submit Ticket
        |
        v
    Store Ticket in Database
        |
        +----> Create Ticket History Record
        |
        v
    Ticket Dashboard
        |
        v
    Ticket Details
        |
        +----> Update Ticket Status
        |
        v
    Record Status Change
    in Ticket History

## Development Process

The project was developed incrementally through the following major steps:

1. Defined the HelpDesk system requirements and core features.
2. Created the Java project and application structure.
3. Designed and configured the MySQL database.
4. Implemented JDBC database connectivity.
5. Developed the Create Ticket interface.
6. Connected ticket creation to the MySQL database.
7. Developed the Ticket Dashboard.
8. Added the Ticket Details interface.
9. Implemented ticket status management.
10. Added ticket status history tracking.
11. Implemented database transaction handling for status changes.
12. Developed the AI ticket triage service.
13. Integrated AI triage into the Create Ticket interface and Ticket Dashboard.
14. Tested and debugged communication between the GUI, database, and AI components.
15. Used Git and GitHub for source control and team collaboration.
16. Prepared the project for automated build and testing through GitHub Actions.
17. Performed integration testing in preparation for the Alpha release.

## Alpha Release

The Alpha release demonstrates integration of the major components of the AI IT Help Desk Assistant.

### Implemented Functionality

The Alpha release includes:

- Java Swing graphical user interface
- Ticket creation and validation
- MySQL/MariaDB database integration
- Ticket Dashboard
- Individual ticket details
- Ticket status management
- Ticket-history tracking
- Database transaction handling
- AI-assisted ticket triage
- Integration between the GUI, database, and AI components
- Git/GitHub version control
- GitHub Actions continuous integration

### End-to-End Testing

The primary application workflow can be tested by:

1. Starting the database server.
2. Launching the Ticket Dashboard.
3. Opening the Create Ticket interface.
4. Entering a ticket title and problem description.
5. Running AI-assisted ticket triage.
6. Reviewing the recommended category, priority, summary, and department.
7. Submitting the support ticket.
8. Verifying that the ticket appears on the Ticket Dashboard.
9. Opening the ticket to review its details.
10. Changing the ticket status.
11. Verifying that the updated status is displayed.
12. Verifying that the status change is recorded in ticket history.

Dashboard AI Triage can also be tested by selecting an existing ticket, clicking AI Triage,
and reviewing the generated category, priority, summary, and department recommendations. 
Dashboard AI analysis is advisory and does not modify the stored ticket.

AI-assisted testing requires a valid `OPENAI_API_KEY` with access to the configured API service.

## Prerequisites

The development version of the application requires:

- Java Development Kit (JDK)
- MySQL or MariaDB database server
- MySQL Connector/J
- The included `helpdesk.sql` database script
- An OpenAI API key for AI-assisted triage
- An IDE capable of building and running the Java project, such as IntelliJ IDEA

Detailed environment configuration and database setup instructions are provided in the Installation Guide.

## Documentation

Additional project documentation is maintained in the project repository:

- `INSTALLATION_GUIDE.md` — Environment, database, JDBC driver, and API configuration instructions.
- `API_DOCUMENTATION.md` — Documentation for the application's AI integration and supporting Java components.
- `USER_MANUAL.md` — Instructions for operating the Help Desk application.

## CI/CD

The project uses GitHub Actions to support continuous integration.

The CI workflow is designed to validate project changes after they are pushed to the repository. The process includes checking out the source code, configuring the Java environment, building the application, running available tests, and reporting the result.

```text
Code Change
     |
     v
Git Commit
     |
     v
Git Push
     |
     v
GitHub Actions
     |
     v
Build and Test
     |
     v
PASS / FAIL
```

Continuous integration helps identify integration or build problems before changes are incorporated into later releases.

## Security and Configuration

API credentials must not be stored directly in the source code or committed to the GitHub repository.

The AI integration retrieves the OpenAI API key from the following environment variable:

```text
OPENAI_API_KEY
```

Each development environment must configure this variable separately before using AI-assisted ticket triage.

Database connection settings are configured through the application's database connection component. Developers should verify that these settings match their local database environment before launching the application.

## Project Status

The project has reached the Alpha integration stage. Core Help Desk functionality has been implemented, including ticket creation, database persistence, dashboard viewing, ticket details, status management, ticket-history tracking, and AI-assisted ticket triage.

Current development efforts focus on integration testing, validation of the AI service configuration, CI/CD verification, defect resolution, and preparation of project documentation.