package Week8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenAIService {

    private final String apiKey;

    public OpenAIService() {

        apiKey = System.getenv("OPENAI_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException(
                "OPENAI_API_KEY was not found."
            );
        }
    }

    public String sendRequest(String prompt) throws Exception {

        URL url = new URL(
            "https://api.openai.com/v1/responses"
        );

        HttpURLConnection connection =
            (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty(
            "Content-Type",
            "application/json"
        );

        connection.setRequestProperty(
            "Authorization",
            "Bearer " + apiKey
        );

        connection.setDoOutput(true);

        String json =
            "{"
            + "\"model\":\"gpt-5\","
            + "\"input\":\""
            + escapeJson(prompt)
            + "\""
            + "}";

        try (OutputStream outputStream =
                 connection.getOutputStream()) {

            outputStream.write(
                json.getBytes("UTF-8")
            );
        }

        int responseCode =
            connection.getResponseCode();

        BufferedReader reader;

        if (responseCode >= 200 &&
            responseCode < 300) {

            reader = new BufferedReader(
                new InputStreamReader(
                    connection.getInputStream(),
                    "UTF-8"
                )
            );

        } else {

            reader = new BufferedReader(
                new InputStreamReader(
                    connection.getErrorStream(),
                    "UTF-8"
                )
            );
        }

        StringBuilder response =
            new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();

        String fullResponse = response.toString();

        int typePosition =
                fullResponse.indexOf("\"type\":\"output_text\"");

        if (typePosition == -1) {
            typePosition =
                    fullResponse.indexOf("\"type\": \"output_text\"");
        }

        if (typePosition == -1) {
            return fullResponse;
        }

        int textStart =
                fullResponse.indexOf("\"text\"", typePosition);

        if (textStart == -1) {
            return fullResponse;
        }

        textStart =
                fullResponse.indexOf(":", textStart) + 1;

        // Skip spaces and the opening quote
        while (textStart < fullResponse.length() &&
               Character.isWhitespace(fullResponse.charAt(textStart))) {
            textStart++;
        }

        if (fullResponse.charAt(textStart) == '"') {
            textStart++;
        }

        StringBuilder extractedText =
                new StringBuilder();

        boolean escaped = false;

        for (int i = textStart;
             i < fullResponse.length();
             i++) {

            char c = fullResponse.charAt(i);

            if (escaped) {

                if (c == 'n') {
                    extractedText.append('\n');
                }
                else if (c == 'r') {
                    extractedText.append('\r');
                }
                else if (c == '"') {
                    extractedText.append('"');
                }
                else if (c == '\\') {
                    extractedText.append('\\');
                }
                else {
                    extractedText.append(c);
                }

                escaped = false;

            }
            else if (c == '\\') {

                escaped = true;

            }
            else if (c == '"') {

                break;

            }
            else {

                extractedText.append(c);
            }
        }

        String aiText = extractedText.toString();

        System.out.println("\n--- AI TEXT RECEIVED ---");
        System.out.println(aiText);
        System.out.println("--- END AI TEXT ---\n");

        return aiText;
    }

    private String escapeJson(String text) {

        return text
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r");
    }
}