package Gui;

import javafx.fxml.FXML;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterFunctionality {
    private TwitterFunctionality() {
    }
    protected static String OAuthConsumerKey;
    protected static String OAuthConsumerSecret;
    protected static String OAuthAccessToken;
    protected static String OAuthAccessTokenSecret;
    static Controller controller = new Controller();
@FXML
public static void sendTweet(String Text ){
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
            controller.setLabel_StatusUpdate("Successfully updated the status.");
        } else {
            splitTweets(Text);

        }

    } catch (TwitterException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}

    private static void splitTweets(String text) {
        int numberOfTweets = text.length() / 240;
        controller.setLabel_StatusUpdate(Integer.toString(numberOfTweets));
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
