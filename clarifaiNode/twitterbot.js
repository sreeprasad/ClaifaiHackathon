const Twit = require('twit');
const express = require('express');
const bodyParser = require("body-parser");
const secrets = require('./secrets.json');

var app = express();
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

var T = new Twit({
    consumer_key: secrets.twitter.consumer_key,
    consumer_secret: secrets.twitter.consumer_secret,
    access_token: secrets.twitter.access_token,
    access_token_secret: secrets.twitter.access_token_secret
});


function send_tweet(tweet_body, success_cb, failure_cb, wrong_key_cb) {
    console.log("got wacky")
    console.log("tweeting this: \"" + tweet_body + '".');

    T.post('statuses/update', { status: tweet_body }, function(err, data, response) {
        if(err) {
            console.log("There was a problem tweeting the message.", err);
            return failure_cb(err);
        }
            success_cb();
    });
}

app.post('/', function(req, res) {
    // console.log(req);
    var data = req.body;
    console.log('got request', data);

    if (data.access_token !== secrets.end_of_the_world_bot.access_token) {
        return res.status(333).end("Your app key is not authorized.");
    }

    send_tweet(data.tweet, function(){
        res.end("Got it.");
    }, function(err) {
        res.end("error." + err);
    });
});

app.listen(3000, function() {
    console.log("running on port 3000");
});
