package textEditor;

import javax.swing.text.JTextComponent;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextAreaController {
    String filename;
    JTextComponent textArea;
    private HTMLSimplifier htmlSimplifier = new HTMLSimplifier();
    private Capitalizer capitalizer = new Capitalizer();

    public TextAreaController(JTextComponent textArea, String filename) {
        this.filename = filename;
        this.textArea = textArea;
    }
    public void readFile() {
        String line;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(new File(filename)))){
            StringBuilder sb = new StringBuilder();
            while ((line = fileReader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            textArea.setText(sb.toString());
        } catch (IOException e) {
            System.err.println("Unable to read file " + filename);
        }
    }
    public void save() {
        File file = new File(filename);
        if (file.exists() && file.delete()) {
            try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file))){
                fileWriter.write(textArea.getText());
                fileWriter.flush();
            } catch (IOException e) {
                System.err.println("Unable to write in " + filename);
            }
        }
    }

    public void simplify() {
        String newText = htmlSimplifier.simplify(textArea.getText());
        textArea.setText(newText);
    }

    public void replace(String oldSequence, String newSequence) {
        String text = textArea.getText();
        Matcher matcher = Pattern.compile(oldSequence, Pattern.LITERAL).matcher(text);
        text = matcher.replaceAll(newSequence);
        textArea.setText(text);
    }

    public void capitalize(String s) {
        textArea.setText(capitalizer.capitalize(s));
    }
}
