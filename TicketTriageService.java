package Week8;

public class TicketTriageService {

    private final OpenAIService aiService;

    public TicketTriageService() {
        aiService = new OpenAIService();
    }

    public TriageResult analyzeTicket(
            String title,
            String description) throws Exception {

        String prompt =
                "You are an IT helpdesk ticket triage assistant.\n\n" +

                "Analyze the following support ticket.\n\n" +

                "TITLE:\n" +
                title +
                "\n\nDESCRIPTION:\n" +
                description +
                "\n\n" +

                "Return exactly four lines using this format:\n" +
                "Category: <category>\n" +
                "Priority: <priority>\n" +
                "Summary: <one sentence summary>\n" +
                "Department: <responsible department>\n\n" +

                "Use these priority values only:\n" +
                "Low, Medium, High, Critical.\n\n" +

                "Use a reasonable IT category such as:\n" +
                "Hardware, Software, Network, Account, " +
                "Security, Email, Printer, Other.\n\n" +

                "Do not add explanations before or after the four lines.";

        String response =
                aiService.sendRequest(prompt);

        return parseResponse(response);
    }

    private TriageResult parseResponse(String response) {

        String category = "";
        String priority = "";
        String summary = "";
        String department = "";

        String[] lines = response.split("\\r?\\n");

        for (String line : lines) {

            line = line.trim();

            if (line.startsWith("Category:")) {
                category =
                        line.substring("Category:".length()).trim();
            }

            else if (line.startsWith("Priority:")) {
                priority =
                        line.substring("Priority:".length()).trim();
            }

            else if (line.startsWith("Summary:")) {
                summary =
                        line.substring("Summary:".length()).trim();
            }

            else if (line.startsWith("Department:")) {
                department =
                        line.substring("Department:".length()).trim();
            }
        }

        return new TriageResult(
                category,
                priority,
                summary,
                department
        );
    }
}