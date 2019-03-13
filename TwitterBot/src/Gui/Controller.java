package Gui;


import javafx.scene.control.*;
import javafx.scene.paint.Color;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.Properties;

import static Gui.TwitterFunctionality.sendTweet;

public class Controller implements Runnable {

    private static final String Prop_Src = "C://Users//All Users//props.xml";
    public TextArea MarkovTextField;
    public static Button btn_sendTweet;


    public TextField AnzahlDerWoerter;
    public TextField UrlTextFeld;
    public Button btn_setUrlButton;
    public Button btn_postOnTwitter;

    public Label getLabel_StatusUpdate() {
        return Label_StatusUpdate;
    }

    public void setLabel_StatusUpdate(String label_StatusUpdate) {
        Label_StatusUpdate.setText(label_StatusUpdate);
    }

    public Label Label_StatusUpdate = new Label();


    public Button btn_setAuthKeys;
    public Label label_anzahlZeichen;
    public Button btn_setSentenceLength;
    public TextField setOAuthConsumerKey;
    public TextField setOAuthConsumerSecret;
    public TextField setOAuthAccessToken;
    public TextField setOAuthAccessTokenSecret;
    public Button btn_keysSpeichern;
    public TextField periodTextField;
    public int anzahlDerWoertertest;
    public String markovFunctionalityTest;
    public Button btn_setPeriod;
    public Label lbl_test;
    boolean flag = true;
    static MarkovFunctionality markovFunctionality = new MarkovFunctionality();



    public void markovGenerateButton() {

        if (Integer.parseInt(AnzahlDerWoerter.getText()) < 3) {
            MarkovTextField.setText("Error: anzahl der Wörter eingeben!");

        }

        if (UrlTextFeld.getText().isEmpty()) {
            UrlTextFeld.setText("Error: gültige Url eingeben!");
        }

        String text = markovFunctionality.getGeneratedText();


        MarkovTextField.setText(text);
        checkSentenceLength01();

    }

    public void checkSentenceLength01() {
        int length = MarkovTextField.getText().length();
        if (length > 240) {
            label_anzahlZeichen.setTextFill(Color.RED);

        } else {
            label_anzahlZeichen.setTextFill(Color.BLACK);
        }
        label_anzahlZeichen.setText(length + "/240");
    }

    public void postOnTwitterButton() {
        if (MarkovTextField.getText().length() > 240) {
            Label_StatusUpdate.setText("Tweetlänge überschritten!");
        }
        sendTweet(MarkovTextField.getText());
    }

    public void setUrl() {

        if (UrlTextFeld.getText().contains("http") || UrlTextFeld.getText().contains(".txt")) {
            markovFunctionality.urlText = UrlTextFeld.getText();
            markovFunctionalityTest = UrlTextFeld.getText();
            markovFunctionality.text = null;
            Label_StatusUpdate.setText("Url is set");
        } else {
            Label_StatusUpdate.setText("Url konnte nicht gesetzt werden");
        }


    }


    public void setSatzlaenge() {
        if (AnzahlDerWoerter.getText().matches("\\d{1,3}") && Integer.parseInt(AnzahlDerWoerter.getText()) >= MarkovFunctionality.WORDS_PER_STATE) {
            markovFunctionality.wortlaenge = Integer.parseInt(AnzahlDerWoerter.getText());
            anzahlDerWoertertest = Integer.parseInt(AnzahlDerWoerter.getText());

            setLabel_StatusUpdate("Satzlaenge erfolgreich gesetzt");
        } else {
            Label_StatusUpdate.setText("Bitte nur 1- bis 3-Stellige Zahlen eingeben die größer als " + MarkovFunctionality.WORDS_PER_STATE + " sind");
        }
    }

    /**
     * Erstellen eines Threads um Label nur zeitweise anzuzeigen
     */

    public void setAuthOnStart() {
        Properties props = new Properties();
        try {
            props.loadFromXML(new FileInputStream(Prop_Src));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!props.isEmpty()) {
            setOAuthAccessToken.setText(props.getProperty("OAuthAccessToken"));
            setOAuthAccessTokenSecret.setText(props.getProperty("OAuthAccessTokenSecret"));
            setOAuthConsumerKey.setText(props.getProperty("OAuthConsumerKey"));
            setOAuthConsumerSecret.setText(props.getProperty("OAuthConsumerSecret"));
            TwitterFunctionality.OAuthAccessToken = props.getProperty("OAuthAccessToken");
            TwitterFunctionality.OAuthAccessTokenSecret = props.getProperty("OAuthAccessTokenSecret");
            TwitterFunctionality.OAuthConsumerKey = props.getProperty("OAuthConsumerKey");
            TwitterFunctionality.OAuthConsumerSecret = props.getProperty("OAuthConsumerSecret");


        }
    }


    public void setProperties() {
        Properties prop = new Properties();

        try {
            XMLEncoder encoder = new XMLEncoder(new FileOutputStream(Prop_Src));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            prop.loadFromXML(new FileInputStream(Prop_Src));
        } catch (IOException e) {
            e.printStackTrace();
        }
        prop.setProperty("OAuthAccessToken", setOAuthAccessToken.getText());
        prop.setProperty("OAuthAccessTokenSecret", setOAuthAccessTokenSecret.getText());
        prop.setProperty("OAuthConsumerKey", setOAuthConsumerKey.getText());
        prop.setProperty("OAuthConsumerSecret", setOAuthConsumerSecret.getText());
        try {
            prop.storeToXML(new FileOutputStream(Prop_Src), "OAuthKeys");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setPeriod() {
        Controller controller = new Controller();
        controller.periodTextField = periodTextField;
        controller.MarkovTextField = MarkovTextField;
        controller.AnzahlDerWoerter = AnzahlDerWoerter;
        controller.UrlTextFeld = UrlTextFeld;
        controller.label_anzahlZeichen = label_anzahlZeichen;
        controller.flag = flag;
        controller.checkSentenceLength01();
        (new Thread(controller)).start();

    }


    @Override
    public void run() {
        Long period = Long.parseLong(periodTextField.getText()) * 60000;


        if (!periodTextField.getText().isEmpty() && period >= 1000) {

            if (flag == true) {
                markovGenerateButton();
                sendTweet(MarkovTextField.getText());
                try {
                    Thread.sleep(period);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Thread.currentThread().stop();
            }


        } else {
            Label_StatusUpdate.setText("please fill in a correct number in Milliseconds in the period Field!");
        }
    }

    public void stopPeriodTweet() {
        Thread.currentThread().interrupt();
        flag = false;
        Label_StatusUpdate.setText("Periodisches tweeten abgebrochen.");
    }




}
