import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Translate implements ActionListener {
	public static final String englishLanguageId = "en";
	public static final String latinLanguageId = "la";
    JFrame frame;
    JPanel panel;
    JButton translateButton;
    JTextArea input;
    JTextArea output;
    
    public Translate() {
        frame = new JFrame();
        panel = new JPanel();
        translateButton = new JButton();
        input = new JTextArea(20, 50);
        output = new JTextArea(20, 50);
        
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Athena's Latin Translator");
        frame.add(panel);
        
        panel.add(input);
        panel.add(translateButton);
        panel.add(output);
        
        translateButton.setText("Latin Translate");
        translateButton.addActionListener(this);
        
        input.setText("Hello World");
        
        frame.pack();
    }

    private String translate(String text) throws IOException {
        
        // ***YOUR OWN SCRIPT URL HERE***
        String googleAppScriptUrl = "https://script.google.com/macros/s/AKfycbzJK-Y8L_PquCAKmYE0kYlA0kac0BUqor0Ak3H5YcYDX6Y_XhZw/exec";
        
        String urlStr = googleAppScriptUrl +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + latinLanguageId +
                "&source=" + englishLanguageId;
        URL url = new URL(urlStr);
        
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        return response.toString();
    }

	@Override
    public void actionPerformed(ActionEvent e) {
        if( e.getSource() == translateButton ) {
            
            // Example output
            // {"sourceText":"Hello World","translatedText":"salve Orbis Terrarum"}
            
            String translatedText = null;
            try {
                translatedText = translate( input.getText() );
                
                // THIS IS NOT QUALITY CODE -- OPTIMIZE
                final String keyword = "\"translatedText\""; 
                int translatedIndex = translatedText.indexOf( keyword ) + keyword.length();
                String t = translatedText.substring( translatedIndex + 2, translatedText.length() - 2);
                
                output.setText( t );
            } catch (IOException e1) {
                output.setText( "ERROR: Unable to translate text" );
                e1.printStackTrace();
            }
        }
    }
}