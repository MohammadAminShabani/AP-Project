package divar.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import divar.config.AppConfig;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

public class ApiClient {

    private static final HttpClient client =
            HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

    private static final ObjectMapper mapper = new ObjectMapper();

    private ApiClient() {
    }

    public static String get(String endpoint)
            throws IOException, InterruptedException, ApiException {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(AppConfig.BASE_URL + endpoint))
                .GET();

        addAuthorization(builder);

        HttpResponse<String> response =
                client.send(builder.build(), HttpResponse.BodyHandlers.ofString());

        return unwrap(response);
    }

    public static String post(String endpoint, String json)
            throws IOException, InterruptedException, ApiException {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(AppConfig.BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json));

        addAuthorization(builder);

        HttpResponse<String> response =
                client.send(builder.build(), HttpResponse.BodyHandlers.ofString());

        return unwrap(response);
    }

    public static String put(String endpoint, String json)
            throws IOException, InterruptedException, ApiException {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(AppConfig.BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json));

        addAuthorization(builder);

        HttpResponse<String> response =
                client.send(builder.build(), HttpResponse.BodyHandlers.ofString());

        return unwrap(response);
    }

    public static String delete(String endpoint)
            throws IOException, InterruptedException, ApiException {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(AppConfig.BASE_URL + endpoint))
                .DELETE();

        addAuthorization(builder);

        HttpResponse<String> response =
                client.send(builder.build(), HttpResponse.BodyHandlers.ofString());

        return unwrap(response);
    }

    private static void addAuthorization(HttpRequest.Builder builder) {

        String token = TokenManager.getToken();

        if (token != null && !token.isBlank()) {
            builder.header("Authorization", "Bearer " + token);
        }
    }

    /**
     * Returns the body if the response was successful (2xx).
     * Otherwise throws an ApiException with a clean, human-readable
     * message extracted from the backend's error body.
     */
    private static String unwrap(HttpResponse<String> response) throws ApiException {

        int status = response.statusCode();
        String body = response.body();

        if (status >= 200 && status < 300) {
            return body;
        }

        throw new ApiException(status, extractMessage(body));
    }

    @SuppressWarnings("unchecked")
    private static String extractMessage(String body) {

        if (body == null || body.isBlank()) {
            return "خطایی رخ داد. لطفا دوباره تلاش کنید.";
        }

        try {
            Object parsed = mapper.readValue(body, Object.class);

            if (parsed instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) parsed;

                if (map.containsKey("message")) {
                    return String.valueOf(map.get("message"));
                }

                StringBuilder sb = new StringBuilder();
                for (Object value : map.values()) {
                    if (sb.length() > 0) {
                        sb.append("\n");
                    }
                    sb.append(value);
                }
                return sb.toString();
            }

            return String.valueOf(parsed);

        } catch (IOException e) {
            // body wasn't JSON, treat as plain text message
            return body;
        }
    }
}
