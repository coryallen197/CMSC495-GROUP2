# AI IT Help Desk Assistant
## API and Interface Documentation

**CMSC 495 – Group 2**

## 1. Purpose

This document describes the interfaces used by the AI IT Help Desk Assistant to support database connectivity and AI-assisted ticket triage.

The application is a Java desktop application and does not expose a public REST API. Instead, it uses internal Java classes to communicate between application components and an external API to perform AI-assisted ticket analysis.

The primary components documented here are:

- `OpenAIService`
- `TicketTriageService`
- `TriageResult`
- `DatabaseConnection`
- OpenAI API integration

---

## 2. Interface Architecture

The AI-assisted triage process separates the user interface, triage logic, external API communication, and returned data.

```text
Ticket Details
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
OpenAIService
      |
      v
TicketTriageService
      |
      v
TriageResult
      |
      v
Ticket Details
```

This design separates responsibilities between components:

- **Ticket Details** initiates the triage operation and presents the result.
- **TicketTriageService** constructs the triage request and interprets the AI response.
- **OpenAIService** manages communication with the external OpenAI API.
- **TriageResult** represents the structured recommendation returned to the application.

---

## 3. OpenAIService

### 3.1 Purpose

`OpenAIService` provides the application's interface to the external OpenAI API.

Its responsibilities include:

- Retrieving the API key from the environment.
- Creating the API request.
- Establishing an HTTPS connection.
- Sending the request to the OpenAI API.
- Receiving the API response.
- Extracting the returned AI-generated text.
- Returning the extracted text to the calling service.

### 3.2 Constructor

```java
public OpenAIService()
```

The constructor retrieves the API credential from the environment variable:

```text
OPENAI_API_KEY
```

If the environment variable is missing or empty, the service throws an `IllegalStateException`.

Example error:

```text
OPENAI_API_KEY was not found.
```

The API key is intentionally kept outside the source code so that credentials do not need to be stored in or committed with the application.

### 3.3 sendRequest Method

```java
public String sendRequest(String prompt) throws Exception
```

#### Parameter

`prompt`

A `String` containing the instructions and ticket information to be submitted to the AI service.

#### Return Value

Returns a `String` containing the text extracted from the API response.

#### Exceptions

The method may propagate exceptions generated while establishing the connection, transmitting the request, or processing the response.

---

## 4. External AI API Request

`OpenAIService` sends an HTTPS `POST` request to the configured OpenAI API endpoint.

### 4.1 Authentication

Authentication is supplied through the HTTP `Authorization` header:

```text
Authorization: Bearer <OPENAI_API_KEY>
```

The request also specifies:

```text
Content-Type: application/json
```

The API key must never be written directly into the application's source code or committed to the repository.

### 4.2 Request Body

The service creates a JSON request containing the configured model and the input prompt.

Conceptually, the request has the following structure:

```json
{
  "model": "<configured model>",
  "input": "<ticket triage prompt>"
}
```

The prompt is escaped before being inserted into the JSON request so that characters such as quotation marks, backslashes, and line breaks can be transmitted correctly.

### 4.3 Response Processing

After sending the request, `OpenAIService` obtains the HTTP response code.

For successful HTTP responses, the service reads the normal response stream.

For unsuccessful HTTP responses, the service reads the API error stream.

The service then attempts to locate the generated output text in the returned response and passes that text back to `TicketTriageService`.

If the expected output text cannot be located, the current implementation returns the full API response to the calling service for further processing or troubleshooting.

## 5. TicketTriageService

### 5.1 Purpose

`TicketTriageService` provides the application logic for analyzing an IT support ticket using the AI service.

The class acts as an intermediary between the application and `OpenAIService`. It is responsible for:

- Receiving the ticket title and description.
- Constructing the AI triage prompt.
- Sending the prompt through `OpenAIService`.
- Receiving the AI-generated response.
- Parsing the response into structured values.
- Returning the results as a `TriageResult` object.

### 5.2 Constructor

```java
public TicketTriageService()
```

The constructor creates an instance of `OpenAIService` that is used for communication with the external AI service.

Because `OpenAIService` requires the `OPENAI_API_KEY` environment variable, creation of `TicketTriageService` also depends on the API key being properly configured.

### 5.3 analyzeTicket Method

```java
public TriageResult analyzeTicket(
        String title,
        String description) throws Exception
```

#### Parameters

`title`

A `String` containing the title of the support ticket.

`description`

A `String` containing the user's description of the technical problem.

#### Return Value

Returns a `TriageResult` containing the structured AI-assisted recommendation.

The returned result contains:

- Category
- Priority
- Summary
- Department

