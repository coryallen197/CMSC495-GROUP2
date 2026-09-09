package Week8;

public class OpenAITest {

    public static void main(String[] args) {

        try {

            OpenAIService ai =
                    new OpenAIService();

            String response =
                    ai.sendRequest(
                            "Say hello to my Java helpdesk application."
                    );

            System.out.println("OpenAI Response:");
            System.out.println(response);

        } catch (Exception e) {

            System.out.println(
                    "Error connecting to OpenAI:"
            );

            e.printStackTrace();
        }
    }
}