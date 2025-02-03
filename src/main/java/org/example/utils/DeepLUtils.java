package org.example.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class DeepLUtils {
    public static final String API_KEY = "4d9bcfdb-79e7-4c9e-9fc9-06f8a5386b46:fx";

    private DeepLUtils(){}
    public static String translateDeepL(String stringaDaTradurre) throws IOException, InterruptedException {

        String translatedText="";
        String url = "https://api-free.deepl.com/v2/translate";
        HttpClient client = HttpClient.newHttpClient();
        String body = String.format("auth_key=%s&text=%s&target_lang=IT", API_KEY, stringaDaTradurre);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String responseBody = response.body();
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray translations = jsonResponse.getJSONArray("translations");
            translatedText = translations.getJSONObject(0).getString("text");
        }
        return translatedText;
    }
}
