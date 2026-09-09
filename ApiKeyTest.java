package Week8;

public class ApiKeyTest {

    public static void main(String[] args) {

        String apiKey = System.getenv("OPENAI_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {

            System.out.println("API key was NOT found.");

        } else {

            System.out.println("API key was found!");
            System.out.println("Key length: " + apiKey.length());
        }
    }
}