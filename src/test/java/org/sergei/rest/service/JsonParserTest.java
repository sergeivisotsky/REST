package org.sergei.rest.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonParserTest {

    @Ignore
    @Test
    public void parseJSONTest() throws IOException, JSONException {
        URL url = new URL("http://localhost:8080/rest/api/v1/orders/123");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        while ((output = br.readLine()) != null) {
            JSONObject jsonObject = new JSONObject(output);
            JSONArray orderDetailsFromJSON = jsonObject.getJSONArray("orderDetailsDTO");

            System.out.println((orderDetailsFromJSON.getJSONObject(0).getString("productCode")));
            Assert.assertEquals("LV_567", (orderDetailsFromJSON.getJSONObject(0).getString("productCode")));
            System.out.println(output);
        }
    }

    @Test
    public void parseJSONWithJacksonText() {

    }
}