#### Exceptions

The method may propagate exceptions generated by the underlying AI service.

### 5.4 Triage Prompt

The service constructs a prompt identifying the AI as an IT Help Desk ticket triage assistant.

The ticket title and description are included in the prompt, and the AI is instructed to return exactly four lines:

```text
Category: <category>
Priority: <priority>
Summary: <one sentence summary>
Department: <responsible department>
```

The allowed priority values are:

```text
Low
Medium
High
Critical
```

The prompt suggests IT categories including:

```text
Hardware
Software
Network
Account
Security
Email
Printer
Other
```

The AI is instructed not to add additional explanations before or after the four required lines.

---

## 6. AI Response Contract

The communication between `TicketTriageService` and the AI service relies on a defined text format.

### 6.1 Expected Response

A valid response follows this structure:

```text
Category: Network
Priority: High
Summary: User cannot connect a laptop to the company wireless network before an upcoming meeting.
Department: Network Support
```

The specific values may vary depending on the ticket being analyzed, but the four field labels are expected to remain consistent.

### 6.2 Response Parsing

`TicketTriageService` separates the returned response into individual lines and examines each line for one of the expected prefixes:

```text
Category:
Priority:
Summary:
Department:
```

The text following each prefix is extracted and assigned to the corresponding result field.

The extracted values are then used to construct a `TriageResult`.

Conceptually:

```text
AI Response
     |
     +---- Category:   ---> category
     |
     +---- Priority:   ---> priority
     |
     +---- Summary:    ---> summary
     |
     +---- Department: ---> department
                              |
                              v
                         TriageResult
```

### 6.3 Response Requirements

For the response to be interpreted correctly, the AI service should return the four expected labeled values.

If an expected label is absent, the current parser does not generate a replacement value for that field. This makes the structured response format an important part of the interface contract between `TicketTriageService` and the AI service.

## 7. TriageResult

### 7.1 Purpose

`TriageResult` is the data object used to represent the structured recommendation produced by the AI-assisted triage process.

It separates the returned recommendation into four values:

- Category
- Priority
- Summary
- Department

`TicketTriageService` creates a `TriageResult` after parsing the response received from the AI service.

### 7.2 Constructor

```java
public TriageResult(
        String category,
        String priority,
        String summary,
        String department)
```

#### Parameters

`category`

The recommended classification of the IT support issue.

`priority`

The recommended urgency of the support ticket.

`summary`

A concise AI-generated summary of the reported issue.

`department`

The recommended department or support group responsible for handling the issue.

### 7.3 Accessor Methods

The result values can be retrieved using the following methods:

```java
getCategory()
getPriority()
getSummary()
getDepartment()
```

These methods allow other application components to access individual portions of the AI recommendation without processing the original API response.

### 7.4 Data Flow

```text
AI Response
     |
     v
TicketTriageService
     |
     | Parse response
     v
TriageResult
     |
     +----> getCategory()
     +----> getPriority()
     +----> getSummary()
     +----> getDepartment()
```

This object provides a defined boundary between the AI response-processing logic and the components that consume the triage recommendation.

---

## 8. DatabaseConnection

### 8.1 Purpose

`DatabaseConnection` provides a centralized method for establishing JDBC connections to the Help Desk database.

The application uses this component whenever access to the MySQL/MariaDB database is required.

### 8.2 Connection Configuration

The development configuration uses the following database connection:

```text
Database: helpdesk_db
Host: localhost
Port: 3306
Username: root
Password: [blank]
```

The JDBC connection URL is:

```text
jdbc:mysql://localhost:3306/helpdesk_db
```

These values represent the local development configuration and may need to be changed when the application is deployed in another environment.

### 8.3 getConnection Method

```java
public static Connection getConnection()
        throws SQLException
```

#### Return Value

Returns a JDBC `Connection` to the configured Help Desk database.

#### Exceptions

Throws `SQLException` when a connection cannot be established.

Possible causes include:

- The database server is not running.
- `helpdesk_db` does not exist.
- The database host or port is incorrect.
- The username or password is incorrect.
- MySQL Connector/J is not available to the application.

### 8.4 Example Usage

Application components obtain a connection using:

```java
try (Connection connection =
         DatabaseConnection.getConnection()) {

    // Perform database operation

} catch (SQLException e) {

    // Handle database error
}
```

Using try-with-resources allows the JDBC connection to be closed automatically after the database operation is complete.

### 8.5 Connection Test

`DatabaseConnection` also contains a `main` method that can be used to test database connectivity independently of the graphical application.

A successful test produces:

```text
Testing database connection...
Database connection successful!
```

