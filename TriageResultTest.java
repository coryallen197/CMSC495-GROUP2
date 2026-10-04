package Week8;

public class TriageResultTest {

    private static int testsRun = 0;
    private static int testsPassed = 0;

    public static void main(String[] args) {

        TriageResult result = new TriageResult(
                "Network",
                "High",
                "User cannot connect to Wi-Fi.",
                "Network Support"
        );

        test(
                "Category getter",
                "Network".equals(result.getCategory())
        );

        test(
                "Priority getter",
                "High".equals(result.getPriority())
        );

        test(
                "Summary getter",
                "User cannot connect to Wi-Fi."
                        .equals(result.getSummary())
        );

        test(
                "Department getter",
                "Network Support".equals(result.getDepartment())
        );

        String expected =
                "Category: Network" +
                        "\nPriority: High" +
                        "\nSummary: User cannot connect to Wi-Fi." +
                        "\nDepartment: Network Support";

        test(
                "toString output",
                expected.equals(result.toString())
        );

        System.out.println();
        System.out.println(
                "Tests passed: " + testsPassed + "/" + testsRun
        );

        if (testsPassed != testsRun) {
            System.exit(1);
        }
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