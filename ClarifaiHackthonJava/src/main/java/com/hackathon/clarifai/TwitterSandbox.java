package com.hackathon.clarifai;


import com.clarifai.api.Tag;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import facebook4j.internal.org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TwitterSandbox {

    static final String consumerKey ="5iQWtKRdFn8N210NcFnR974IG";
    static final String consumerSecret ="6Zyx6ZCcLLi8Fjt4kxBJ7FMbZFbi1R36Aa43JOSapoda3b8Cz3";
    static final String token ="6698662-pXvfdEPuaAQJZLix6p9ZEBtqxX9ugwekkekiHYrLt2";
    static final String secret ="S8VFKxMgTHr4VRwOYMZXhVmldnPKty7mC9vohaqOcvaHi";

    public static void run(String consumerKey, String consumerSecret, String token, String secret) throws InterruptedException, IOException, JSONException {

        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();

        endpoint.trackTerms(Lists.newArrayList("#EndOfTheWorldNYC"));
        Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);

        // Create a new BasicClient. By default gzip is enabled.
        Client client = new ClientBuilder()
                .hosts(Constants.STREAM_HOST)
                .endpoint(endpoint)
                .authentication(auth)
                .processor(new StringDelimitedProcessor(queue))
                .build();

        // Establish a connection
        client.connect();

        // Do whatever needs to be done with messages
        for (int msgRead = 0; msgRead < 1000; msgRead++) {
            String msg = queue.take();
            System.out.println(msg);
            NameAndFoodUrl nameAndFoodUrl = JSONParserService.parseMessage(msg);
            if (nameAndFoodUrl != null) {
                List<Tag> tags = Sandbox.getListOfTags(nameAndFoodUrl.foodUrl);
                LocuSandbox.getRestaurantName(tags,nameAndFoodUrl.name);
            }

        }

        client.stop();

    }

    public static void main(String[] args) throws IOException, JSONException {
        try {

            TwitterSandbox.run(consumerKey, consumerSecret, token, secret);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}
