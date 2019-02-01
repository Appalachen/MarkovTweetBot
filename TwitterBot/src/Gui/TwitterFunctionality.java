package Gui;

import javafx.fxml.FXML;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterFunctionality {
    private static final ConfigurationBuilder cb= new ConfigurationBuilder();
    protected static String OAuthConsumerKey;
    protected static String OAuthConsumerSecret;
    protected static String OAuthAccessToken;
    protected static String OAuthAccessTokenSecret;
@FXML
    public static void sendTweet(String Text ){
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("6wElQc57O8c0TWSctOwvzb7ZO")
                .setOAuthConsumerSecret("elWfHMyEQMuZ5ie1yYA8uOJDUlY5ErRlbCrku6A2d2HpU6uzEh")
                .setOAuthAccessToken("1088701870518996992-xnXKb1dbIxL70DgNBzaltuTIZaJwqr")
                .setOAuthAccessTokenSecret("L3RRWhB9yd2cz9F3UR5AOHkoRifJT26hXnLPX8koOkkl9");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
//		twitter = TwitterFactory.getSingleton();
        try {
            System.out.println(twitter.getConfiguration());
            Status status = twitter.updateStatus(Text);
            System.out.println("Successfully updated the status to [" + status.getText() + "].");
        } catch (TwitterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
