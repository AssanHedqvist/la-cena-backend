package se.hedsec.webscraperspring;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;


public class ImageHandler {

    private static final String IMGUR_UPLOAD_URL = "https://api.imgur.com/3/image";
    private static final String IMGUR_API_KEY = "bd80f523c4cf8f6d229b06c95f70f20a87400222"; // Replace with your Imgur Client ID

    public static void generateImage(String prompt) throws IOException, InterruptedException {

        String encodedPrompt = URLEncoder.encode(prompt, StandardCharsets.UTF_8);
        String uri = "https://image.pollinations.ai/prompt/"+encodedPrompt;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() == 200) {
            try (InputStream inputStream = response.body()) {
                Files.copy(inputStream, Path.of("image.jpg"), StandardCopyOption.REPLACE_EXISTING);
            }
            System.out.println("Download Completed");
        } else {
            System.out.println("Failed to download image. Status code: " + response.statusCode());
        }
    }
    public static String uploadImage(String imagePath) throws IOException {
        // Read the image file and convert it to base64
        File imageFile = new File(imagePath);
        byte[] fileContent = Files.readAllBytes(imageFile.toPath());
        String imageBase64 = Base64.getEncoder().encodeToString(fileContent);

        // Create the JSON payload
        String jsonPayload = String.format("{\"image\": \"%s\"}", imageBase64);

        // Create the connection
        URL url = new URL(IMGUR_UPLOAD_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + IMGUR_API_KEY);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Send the request
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonPayload.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            // Parse the JSON response to extract the image URL
            JSONObject jsonResponse = new JSONObject(response.toString());
            if (jsonResponse.getBoolean("success")) {
                JSONObject data = jsonResponse.getJSONObject("data");
                return data.getString("link"); // Return the image URL
            } else {
                throw new IOException("Upload failed: " + jsonResponse.toString());
            }
        } finally {
            connection.disconnect();
        }
    }


    /*


public class ImgurUploader {

    public static String uploadImage(String imagePath) throws IOException {
        // Read the image file and convert it to base64
        File imageFile = new File(imagePath);
        byte[] fileContent = Files.readAllBytes(imageFile.toPath());
        String imageBase64 = Base64.getEncoder().encodeToString(fileContent);

        // Create the JSON payload
        String jsonPayload = String.format("{\"image\": \"%s\"}", imageBase64);

        // Create the connection
        URL url = new URL(IMGUR_UPLOAD_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Client-ID " + CLIENT_ID);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Send the request
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonPayload.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Read the response
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            // Parse the JSON response to extract the image URL
            JSONObject jsonResponse = new JSONObject(response.toString());
            if (jsonResponse.getBoolean("success")) {
                JSONObject data = jsonResponse.getJSONObject("data");
                return data.getString("link"); // Return the image URL
            } else {
                throw new IOException("Upload failed: " + jsonResponse.toString());
            }
        } finally {
            connection.disconnect();
        }
    }
}
     */
}
