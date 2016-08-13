package com.hackathon.clarifai;

import com.clarifai.api.Tag;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;
import java.util.Set;

class Venue {

    String name;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Venue venue = (Venue) o;

        return name != null ? name.equals(venue.name) : venue.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}


public class LocuSandbox {

    public static void main(String[] args) throws IOException, JSONException {


        getRestaurantName(null, "sreeprasad");

    }

    public static void getRestaurantName(List<Tag> tags, String screeName) throws IOException, JSONException {


        for( Tag tag: tags) {


            Set<Venue> venuesToGo  = postV2(tag.getName());

            URL url = new URL("http://localhost:3000");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            JSONObject json = new JSONObject();
            System.out.println("tag prob "+tag.getProbability());
            json.put("tweet",".@"+screeName+" your "+tag.getName()+ " is being delivered from "+venuesToGo.iterator().next().name);
            json.put("access_token","kljdGF7ljd4sfU8Ssa5doiShgpvkSfio31vldk1231n");

            writer.write(json.toString());
            writer.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

            String line = in.readLine();
            if (line != null && line.equals("Got it.")) {
                System.out.println(line);
                break;
            }

        }


    }


    public static Set<Venue> postV2(String foodName) throws IOException {

        Set<Venue> venuesToGo = Sets.newHashSet();

        URL url = new URL("https://api.locu.com/v2/venue/search");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");

        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(createJSON().toString());
        writer.flush();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String line = in.readLine();
        printVenue(venuesToGo, line,foodName);

        return venuesToGo;


    }

    private static void printVenue(Set<Venue> venuesToGo, String line,String foodName) {

        JsonElement jelement = new JsonParser().parse(line);
        JsonArray venues = jelement.getAsJsonObject().get("venues").getAsJsonArray();

        for(JsonElement elem : venues) {

            for(JsonElement menu : elem.getAsJsonObject().get("menus").getAsJsonArray()){

                 JsonArray sections = menu.getAsJsonObject().get("sections").getAsJsonArray();
                for(JsonElement section : sections.getAsJsonArray()) {

                    for(JsonElement subsection : section.getAsJsonObject().get("subsections").getAsJsonArray()){
                        for(JsonElement content : subsection.getAsJsonObject().get("contents").getAsJsonArray()) {
                            JsonElement nameElement = content.getAsJsonObject().get("name");



                            if (nameElement != null) {
                                String check = nameElement.getAsString().toLowerCase().toLowerCase();
                                if (check.contains(foodName)) {
                                    Venue venue = new Venue();
                                    venue.name = elem.getAsJsonObject().get("name").getAsString();
                                    venuesToGo.add(venue);
                                }
                            }
                        }
                    }
                    if(venuesToGo.size()>=2) {
                        break;
                    }
                }
                if(venuesToGo.size()>=2) {
                    break;
                }

            }
            if(venuesToGo.size()>=2) {
                break;
            }
        }


    }


    private static JSONObject createJSON(){
        RestaurantInfo restaurantInfo = new RestaurantInfo();
        try {
            JSONObject json = new JSONObject();

            json.put("api_key", "dc2f913af71837f909719394918a668f6b27c137");


            JSONArray jsonArr = new JSONArray();
            jsonArr.put("name");
            jsonArr.put("menus");
            jsonArr.put("location");
            json.put("fields", jsonArr);

            JSONObject queryObject = new JSONObject();

            jsonArr = new JSONArray();
            jsonArr.put(40.759366);
            jsonArr.put(-73.985136);
            jsonArr.put(5);
            JSONObject locObject = new JSONObject();
            locObject.put("$in_lat_lng_radius", jsonArr);

            JSONObject loc2Object = new JSONObject();


            loc2Object.put("address1", "335 west 29 street");
            loc2Object.put("locality", "New York");
            loc2Object.put("region", "NY");
            loc2Object.put("postal_code", "10001");
            loc2Object.put("geo", locObject);

            queryObject.put("location", loc2Object);

            jsonArr = new JSONArray();
            jsonArr.put(queryObject);






            JSONObject hasMenu = new JSONObject();
            hasMenu.put( "$present",true);
            JSONObject menus = new JSONObject();
            menus.put("menus",hasMenu);
            jsonArr.put(menus);

            json.put("venue_queries", jsonArr);


            jsonArr = new JSONArray();
            queryObject = new JSONObject();
            queryObject.put("type", "ITEM");
            jsonArr.put(queryObject);






            //json.put("menu_item_queries", jsonArr);
            System.out.println(json.toString());
            return json;

        }catch(JSONException e){
            e.printStackTrace();
        }

        return null;
    }
}
