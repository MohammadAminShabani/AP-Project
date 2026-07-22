package divar.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import divar.config.AppConfig;
import divar.session.SessionManager;

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

        String token = SessionManager.getToken();

        if (token != null && !token.isBlank()) {
            builder.header("Authorization", "Bearer " + token);
        }
    }

    /**
     * Returns the body if the response was successful (2xx).
     * Otherwise throws an ApiException with a clean, human-readable
     * message extracted from the backend's error body.
     */
    private static String unwrap(HttpResponse<String> response)
            throws ApiException {

        int status = response.statusCode();

        if (status >= 200 && status < 300) {
            return response.body() == null ? "" : response.body();
        }

        throw new ApiException(status, extractMessage(response.body()));
    }

    private static String extractMessage(String body) {

        if (body == null || body.isBlank()) {
            return "خطایی از سمت سرور دریافت نشد.";
        }

        try {
            Object parsed = mapper.readValue(body, Object.class);

            if (parsed instanceof Map<?, ?> map) {

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
            return body;
        }
    }

    public static String uploadFile(
            String endpoint,
            java.io.File file
    )
            throws IOException,
            InterruptedException,
            ApiException {

        String boundary =
                "----JavaFXBoundary" +
                        System.currentTimeMillis();

        byte[] fileBytes =
                java.nio.file.Files.readAllBytes(
                        file.toPath()
                );

        String fileName =
                file.getName();

        String prefix =
                "--" + boundary + "\r\n" +
                        "Content-Disposition: form-data; " +
                        "name=\"image\"; filename=\"" +
                        fileName +
                        "\"\r\n" +
                        "Content-Type: application/octet-stream\r\n\r\n";

        String suffix =
                "\r\n--" +
                        boundary +
                        "--\r\n";

        byte[] prefixBytes =
                prefix.getBytes(
                        java.nio.charset.StandardCharsets.UTF_8
                );

        byte[] suffixBytes =
                suffix.getBytes(
                        java.nio.charset.StandardCharsets.UTF_8
                );

        byte[] body =
                new byte[
                        prefixBytes.length +
                                fileBytes.length +
                                suffixBytes.length
                        ];

        System.arraycopy(
                prefixBytes,
                0,
                body,
                0,
                prefixBytes.length
        );

        System.arraycopy(
                fileBytes,
                0,
                body,
                prefixBytes.length,
                fileBytes.length
        );

        System.arraycopy(
                suffixBytes,
                0,
                body,
                prefixBytes.length +
                        fileBytes.length,
                suffixBytes.length
        );

        HttpRequest.Builder builder =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        AppConfig.BASE_URL +
                                                endpoint
                                )
                        )
                        .header(
                                "Content-Type",
                                "multipart/form-data; boundary=" +
                                        boundary
                        )
                        .POST(
                                HttpRequest.BodyPublishers
                                        .ofByteArray(body)
                        );

        addAuthorization(builder);

        HttpResponse<String> response =
                client.send(
                        builder.build(),
                        HttpResponse.BodyHandlers
                                .ofString()
                );

        return unwrap(response);
    }
}
