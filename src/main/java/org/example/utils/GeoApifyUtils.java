package org.example.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeoApifyUtils {
    static String apiKey = "4769cc0f976c430fbe66abdf8e32bb70";

    private GeoApifyUtils(){}


    public static double[] addressToCoordinatesDouble(String address) throws IOException, InterruptedException {

        double[] coordinates= new double[2];

        JsonArray features = doCoordinatesRequest(address);


        for (var feature : features) {
            JsonObject properties = feature.getAsJsonObject().getAsJsonObject("properties");

             coordinates[0] = properties.get("lat").getAsDouble();
             coordinates[1] = properties.get("lon").getAsDouble();


        }
        return coordinates;
    }

    private static JsonArray doCoordinatesRequest(String address) throws IOException, InterruptedException {
        String url = String.format(
                "https://api.geoapify.com/v1/geocode/search?text=%s&apiKey=%s",
                address.replace(" ", "%20"), apiKey
        );


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();
        return root.getAsJsonArray("features");

    }

    public static String getPOIFromCoordinates(double latitude, double longitude) throws IOException, InterruptedException {
        int radius = 3000;
        String pointOfInterests="";

            String url = String.format(
                    "https://api.geoapify.com/v2/places?categories=education.school,education.university,public_transport&filter=circle:%s,%s,%d&limit=10&apiKey=%s",
                    latitude, longitude, radius, apiKey
            );


            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();


            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonArray features = root.getAsJsonArray("features");

            for (var element : features) {
                JsonObject properties = element.getAsJsonObject().getAsJsonObject("properties");
                if(properties.get("name")!=null) {
                    JsonArray categories = properties.getAsJsonArray("categories");
                    String cat = categories.get(1).toString();
                    int dotInd = cat.indexOf(".");
                    cat = cat.substring(dotInd + 1, cat.length() - 1);
                    if(!pointOfInterests.contains(cat))
                        pointOfInterests=pointOfInterests.concat(cat+";");
                }
            }
            pointOfInterests=DeepLUtils.translateDeepL(pointOfInterests);
        return pointOfInterests;
    }
    public static double haversine(double lat1,double lon1,double lat2, double lon2) {

        final int RADIUS = 6371000; // Radius of the earth
        double lat1Radians = Math.toRadians(lat1);
        double lon1Radians = Math.toRadians(lon1);
        double lat2Radians = Math.toRadians(lat2);
        double lon2Radians = Math.toRadians(lon2);
        double deltaLat = lat2Radians - lat1Radians;
        double deltaLon = lon2Radians - lon1Radians;
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1Radians) * Math.cos(lat2Radians)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIUS * c;
    }

}
