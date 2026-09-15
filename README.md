# CMSC495-GROUP2

Cory Allen
Kayden Ayers
Rich Sterchele

# AI IT Help Desk Assistant

## Project Overview

The AI IT Help Desk Assistant is designed to improve the process of submitting, reviewing, and managing IT support requests. The system uses an AI-powered assistant to help users describe technical issues, identify relevant information, and create more complete and structured support tickets.

The goal of the project is to reduce the amount of time IT support personnel spend interpreting incomplete or unclear ticket descriptions. By assisting with the initial ticketing process, the system can help IT staff better understand reported problems, prioritize requests, and provide more efficient support.

AI-generated recommendations are intended to assist IT personnel rather than replace human decision-making. Support staff remain responsible for reviewing tickets and determining the appropriate course of action.

## Core Features

The AI IT Help Desk Assistant includes the following core functionality:

* Create and submit IT support tickets.
* Store and retrieve ticket information using a MySQL database.
* View submitted tickets through a ticket dashboard.
* Open individual tickets to review detailed information.
* Assign ticket categories and priorities.
* Update ticket status as Open, In Progress, or Closed.
* Record ticket status changes for tracking and accountability.
* Use AI to analyze ticket titles and descriptions.
* Generate AI-assisted recommendations for ticket category, priority, summary, and responsible department.

## Technology Stack

The project uses the following technologies:

* **Java** — Core application development.
* **Java Swing** — Graphical user interface.
* **JDBC** — Communication between the Java application and database.
* **MySQL** — Storage of tickets and ticket history.
* **AI API Integration** — AI-powered ticket analysis and triage.
* **Git** — Source code version control.
* **GitHub** — Repository hosting and team collaboration.
* **GitHub Actions** — Continuous integration and automated build/testing.

## Application Components

The application is divided into several components that work together to provide the HelpDesk functionality.

### Create Ticket

The Create Ticket interface allows users to enter information about an IT problem and submit it to the HelpDesk system. Ticket information is stored in the MySQL database for later review by support personnel.

### Ticket Dashboard

The Ticket Dashboard provides support personnel with an overview of submitted tickets. It displays important information including:

* Ticket ID
* Title
* Category
* Priority
* Status
* Creation date

Users can refresh the dashboard, create additional tickets, and open an existing ticket to view its details.

### Ticket Details

The Ticket Details interface displays additional information about a selected ticket, including its title, description, category, priority, and current status.

Support personnel can also update the ticket status from this screen.

### Ticket History

When the status of a ticket changes, the application records the change in the ticket history. This allows the system to maintain a record of previous and new ticket statuses.

Database transactions are used to help ensure that the ticket status update and corresponding history entry are processed together.

## AI Ticket Triage

One of the primary features of the project is AI-assisted ticket triage.

The AI analyzes the ticket's **title and description** and generates a structured recommendation containing:

* **Category** — The type of technical issue.
* **Priority** — The recommended urgency of the ticket.
* **Summary** — A concise summary of the reported problem.
* **Department** — The recommended IT support group.

The general AI workflow is:

```text
Ticket Title + Description
          |
          v
 Ticket Triage Service
          |
          v
      AI Service
          |
          v
    Triage Result
          |
          +-- Category
          +-- Priority
          +-- Summary
          +-- Department
```

The recommendation is presented to support personnel for review. The AI provides assistance with classification and prioritization, while the final decisions remain with the support staff.

## System Workflow

The primary application workflow is:

```text
Create Ticket
      |
      v
Store in MySQL
      |
      v
Ticket Dashboard
      |
      v
Ticket Details
      |
      +------> Update Status
      |              |
      |              v
      |        Ticket History
      |
      +------> AI Ticket Triage
                     |
                     v
              AI Recommendation
```

This demonstrates the integration of the user interface, database, ticket management, and AI components within a single application.

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
13. Integrated AI triage into the Ticket Details interface.
14. Tested and debugged communication between the GUI, database, and AI components.
15. Used Git and GitHub for source control and team collaboration.
16. Prepared the project for automated build and testing through GitHub Actions.
17. Performed integration testing in preparation for the Alpha release.

## Alpha Release

The Alpha release is intended to demonstrate a working integration of the project's major components.

### Implemented Alpha Functionality

* Java Swing graphical user interface
* Ticket creation
* MySQL database integration
* Ticket dashboard
* Individual ticket details
* Ticket status management
* Ticket history tracking
* AI-powered ticket triage
* Integration between the GUI, database, and AI components
* Git/GitHub version control

### Alpha Release Testing

The application can be tested using the following end-to-end workflow:

1. Launch the HelpDesk application.
2. Create a new support ticket.
3. Verify that the ticket is stored in MySQL.
4. Verify that the ticket appears on the Ticket Dashboard.
5. Open the ticket and review its details.
6. Run AI ticket triage.
7. Review the AI-generated category, priority, summary, and department.
8. Change the ticket status.
9. Verify that the status change is stored in the ticket history.
10. Commit and push the completed changes to GitHub.
11. Verify the automated CI/CD workflow.

## CI/CD

The project uses GitHub Actions for continuous integration.

The CI/CD pipeline is intended to automatically validate project changes after they are pushed to GitHub. The workflow includes:

1. Checking out the latest source code.
2. Configuring the Java environment.
3. Building the application.
4. Running available automated tests.
5. Reporting whether the build and tests pass or fail.

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

## Project Status

The project is currently being prepared for the Alpha release. Core HelpDesk functionality, database integration, ticket management, and AI-assisted ticket triage have been implemented.

Remaining Alpha-release work includes final integration testing, validation of the CI/CD pipeline, resolution of any identified defects, and preparation of the Alpha release.