This test is useful during installation and troubleshooting because it verifies the database connection before other application components are executed.

## 9. Error Handling

The application uses exception handling at the database and external API boundaries to prevent failures from terminating normal application processing without feedback.

### 9.1 Database Errors

Database operations may generate `SQLException` when a database operation cannot be completed.

Possible causes include:

- Database server unavailable
- Invalid connection settings
- Missing database or tables
- Invalid SQL operation
- Database constraint violation

Database-related application components catch SQL exceptions where appropriate and provide error information for troubleshooting.

### 9.2 AI Service Errors

`OpenAIService` may encounter errors while:

- Retrieving the API key
- Establishing the HTTPS connection
- Sending the API request
- Receiving the API response
- Processing the returned response

If `OPENAI_API_KEY` is unavailable, the service throws an `IllegalStateException`.

Unsuccessful HTTP responses are read from the API error stream. The returned response can then be used to identify problems with authentication, API access, service availability, or request processing.

### 9.3 Triage Response Errors

`TicketTriageService` expects the AI-generated response to contain:

```text
Category:
Priority:
Summary:
Department:
```

If one of these labels is missing, the current parser leaves the corresponding result value empty.

Because AI-generated output can vary, the structured response format is an important part of the application's AI interface contract.

---

## 10. Security Considerations

### 10.1 API Credentials

The OpenAI API key must be stored outside the source code using:

```text
OPENAI_API_KEY
```

API keys must not be:

- Hard-coded into Java classes.
- Stored in documentation.
- Committed to Git or GitHub.
- Displayed in screenshots or demonstrations.
- Shared publicly.

### 10.2 Database Credentials

The current database configuration is intended for local development.

Production or externally accessible deployments should not rely on a default administrative database account or blank password.

Database credentials should be protected and configured appropriately for the deployment environment.

### 10.3 AI Recommendations

AI-generated triage results are recommendations rather than authoritative ticket-management decisions.

Support personnel should review AI-generated:

- Categories
- Priorities
- Summaries
- Department assignments

before acting on the recommendation.

---

## 11. Interface Testing

The project includes test classes that allow major interfaces to be checked independently.

### ApiKeyTest

Purpose:

Verifies that the Java runtime can access the `OPENAI_API_KEY` environment variable.

### TriageTest

Purpose:

Submits a sample support ticket through `TicketTriageService` and displays the returned `TriageResult`.

This test can be used to verify communication between:

```text
TriageTest
    |
    v
TicketTriageService
    |
    v
OpenAIService
    |
    v
OpenAI API
```

### DatabaseConnection

Purpose:

Verifies that JDBC can establish a connection to `helpdesk_db`.

Testing these components separately helps isolate configuration problems before testing the complete graphical application.

---

## 12. Interface Reference

| Component | Interface | Input | Output / Purpose |
|---|---|---|---|
| `DatabaseConnection` | `getConnection()` | None | Returns a JDBC database connection |
| `OpenAIService` | `OpenAIService()` | `OPENAI_API_KEY` environment variable | Initializes external API communication |
| `OpenAIService` | `sendRequest(String prompt)` | AI prompt | Returns AI response text |
| `TicketTriageService` | `TicketTriageService()` | None | Initializes the ticket-triage service |
| `TicketTriageService` | `analyzeTicket(String title, String description)` | Ticket title and description | Returns a `TriageResult` |
| `TriageResult` | `getCategory()` | None | Returns recommended category |
| `TriageResult` | `getPriority()` | None | Returns recommended priority |
| `TriageResult` | `getSummary()` | None | Returns generated ticket summary |
| `TriageResult` | `getDepartment()` | None | Returns recommended department |

---

## 13. Configuration Reference

### Database

```text
Host: localhost
Port: 3306
Database: helpdesk_db
Username: root
Password: [blank in local development configuration]
```

### AI Service

```text
Environment Variable: OPENAI_API_KEY
Authentication: Bearer token
Request Format: JSON
Response: AI-generated ticket triage information
```

### Expected Triage Result

```text
Category: <category>
Priority: <Low | Medium | High | Critical>
Summary: <one sentence summary>
Department: <responsible department>
```

---

## 14. Summary

The AI IT Help Desk Assistant uses defined interfaces to separate database access, AI communication, ticket-analysis logic, and returned triage data.

`DatabaseConnection` provides database access, `OpenAIService` manages external AI communication, `TicketTriageService` performs ticket-analysis orchestration, and `TriageResult` provides a structured representation of the recommendation.

This separation allows the application's graphical interface, database functionality, and AI-assisted features to remain modular and easier to test and maintain.