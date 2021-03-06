package Gui;

import javafx.fxml.FXML;


class MarkovFunctionality {

    static final int WORDS_PER_STATE = 3;
    String urlText;
    int wortlaenge;
    String text = null;
    private long end = System.currentTimeMillis();
    static Controller controller = new Controller();

    String getGeneratedText() {
        long start = System.currentTimeMillis();

        TxtFileReader fileDownloader =
                new TxtFileReader(urlText);

        if (text == null) {
            if (urlText.contains("http")) {
                text = fileDownloader.download();

            } else {
                text = fileDownloader.fileReader();
            }
        }


        outputWithDuration("Downloaded the Text ", start, end);

            start = System.currentTimeMillis();
        String[] words = text.split("(\\d\\S\\d|\\s|\\d|\\W\\w\\W)+");
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
        controller.labelStatusUpdate.setText(text + (end - start) + " milliseconds.");
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

}
