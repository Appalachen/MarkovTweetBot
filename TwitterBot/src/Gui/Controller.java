package Gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;

import java.util.Timer;

public class Controller {


    public TextArea MarkovTextField;
    public Button btn_sendTweet;
    public TextField AnzahlDerWoerter;
    public TextField UrlTextFeld;
    public Button btn_setUrlButton;
    public Button btn_postOnTwitter;
    public Label Label_StatusUpdate;
    public PasswordField setOAuthConsumerKey;
    public PasswordField setOAuthConsumerSecret;
    public PasswordField setOAuthAccessToken;
    public PasswordField setOAuthAccessTokenSecret;
    public Button btn_setAuthKeys;

    public void markovGenerateButton(javafx.event.ActionEvent actionEvent) {
        if(Integer.parseInt(AnzahlDerWoerter.getText())< 3){
            MarkovTextField.setText("Error: anzahl der Wörter eingeben!");

        }

        if (UrlTextFeld.getText().isEmpty() ){
                UrlTextFeld.setText("Error: gültige Url eingeben!");
            }

        String text= MarkovFunctionality.getGeneratedText();


        MarkovTextField.setText(text);
    }
    public void postOnTwitterButton(ActionEvent actionEvent){
        TwitterFunctionality.sendTweet(MarkovTextField.getText());
    }
    public void setUrl(){
    MarkovFunctionality.urlText=UrlTextFeld.getText();
    Label_StatusUpdate.setText("Url is set");
    Label_StatusUpdate.setTextFill(Paint.valueOf("#000000"));
    }
    public void setSatzlaenge(){
        if(AnzahlDerWoerter.getText().matches("\\d{1,3}")&& Integer.parseInt(AnzahlDerWoerter.getText())>= MarkovFunctionality.WORDS_PER_STATE){
            MarkovFunctionality.wortlaenge=Integer.parseInt(AnzahlDerWoerter.getText());
        }
   else{
      setLabel_StatusUpdateVisible("Bitte nur 1- bis 3-Stellige Zahlen eingeben die größer als "+ MarkovFunctionality.WORDS_PER_STATE+ " sind");
        }
    }
    /**
     * Erstellen eines Threads um Label nur zeitweise anzuzeigen
     */
    public void setLabel_StatusUpdateVisible(String info){
        Label_StatusUpdate.setText(info);
        Long starttime=System.currentTimeMillis();
        if((starttime+4000)<System.currentTimeMillis()){
            Label_StatusUpdate.setText(" ");  // Alternative:InfoLabel.setVisible(false);
        }
    }
    public void setAuthKeys(){
        TwitterFunctionality.OAuthAccessToken= setOAuthAccessToken.getText();
        TwitterFunctionality.OAuthAccessTokenSecret= setOAuthAccessTokenSecret.getText();
        TwitterFunctionality.OAuthConsumerKey= setOAuthConsumerKey.getText();
        TwitterFunctionality.OAuthConsumerSecret= setOAuthConsumerSecret.getText();



    }
}
//"C:/Users/carlalexander.zepke/Documents/Berufsschule/Projekt TweetBot/Josephine_mutzenbacher.txt"