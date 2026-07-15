import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class GitHubServer {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8080),
                0
        );
        

        server.createContext("/github", GitHubServer::getGitHubProfile);

        server.start();

        System.out.println("Java server started...");
        System.out.println("Server running at http://localhost:8080");

    }

    private static void getGitHubProfile(HttpExchange exchange)
            throws IOException {

        addCorsHeaders(exchange);

        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {

            exchange.sendResponseHeaders(204, -1);

            exchange.close();

            return;
        }

        String query = exchange.getRequestURI().getQuery();

        String username = getUsernameFromQuery(query);

        if (username == null || username.isEmpty()) {

            sendResponse(
                    exchange,
                    400,
                    "{\"error\":\"Username is required\"}"
            );

            return;
        }

        try {

            String apiUrl =
                    "https://api.github.com/users/"
                            + username;

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()

                    .uri(URI.create(apiUrl))

                    .header(
                            "Accept",
                            "application/vnd.github+json"
                    )

                    .build();

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() == 200) {

                sendResponse(
                        exchange,
                        200,
                        response.body()
                );

            } else {

                sendResponse(
                        exchange,
                        response.statusCode(),
                        "{\"error\":\"GitHub user not found\"}"
                );

            }

        } catch (Exception e) {

            sendResponse(
                    exchange,
                    500,
                    "{\"error\":\"Server error\"}"
            );

        }

    }

    private static String getUsernameFromQuery(String query) {

    if (query == null || query.isEmpty()) {
        return null;
    }

    String[] parameters = query.split("&");

    for (String parameter : parameters) {

        String[] keyValue = parameter.split("=", 2);

        if (keyValue.length == 2 &&
                keyValue[0].equals("username")) {

            return keyValue[1];
        }
    }

    return null;
}

    private static void addCorsHeaders(HttpExchange exchange) {

        exchange.getResponseHeaders().add(
                "Access-Control-Allow-Origin",
                "*"
        );

        exchange.getResponseHeaders().add(
                "Access-Control-Allow-Methods",
                "GET, OPTIONS"
        );

        exchange.getResponseHeaders().add(
                "Access-Control-Allow-Headers",
                "Content-Type"
        );

        exchange.getResponseHeaders().add(
                "Content-Type",
                "application/json"
        );

    }

    private static void sendResponse(
            HttpExchange exchange,
            int statusCode,
            String response
    ) throws IOException {

        byte[] responseBytes =
                response.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(
                statusCode,
                responseBytes.length
        );

        OutputStream outputStream =
                exchange.getResponseBody();

        outputStream.write(responseBytes);

        outputStream.close();

    }

}
