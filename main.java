import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiRequest {
    public static void main(String[] args) {
        String nida = "Your nida";
        String apiKey = "Your api key"; // not important
        String token = "Your token";    // not important
        String accountId = "Your account id";

        String apiUrl = "https://kabukukidigitali.xyz/nida/api/index.php";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("api-key", apiKey);
            conn.setRequestProperty("token", token);
            conn.setRequestProperty("account_id", accountId);
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // JSON body
            String jsonInputString = String.format("{\"nida\": \"%s\"}", nida);

            // Send JSON input
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read response
            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                try (Scanner scanner = new Scanner(conn.getInputStream())) {
                    String responseBody = scanner.useDelimiter("\\A").next();
                    System.out.println("Response: " + responseBody);
                }
            } else {
                System.out.println("Error: HTTP response code " + status);
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
