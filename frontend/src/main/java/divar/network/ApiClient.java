package divar.network;

import divar.config.AppConfig;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ApiClient {

    private static final HttpClient client =
            HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

    private ApiClient() {
    }

    public static String get(String endpoint)
            throws IOException, InterruptedException {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(AppConfig.BASE_URL + endpoint))
                .GET();

        addAuthorization(builder);

        HttpResponse<String> response =
                client.send(builder.build(), HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public static String post(String endpoint, String json)
            throws IOException, InterruptedException {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(AppConfig.BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json));

        addAuthorization(builder);

        HttpResponse<String> response =
                client.send(builder.build(), HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public static String put(String endpoint, String json)
            throws IOException, InterruptedException {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(AppConfig.BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json));

        addAuthorization(builder);

        HttpResponse<String> response =
                client.send(builder.build(), HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public static String delete(String endpoint)
            throws IOException, InterruptedException {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(AppConfig.BASE_URL + endpoint))
                .DELETE();

        addAuthorization(builder);

        HttpResponse<String> response =
                client.send(builder.build(), HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    private static void addAuthorization(HttpRequest.Builder builder) {

        String token = TokenManager.getToken();

        if (token != null && !token.isBlank()) {
            builder.header("Authorization", "Bearer " + token);
        }
    }
}