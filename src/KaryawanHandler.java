import com.google.gson.*;
import com.sun.net.httpserver.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class KaryawanHandler implements HttpHandler {
    private static List<Karyawan> data = new ArrayList<>();
    private static Gson gson = new Gson();
    private static int nextId = 1;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String[] parts = path.split("/");

        switch (method) {
            case "GET" -> handelGet(exchange, parts);
            case "POST" -> handlePost(exchange);
            case "PUT" -> handlePut(exchange, parts);
            case "DELETE" -> handleDelete(exchange, parts);
            default -> sendResponse(exchange, "Method Not Allowed", 405);
        }
    }

    private void handelGet(HttpExchange exchange, String[] parts) throws IOException {
        if(parts.length == 3) {
            int id = Integer.parseInt(parts[2]);
            Karyawan k = data.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
            if(k == null) {
                sendResponse(exchange, "Not Found", 404);
            } else {
                sendResponse(exchange, gson.toJson(k), 200);
            }
        }
        else {
            sendResponse(exchange, gson.toJson(data), 200);
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        Karyawan k = gson.fromJson(isr, Karyawan.class);
        k = new Karyawan(nextId++, k.getNama(), k.getJabatan(), k.getGaji());
        data.add(k);
        sendResponse(exchange, gson.toJson(k), 201);
    }

    private void handlePut(HttpExchange exchange, String[] parts) throws IOException {
        if(parts.length != 3) {
            sendResponse(exchange, "Invalid URL", 400);
            return;
        }
        int id = Integer.parseInt(parts[2]);
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        Karyawan input = gson.fromJson(isr, Karyawan.class);
    
        for (Karyawan k : data) {
            if (k.getId() == id) {
                k.setNama(input.getNama());
                k.setJabatan(input.getJabatan());
                k.setGaji(input.getGaji());
                sendResponse(exchange, gson.toJson(k), 200);
                return;
            }
        }
        sendResponse(exchange, "Not Found", 404);
    }

    private void handleDelete(HttpExchange exchange, String[] parts) throws IOException {
        if (parts.length != 3) {
            sendResponse(exchange, "Invalid URL", 400);
            return;
        }
        int id = Integer.parseInt(parts[2]);
        boolean removed = data.removeIf(k -> k.getId() == id);
        if (removed) {
            sendResponse(exchange, "Deleted", 200);
        } else {
            sendResponse(exchange, "Not Found", 404);
        }
    }

    private void sendResponse(HttpExchange exchange, String response, int code) throws IOException {
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(code, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
