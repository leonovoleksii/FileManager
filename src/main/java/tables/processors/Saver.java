package tables.processors;

import tables.visualizers.TableFrame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;

public class Saver {
    public String generateFileContent(TableFrame tableFrame) {
        StringBuilder content = new StringBuilder();
        content.append(tableFrame.getTable().getRowAmount());
        content.append("\n");
        content.append(tableFrame.getTable().getColumnAmount());
        content.append("\n");
        TreeMap<String, String> nameToFormula = tableFrame.getControlPanel().getNameToFormula();
        content.append(nameToFormula.size());
        content.append("\n");
        for (String formula : nameToFormula.keySet()) {
            content.append(formula);
            content.append("\n");
            content.append(nameToFormula.get(formula));
            content.append("\n");
        }
        return content.toString();
    }

    public void save(TableFrame tableFrame) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tableFrame.getFilename()));
            bufferedWriter.write(tableFrame.getTable().getRowAmount() + "\n");
            bufferedWriter.write(tableFrame.getTable().getColumnAmount() + "\n");
            bufferedWriter.flush();
            bufferedWriter.write(tableFrame.getControlPanel().getNameToFormula().size() + "\n");
            for (String name : tableFrame.getControlPanel().getNameToFormula().keySet()) {
                bufferedWriter.write(name + "\n");
                bufferedWriter.write(tableFrame.getControlPanel().getNameToFormula().get(name) + "\n");
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println("Unable to save the file");
        }
    }
}
