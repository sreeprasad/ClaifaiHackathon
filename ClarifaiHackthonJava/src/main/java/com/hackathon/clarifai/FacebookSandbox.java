package com.hackathon.clarifai;


import facebook4j.*;
import facebook4j.auth.AccessToken;

public class FacebookSandbox {

    public static void main(String[] args) {

        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId("163235214107846", "1ce4ed6391188a2443006fe874a409aa");
        facebook.setOAuthPermissions("publish_pages,manage_pages");
        facebook.setOAuthAccessToken(new AccessToken("163235214107846|TIPi9jU-nv2DpjX-W0QwzkHZOfA", null));

        post(facebook);


    }

    public static void post(Facebook facebook) {

        PostUpdate postUpdate = new PostUpdate("test");

        try {
            facebook.postFeed(postUpdate);
        } catch (FacebookException e) {
            e.printStackTrace();
        }
    }
}
