package Week8;

public class TicketTriageServiceTest {

    private static int testsRun = 0;
    private static int testsPassed = 0;

    public static void main(String[] args) {

        // No live OpenAI connection is needed for parser testing.
        TicketTriageService service =
                new TicketTriageService(null);

        testValidResponse(service);
        testWhitespaceResponse(service);
        testMissingField(service);

        System.out.println();
        System.out.println(
                "Tests passed: " + testsPassed + "/" + testsRun
        );

        if (testsPassed != testsRun) {
            System.exit(1);
        }
    }

    private static void testValidResponse(
            TicketTriageService service) {

        String response =
                "Category: Network\n" +
                        "Priority: High\n" +
                        "Summary: User cannot connect to Wi-Fi.\n" +
                        "Department: Network Support";

        TriageResult result =
                service.parseResponse(response);

        test(
                "Valid response category",
                "Network".equals(result.getCategory())
        );

        test(
                "Valid response priority",
                "High".equals(result.getPriority())
        );

        test(
                "Valid response summary",
                "User cannot connect to Wi-Fi."
                        .equals(result.getSummary())
        );

        test(
                "Valid response department",
                "Network Support"
                        .equals(result.getDepartment())
        );
    }

    private static void testWhitespaceResponse(
            TicketTriageService service) {

        String response =
                "  Category: Software  \n" +
                        "  Priority: Medium  \n" +
                        "  Summary: Application will not start.  \n" +
                        "  Department: Software Support  ";

        TriageResult result =
                service.parseResponse(response);

        test(
                "Whitespace is trimmed",
                "Software".equals(result.getCategory()) &&
                        "Medium".equals(result.getPriority()) &&
                        "Application will not start."
                                .equals(result.getSummary()) &&
                        "Software Support"
                                .equals(result.getDepartment())
        );
    }

    private static void testMissingField(
            TicketTriageService service) {

        String response =
                "Category: Hardware\n" +
                        "Priority: Low\n" +
                        "Summary: Mouse is not working.";

        TriageResult result =
                service.parseResponse(response);

        test(
                "Missing department produces empty value",
                result.getDepartment().isEmpty()
        );
    }

    private static void test(
            String testName,
            boolean condition) {

        testsRun++;

        if (condition) {
            testsPassed++;
            System.out.println("PASS: " + testName);
        } else {
            System.out.println("FAIL: " + testName);
        }
    }
}