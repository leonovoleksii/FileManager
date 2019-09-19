package fileManager.components;

import javax.swing.*;
import java.util.ArrayList;

public class HintPanel extends JPanel {
    private ArrayList<JLabel> hintList;

    public HintPanel(ArrayList<String> hints) {
        hintList = new ArrayList<>();
        for (String hint : hints) {
            System.out.println(hint);
            hintList.add(new JLabel(hint));
        }
        for (JLabel label : hintList) {
            add(label);
        }
        // add(new JLabel("Hint 1"));
    }
}
