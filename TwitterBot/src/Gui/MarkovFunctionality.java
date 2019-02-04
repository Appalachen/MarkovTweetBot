package Gui;

import javafx.fxml.FXML;

import java.util.Scanner;

public class MarkovFunctionality {

    protected static final int WORDS_PER_STATE = 3;
    public static String urlText;
    public static int wortlaenge;

    //Test
    @FXML
    public static String getGeneratedText (){
        long start = System.currentTimeMillis();
        String text;
        TxtFileReader fileDownloader =
                new TxtFileReader(urlText);
        if (urlText.contains("http")) {
            text = fileDownloader.download();
        } else {
            text = fileDownloader.fileReader();
        }
//        https://ia800503.us.archive.org/19/items/josefinemutzenba31284gut/31284-8.txt
//      text = fileDownloader.fileReader();
//        System.out.println(text);

        long end = System.currentTimeMillis();

        outputWithDuration("Downloaded the Text ", start, end);

        start = System.currentTimeMillis();
        String[] words = text.split("(\\s|\\W)+");
//        setWordsToLowerCase(words);
        end = System.currentTimeMillis();

        outputWithDuration("Text preprocessing took ", start, end);

        start = System.currentTimeMillis();
        MarkovChain mc = new MarkovChain(words, WORDS_PER_STATE);
        end = System.currentTimeMillis();

        outputWithDuration("Building Markov chain took ", start, end);


        int sentenceLengthInWords = wortlaenge;

        return concat(mc.compose(sentenceLengthInWords));


    }
    @FXML
    private static void outputWithDuration(String text, long start, long end) {
        System.out.println(text + (end - start) + " milliseconds.");
    }

    private static String concat(String... strings) {
        StringBuilder sb = new StringBuilder();
        String separator = "";

        for (String string : strings) {
            sb.append(separator);
            separator = " ";
            sb.append(string);
        }
        sb.append(".");
        return sb.toString();
    }

    private static void setWordsToLowerCase(String[] words) {
        for (int i = 0; i < words.length; ++i) {
            if ( i<=1){
                words[i]=words[i].toUpperCase();
            }
            else {words[i] = words[i].toLowerCase();}
        }
    }
}
