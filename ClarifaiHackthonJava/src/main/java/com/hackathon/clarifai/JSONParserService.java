package com.hackathon.clarifai;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

class NameAndFoodUrl{

    String name;
    String foodUrl;
}

public class JSONParserService {

    static String sampleMsg = "{\"created_at\":\"Sat Aug 13 17:28:46 +0000 2016\",\"id\":764514043419918337,\"id_str\":\"764514043419918337\",\"text\":\"#endoftheworldnyc Cheeese https:\\/\\/t.co\\/7nWUF09FNE\",\"source\":\"\\u003ca href=\\\"http:\\/\\/twitter.com\\/download\\/iphone\\\" rel=\\\"nofollow\\\"\\u003eTwitter for iPhone\\u003c\\/a\\u003e\",\"truncated\":false,\"in_reply_to_status_id\":null,\"in_reply_to_status_id_str\":null,\"in_reply_to_user_id\":null,\"in_reply_to_user_id_str\":null,\"in_reply_to_screen_name\":null,\"user\":{\"id\":238423988,\"id_str\":\"238423988\",\"name\":\"Dennis Yu\",\"screen_name\":\"dyu51\",\"location\":null,\"url\":null,\"description\":null,\"protected\":false,\"verified\":false,\"followers_count\":287,\"friends_count\":817,\"listed_count\":1,\"favourites_count\":634,\"statuses_count\":1340,\"created_at\":\"Sat Jan 15 04:07:35 +0000 2011\",\"utc_offset\":-14400,\"time_zone\":\"Eastern Time (US & Canada)\",\"geo_enabled\":false,\"lang\":\"en\",\"contributors_enabled\":false,\"is_translator\":false,\"profile_background_color\":\"131516\",\"profile_background_image_url\":\"http:\\/\\/abs.twimg.com\\/images\\/themes\\/theme14\\/bg.gif\",\"profile_background_image_url_https\":\"https:\\/\\/abs.twimg.com\\/images\\/themes\\/theme14\\/bg.gif\",\"profile_background_tile\":true,\"profile_link_color\":\"009999\",\"profile_sidebar_border_color\":\"EEEEEE\",\"profile_sidebar_fill_color\":\"EFEFEF\",\"profile_text_color\":\"333333\",\"profile_use_background_image\":true,\"profile_image_url\":\"http:\\/\\/pbs.twimg.com\\/profile_images\\/1720423229\\/63063_1272915266391_1335180574_31265173_1819796_n_normal.jpg\",\"profile_image_url_https\":\"https:\\/\\/pbs.twimg.com\\/profile_images\\/1720423229\\/63063_1272915266391_1335180574_31265173_1819796_n_normal.jpg\",\"default_profile\":false,\"default_profile_image\":false,\"following\":null,\"follow_request_sent\":null,\"notifications\":null},\"geo\":null,\"coordinates\":null,\"place\":null,\"contributors\":null,\"is_quote_status\":false,\"retweet_count\":0,\"favorite_count\":0,\"entities\":{\"hashtags\":[{\"text\":\"endoftheworldnyc\",\"indices\":[0,17]}],\"urls\":[],\"user_mentions\":[],\"symbols\":[],\"media\":[{\"id\":764514035677159425,\"id_str\":\"764514035677159425\",\"indices\":[26,49],\"media_url\":\"http:\\/\\/pbs.twimg.com\\/media\\/CpwZdwJWYAEkKwi.jpg\",\"media_url_https\":\"https:\\/\\/pbs.twimg.com\\/media\\/CpwZdwJWYAEkKwi.jpg\",\"url\":\"https:\\/\\/t.co\\/7nWUF09FNE\",\"display_url\":\"pic.twitter.com\\/7nWUF09FNE\",\"expanded_url\":\"http:\\/\\/twitter.com\\/dyu51\\/status\\/764514043419918337\\/photo\\/1\",\"type\":\"photo\",\"sizes\":{\"medium\":{\"w\":900,\"h\":1200,\"resize\":\"fit\"},\"thumb\":{\"w\":150,\"h\":150,\"resize\":\"crop\"},\"small\":{\"w\":510,\"h\":680,\"resize\":\"fit\"},\"large\":{\"w\":1536,\"h\":2048,\"resize\":\"fit\"}}}]},\"extended_entities\":{\"media\":[{\"id\":764514035677159425,\"id_str\":\"764514035677159425\",\"indices\":[26,49],\"media_url\":\"http:\\/\\/pbs.twimg.com\\/media\\/CpwZdwJWYAEkKwi.jpg\",\"media_url_https\":\"https:\\/\\/pbs.twimg.com\\/media\\/CpwZdwJWYAEkKwi.jpg\",\"url\":\"https:\\/\\/t.co\\/7nWUF09FNE\",\"display_url\":\"pic.twitter.com\\/7nWUF09FNE\",\"expanded_url\":\"http:\\/\\/twitter.com\\/dyu51\\/status\\/764514043419918337\\/photo\\/1\",\"type\":\"photo\",\"sizes\":{\"medium\":{\"w\":900,\"h\":1200,\"resize\":\"fit\"},\"thumb\":{\"w\":150,\"h\":150,\"resize\":\"crop\"},\"small\":{\"w\":510,\"h\":680,\"resize\":\"fit\"},\"large\":{\"w\":1536,\"h\":2048,\"resize\":\"fit\"}}}]},\"favorited\":false,\"retweeted\":false,\"possibly_sensitive\":false,\"filter_level\":\"low\",\"lang\":\"en\",\"timestamp_ms\":\"1471109326592\"}";

    public static NameAndFoodUrl parseMessage (String msg) {


        try {

            JsonElement jelement = new JsonParser().parse(msg);
            JsonObject jobject = jelement.getAsJsonObject();
            JsonArray media = jobject.getAsJsonObject("entities").getAsJsonArray("media");
            JsonObject mediaObject = media.get(0).getAsJsonObject();

            NameAndFoodUrl nameAndFoodUrl = new NameAndFoodUrl();
            nameAndFoodUrl.name = jobject.getAsJsonObject().get("user").getAsJsonObject().get("screen_name").getAsString();
            nameAndFoodUrl.foodUrl = mediaObject.get("media_url").getAsString();
            return nameAndFoodUrl;

        } catch(Exception e) {

            return null;
        }

    }

    public static void main(String[] args) {

        NameAndFoodUrl nameAndFoodUrl = parseMessage(sampleMsg);
        System.out.println(nameAndFoodUrl.name);
        System.out.println(nameAndFoodUrl.foodUrl);
    }
}
