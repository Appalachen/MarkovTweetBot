package Gui;

import javafx.fxml.FXML;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterFunctionality {
    private TwitterFunctionality() {
    }

    static String OAuthConsumerKey;
    static String OAuthConsumerSecret;
    static String OAuthAccessToken;
    static String OAuthAccessTokenSecret;
    static Controller controller = new Controller();
@FXML
static void sendTweet(String Text) {
    ConfigurationBuilder cb = new ConfigurationBuilder();

    cb.setDebugEnabled(true)
            .setOAuthConsumerKey(OAuthConsumerKey)
            .setOAuthConsumerSecret(OAuthConsumerSecret)
            .setOAuthAccessToken(OAuthAccessToken)
            .setOAuthAccessTokenSecret(OAuthAccessTokenSecret);
    TwitterFactory tf = new TwitterFactory(cb.build());
    Twitter twitter = tf.getInstance();
    try {
        if (Text.length() <= 240) {
            twitter.updateStatus(Text);
            controller.setLabelStatusUpdate("Successfully updated the status.");


        } else {
            splitTweets(Text);

        }

    } catch (TwitterException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}

    static void likeFlorentinWill() {
        ConfigurationBuilder cb = new ConfigurationBuilder();

        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(OAuthConsumerKey)
                .setOAuthConsumerSecret(OAuthConsumerSecret)
                .setOAuthAccessToken(OAuthAccessToken)
                .setOAuthAccessTokenSecret(OAuthAccessTokenSecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        try {
            Query query = new Query("Florentin Will");
            QueryResult result = twitter.search(query);
            for (Status status : result.getTweets()) {
                twitter.tweets().retweetStatus(status.getId());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    private static void splitTweets(String text) {
        int numberOfTweets = text.length() / 240;
        controller.setLabelStatusUpdate(Integer.toString(numberOfTweets));
        int restForLastTweet = text.length() % 240;
        for (int i = 0; i < numberOfTweets; i++) {
            String subText = text.substring(240 * i, 240 + 240 * i);
            sendTweet(subText);

        }
        if (restForLastTweet > 0) {
            String subText = text.substring(text.length() - restForLastTweet);
            sendTweet(subText);
        }
    }

    public static void getTweets() {
//        ConfigurationBuilder cb= new ConfigurationBuilder();
//
//        cb.setDebugEnabled(true)
//                .setOAuthConsumerKey(OAuthConsumerKey)
//                .setOAuthConsumerSecret(OAuthConsumerSecret)
//                .setOAuthAccessToken(OAuthAccessToken)
//                .setOAuthAccessTokenSecret(OAuthAccessTokenSecret);
//        TwitterFactory tf = new TwitterFactory(cb.build());
//        Twitter twitter = tf.getInstance();

    }
}
