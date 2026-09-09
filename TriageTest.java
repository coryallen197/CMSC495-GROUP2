package Week8;

public class TriageTest {

    public static void main(String[] args) {

        try {

            TicketTriageService triage =
                    new TicketTriageService();

            String title =
                    "Laptop cannot connect to company Wi-Fi";

            String description =
                    "My laptop won't connect to company Wi-Fi " +
                    "and I have a meeting in 20 minutes.";

            TriageResult result =
                    triage.analyzeTicket(
                            title,
                            description
                    );

            System.out.println("AI TRIAGE RESULT");
            System.out.println("================");
            System.out.println(result);

            System.out.println("\n--- Individual Values ---");
            System.out.println("Category = [" + result.getCategory() + "]");
            System.out.println("Priority = [" + result.getPriority() + "]");
            System.out.println("Summary = [" + result.getSummary() + "]");
            System.out.println("Department = [" + result.getDepartment() + "]");

            System.out.println("AI TRIAGE RESULT");
            System.out.println("================");
            System.out.println(result);

        } catch (Exception e) {

            System.out.println(
                    "Error during AI triage:"
            );

            e.printStackTrace();
        }
    }
}