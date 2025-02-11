package se.hedsec.webscraperspring;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import se.hedsec.webscraperspring.recipe.Recipe;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Webscraper {

    public static List<String> getVideoUrls(String username) {
        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            Page page = browser.newPage();
            page.navigate("https://www.tiktok.com/@" + username);
            page.waitForSelector("a.css-1mdo0pl-AVideoContainer");

            List<String> videoLinks =
                    (List<String>) page.locator("a.css-1mdo0pl-AVideoContainer").evaluateAll(
                            "elements => elements.map(element => element.href)");

            browser.close();
            return videoLinks;
        }
    }
    public static String fetchVideoDesc(String videoUrl) {
        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate(videoUrl);

            Locator videoDescLocator = page.locator("[data-e2e='browse-video-desc']");
            videoDescLocator.waitFor();
            String videoDesc = videoDescLocator.innerText();
            browser.close();
            return videoDesc;

        } catch (TimeoutError ex) {
            return null;
        }
    }
    public static Recipe createRecipeFromDesc(String videoDesc) throws IOException, InterruptedException {

        String api_key = System.getenv("GEMINI_API_KEY");
        String requestBody = """
                {
                    "contents": [{
                        "parts": [
                            {"text": "Kan du översätta till ett svenskt recept, endast med bokstäver och siffror(inga emojis) med EU metric, name / titeln på receptet kan förbli på engelska: %s"}
                        ]
                    }],
                    "generationConfig": {
                        "response_mime_type": "application/json",
                        "response_schema": {
                            "type": "OBJECT",
                            "properties": {
                                "name": {"type": "STRING"},
                                "ingredients": {"type": "STRING"},
                                "instructions": {"type": "STRING"}
                            }
                        }
                    }
                }
                """.formatted(videoDesc);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + api_key))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String jsonResponse = response.body();
        return parseRecipe(jsonResponse);

    }
    private static Recipe parseRecipe(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readValue(jsonResponse, JsonNode.class);
            String recipeJson = root.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();
            return objectMapper.readValue(recipeJson, Recipe.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
