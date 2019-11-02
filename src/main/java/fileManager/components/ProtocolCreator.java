package fileManager.components;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProtocolCreator {

    // hide constructor because it is Singleton
    private ProtocolCreator() {}

    private static ProtocolCreator INSTANCE = null;

    private FileWriter protocolWriter;

    public static final String
            TRANSITION = "TRANSITION",
            CHANGES = "CHANGES",
            ERROR = "ERROR";


    public static ProtocolCreator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProtocolCreator();
            try {
                INSTANCE.protocolWriter = new FileWriter(new File(System.getProperty("user.home"),
                        "log.out"), true);
            } catch (IOException e) {
                System.err.println("Unable to create ProcolCreator");
                return null;
            }
        }
        return INSTANCE;
    }

    public void appendToProtocol(String message, String code) {
        try {
            protocolWriter.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) +
                    ". " + code + ". " + message + "\n");
            protocolWriter.flush();
        } catch (IOException e) {
            System.err.println("Unable to append message to log.out");
        }
    }
}
