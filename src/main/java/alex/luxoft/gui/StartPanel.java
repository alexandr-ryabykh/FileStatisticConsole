package alex.luxoft.gui;

import alex.luxoft.bean.Line;
import alex.luxoft.dao.FileStatisticDao;
import alex.luxoft.service.FileParser;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartPanel extends JPanel {

    private JPanel mainPanel = null;
    private JPanel mainCenterArea = null;
    private JPanel centerArea = null;
    private JPanel centerButtonArea = null;

    private JButton addFile = null;
    private JButton addFolder = null;

    private final int COLOR_RED = 20;
    private final int COLOR_GREEN = 35;
    private final int COLOR_BLUE = 73;

    public StartPanel() throws BadLocationException, SQLException {

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(COLOR_RED, COLOR_GREEN, COLOR_BLUE));

        mainCenterArea = new JPanel(new GridBagLayout());
        centerArea = new JPanel(new BorderLayout());
        centerArea.setSize(300, 100);

        centerButtonArea = new JPanel(new GridLayout(2, 1));
        addFile = new JButton("Обработать один файл");
        addFolder = new JButton("Обработать каталог");
        addFile.setFocusable(false);
        addFolder.setFocusable(false);
        addFile.setPreferredSize(new Dimension(300, 50));
        centerButtonArea.add(addFile);
        centerButtonArea.add(addFolder);

        centerArea.add(centerButtonArea, BorderLayout.CENTER);

        mainCenterArea.add(centerArea);
        mainPanel.add(mainCenterArea, new GridBagConstraints(0, 0, 0, 0, 0, 0,
                GridBagConstraints.CENTER,
                GridBagConstraints.CENTER,
                new Insets(0, 0, 0, 0), 0, 0));

        addFile.addActionListener(ae -> {
            FileParser fileParser = new FileParser();
            List<Line> lineList = fileParser.parseFile();

            FileStatisticDao statistic = new FileStatisticDao();
            Object[] options = {"Нет", "Да"};
            int n = JOptionPane.showOptionDialog(null,
                    "Сохранять лог в файл?",
                    "Требуется Ваш выбор",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (n == 1) {
                if (lineList.size() > 0) {
                    statistic.writeIntoFile(lineList, lineList.get(0).getFileName());
                }
            }
            statistic.writeIntoDB(lineList);
        });
        addFolder.addActionListener((ActionEvent ae) -> {
            Object[] options = {"Нет", "Да"};
            int n = JOptionPane.showOptionDialog(null,
                    "Обрабатывать вложенные каталоги?",
                    "Требуется Ваш выбор",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            switch (n) {
                case 0:
                    JFileChooser folderChooserNo = new JFileChooser();
                    folderChooserNo.setCurrentDirectory(new File("D:/"));
                    folderChooserNo.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    folderChooserNo.showOpenDialog(null);
                    File chooseCDirectoryNo = folderChooserNo.getSelectedFile();
                    File[] fileArr = chooseCDirectoryNo.listFiles();
                    assert fileArr != null;
                    for (File f : fileArr) {
                        if (f.isFile()) {
                            System.out.println("Запустили поток: " + f.getAbsolutePath());
                            Thread myThready = new Thread(() -> {
                                FileStatisticDao statistic = new FileStatisticDao();
                                FileParser fileParser = new FileParser(f);
                                statistic.writeIntoDB(fileParser.parseFile());
                            });
                            myThready.start();
                            try {
                                Thread.sleep(100L);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(StartPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    break;
                default:
                    JFileChooser folderChooserYes = new JFileChooser();
                    folderChooserYes.setCurrentDirectory(new File("D:/"));  //
                    folderChooserYes.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    folderChooserYes.showOpenDialog(null);
                    File chooseCDirectoryYes = folderChooserYes.getSelectedFile();
                    try {
                        List<String> fileList = showFilesAndDirectories(chooseCDirectoryYes);
                        for (String file : fileList) {
                            System.out.println("Запустили поток: " + file);
                            Thread myThready = new Thread(() -> {
                                FileStatisticDao statistic = new FileStatisticDao();
                                FileParser fileParser = new FileParser(new File(file));
                                statistic.writeIntoDB(fileParser.parseFile());
                            });
                            myThready.start();
                            try {
                                Thread.sleep(100L);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(StartPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(StartPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        });
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public static List<String> showFilesAndDirectories(File f) throws Exception {
        List<String> outList = new ArrayList<>();
        if (!f.isDirectory()) {
            outList.add(f.getCanonicalPath());
        }

        if (f.isDirectory()) {
            try {
                File[] child = f.listFiles();

                for (int i = 0; i < child.length; i++) {
                    outList.addAll(showFilesAndDirectories(child[i]));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return outList;
    }
}
