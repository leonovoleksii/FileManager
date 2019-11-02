package fileManager;

import fileManager.components.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FileManagerFrame extends JFrame implements Runnable, ActionListener {
    private static final FileManagerFrame INSTANCE = new FileManagerFrame();

    private JMenuItem help;
    private final String helpText = "This is the program that helps manipulate files on your computer\n" +
        "This file manager can perform simple operations like creating, renaming, moving, deleting\n" +
        "and finding files and directories\n\n" +
        "In order to create, delete, rename or move the file or directory, choose the item in one\n" +
        "of the lists and press the corresponding button in the bottom\n" +
        "Select the file and press F3 to edit this file in text editor\n\n" +
        "Варіант13\n" +
        "0.1 - 3 - Пошук файлів, що мають особливості у розташуванні, розмірі, даті та ін. (напр., усі вони є у якомусь каталозі) та виділення слів, що написані латиницею\n" +
        "0.2 - 7 - Спрощення тегів файлів формату HTML (напр., FONT color=#ff0000 замінити на FONT  та ін.)\n" +
        "1.1 - 6 - Заміна однієї послідовності літер іншою.\n" +
        "1.2 - 13 - Перетворити текст, що складається тільки з малих літер у текст, що складається з великих і малих літер. Перша літера і літера після кінця речення - великі, інші - малі. \n" +
        "2 - 5 - Використовуючи шаблон «Одинак» (Singleton), розробіть систему протоколювання подій файлового менеджера. Система повинна: \n" +
        "- підтримувати 3 рівня важливості подій (зміни, перехід, помилка) - забезпечити фіксацію події (з подією фіксуються час, важливість, текстове повідомлення).\n" +
        "Наприклад:\n" +
        "21.09.2017 13:02:22 Зміни. Створено файл c:\\temp\\aaa.txt\n" +
        "21.09.2017 14:02:22 Зміни. Переміщено файл з c:\\temp\\aaa.txt в c: \\aaa.txt\n" +
        "21.09.2017 14:02:22. Перехід. Відкрито директорію c:\\temp\n" +
        "21.09.2017 15:02:22 Помилка. Помилка доступу до файлу з c:\\temp\\ata.txt \n\n" +
        "Developed by Oleksii Leonov\n";
    private final int  START_WIDTH = 1000,
                      START_HEIGHT = 600;

    public static FileManagerFrame getInstance() {
        return INSTANCE;
    }

    public void run() {
        setContentPane(new MainPanel());

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        help = new JMenuItem("About");
        help.addActionListener(this);
        helpMenu.add(help);

        setMinimumSize(new Dimension(START_WIDTH, START_HEIGHT));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == help) {
            JTextArea text = new JTextArea(helpText);
            text.setEditable(false);
            JOptionPane.showMessageDialog(this, text, "Help", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new FileManagerFrame());
    }
}
