package com.company;

import com.oracle.javafx.jmx.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class has methods concerning http handling
 */

public class HttpUtil {

    final static String endPoint = "https://www.goeuro.com/suggester-api/v2/position/suggest/en-150/";

    // Building the http request and returning result in String
    private static String getJson (String httpRequest) throws IOException {
         HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();
        HttpGet request = new HttpGet(httpRequest);
        org.apache.http.HttpResponse result = httpClient.execute(request);
        return EntityUtils.toString(result.getEntity(), "UTF-8");
    }

    // Turning the String response from getJson method, to a JSONArray
    private static JSONArray getEndPoint(String location) throws JSONException {

        try {
            String json = getJson(endPoint+location);
            JSONArray arr = new JSONArray(json);
            return arr;
        } catch (Exception ex) {
            JSONArray arr = new JSONArray();
            return arr;
        }
    }

    /* Returning an Array list of arrays, to be the data for the CSV file.
     Array list has a pre-filled first array with items: _id, name, type, latitude, longitude.
     The rest of the arrays are added according to the number of results for the searched location.
     */
    public static ArrayList<String[]> getDataFromEndPoint(String location){

        JSONArray locationArrayResults = getEndPoint(location);

        // Array list has a pre-filled first array
        ArrayList<String[]> fullData = new ArrayList<String[]>();
        fullData.add(new String[]{"id", "name", "type", "latitude", "longitude"});

        // According to the number of results for the searched location, adds arrays with data
        if (locationArrayResults.length() > 0) {
            for (int i = 0 ; i < locationArrayResults.length() ; i++) {
                JSONObject singleResult = locationArrayResults.getJSONObject(i);
                fullData.add(getArrayWithDataFromJsonObject(singleResult));
            }
        }
        return fullData;
    }

    // Getting data form JSONObject and placing in an array
    private static String[] getArrayWithDataFromJsonObject(JSONObject obj){
        String id = String.valueOf(obj.getInt("_id"));
        String name = obj.getString("name");
        String type = obj.getString("type");
        JSONObject geoPosition = obj.getJSONObject("geo_position");
        String latitude = String.valueOf(
                geoPosition.getDouble("latitude"));
        String longitude = String.valueOf(
                geoPosition.getDouble("longitude"));
        String[] singleData = {id,name,type,latitude,longitude};
        return singleData;
    }
}
