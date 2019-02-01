package Gui;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TxtFileReader {
    private  String urlText;

    public TxtFileReader(String urlText) {

        this.urlText = Objects.requireNonNull(urlText, "The URL text is null.");
    }

    public String fileReader() {
        FileReader fr = null;
        BufferedReader br=null;
        String text = null;
        try
        {
            String fileName=urlText;
            File file = new File(fileName) ;
            fr = new FileReader(file);
            br = new BufferedReader(fr) ;

            String line ;
            StringBuffer sb = new StringBuffer();
            // systemeigenes Zeilenumbruchszeichen ermitteln
            String sep = System.getProperty("line.separator");

            while( (line=br.readLine()) != null )
                sb.append(line+sep) ;

            System.out.println(sb);  //ganze Datei ausgegeben
            text=sb.toString();
        }

        catch(IOException ex)
        {
            System.out.println(ex);
        }
        finally
        {
            try
            {
                if(br!=null) br.close();
                if(fr!=null) fr.close();
            }
            catch(Exception ex)
            {
            }
        }

        return text;
    }

    public String download() {
        try {
            URL url = new URL(urlText);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                return reader.lines().collect(Collectors.joining(" "));
            } catch (IOException ex) {
                throw new RuntimeException("IO failed", ex);
            }
        } catch (MalformedURLException ex) {
            throw new IllegalStateException("Bad URL", ex);
        }
    }
}