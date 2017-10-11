package alex.luxoft.util;

import javax.swing.*;
import java.io.File;

public class FileUtils {

    public static File getFile(String dialogName) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(dialogName);
        fileChooser.setCurrentDirectory(new File("D:/"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.showOpenDialog(null);

        File chooseCDirectory = fileChooser.getSelectedFile();

        return chooseCDirectory;
    }
}
