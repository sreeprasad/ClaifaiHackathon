package com.hackathon.clarifai;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Created by sreeprasad on 8/13/16.
 */
public class LocuJsonParser {

    static String message = "" ;

    public static void main(String[] args) {



        JsonElement jelement = new JsonParser().parse(message);
        JsonArray jsonArray = jelement.getAsJsonArray();


    }
}
