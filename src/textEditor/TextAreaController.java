package textEditor;

import javax.swing.*;
import java.io.*;

public class TextAreaController {
    String filename;
    JTextArea textArea;
    private HTMLSimplifier htmlSimplifier = new HTMLSimplifier();
    private Capitalizer capitalizer = new Capitalizer();

    public TextAreaController(JTextArea textArea, String filename) {
        this.filename = filename;
        this.textArea = textArea;
    }
    public void readFile() {
        String line;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(new File(filename)))){
            while ((line = fileReader.readLine()) != null) {
                textArea.append(line + "\n");
            }
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
        text = text.replaceAll(oldSequence, newSequence);
        textArea.setText(text);
    }

    public void capitalize(String s) {
        textArea.setText(capitalizer.capitalize(s));
    }
}
