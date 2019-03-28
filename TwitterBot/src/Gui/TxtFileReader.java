package Gui;

import com.sun.org.apache.bcel.internal.generic.GOTO;
import com.sun.org.apache.bcel.internal.generic.GOTO_W;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import java.util.stream.Collectors;

class TxtFileReader {
    private  String urlText;

    TxtFileReader(String urlText) {

        this.urlText = Objects.requireNonNull(urlText, "The URL text is null.");
    }

    String fileReader() {
        FileReader fr = null;
        BufferedReader br=null;
        String text = null;
        try
        {
            String fileName=urlText;
            File file = new File(fileName);
            fr = new FileReader(file);
            br = new BufferedReader(fr) ;
            String line ;
            StringBuffer sb = new StringBuffer();
            String sep = System.getProperty("line.separator");

            while( (line=br.readLine()) != null )
                sb.append(line+sep) ;

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

    String download() {
        URL url;

        try {
            url = new URL(urlText);

        } catch (MalformedURLException ex) {
            throw new IllegalStateException("Bad URL", ex);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {

            return reader.lines().collect(Collectors.joining(" "));
        } catch (IOException ex) {
            throw new RuntimeException("IO failed", ex);
        }
    }
}