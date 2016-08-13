# ClaifaiHackathon

## Inspiration
In an attempt to assist our robot overlords, we looked to the Dancing Plague of 1518 [link](https://en.wikipedia.org/wiki/Dancing_Plague_of_1518) and the idea of automated ordering to keep the party going.

## What it does
A user will take a photo of food they are having and post to twitter with the hashtag #endoftheworldNYC. We will then ingest the photo and using Clarifai determine what food the user is eating. After we identify the food, we will order more food of the same type from a local restaurant to the user's location and provide a tweet confirmation once the order has been placed. 

## How we built it
We parsed for tweets containing the hashtag _#endoftheworldNYC_, retrieved the image URL, and sent the image URL to Clarifai to determine the type of food. Once we determined the food type from Clarifai, we use the Locu API to determine the closest restaurant that sells that food and using an ordering service, order the food and send a tweet via Twitter Bot to the user indicating the transaction has been placed. 

## Challenges we ran into
The locu API was not well documented and we ran into issues using location data to find relevant restaurant matches within the radius of the user

## Accomplishments that we're proud of
We were able to create a transaction process using only Twitter and images

## What we learned
Location based data APIs can be extremely difficult to use
Clarifai's API was able to fairly accurately determine a generic food type but not brands

## What's next for EndoftheWorld
Adjust order based on previous order (ie Stronger Alcohol)
Parse brand labels (ie Budweiser vs “Beer”) 
Buy products vs food and drink (ie Beach ball)
