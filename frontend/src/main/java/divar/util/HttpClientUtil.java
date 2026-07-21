package divar.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import divar.session.SessionManager;

public class HttpClientUtil {

    private static final HttpClient client =
            HttpClient.newHttpClient();

    private HttpClientUtil(){}

    public static String get(String url)
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url)).GET().build();

        return client.send(request,
                HttpResponse.BodyHandlers.ofString()).body();
    }
    public static String getWithToken(String url)
            throws IOException, InterruptedException {

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header(
                                "Authorization",
                                "Bearer " + SessionManager.getToken()
                        )
                        .GET()
                        .build();

        return client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        ).body();
    }
    public static String post(String url,String json)
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                        .header("Content-Type","application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
    public static String postWithToken(String url, String json)
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type","application/json")
                        .header("Authorization", "Bearer " + SessionManager.getToken())
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .build();

        return client.send(request,
                HttpResponse.BodyHandlers.ofString()).body();
    }
}