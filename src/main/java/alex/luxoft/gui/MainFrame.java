package alex.luxoft.gui;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.sql.SQLException;
import java.util.Locale;

public class MainFrame extends JFrame {

    private final int FRAME_WIDTH;
    private final int FRAME_HEIGHT;
    private final int LOCATION_X;
    private final int LOCATION_Y;
    private static Container mainContainer = null;
    private static JPanel startPanel = null;

    public MainFrame() throws BadLocationException, SQLException {

        Locale loc = new Locale("ru", "RU");
        setLocale(loc);
        getInputContext().selectInputMethod(loc);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        FRAME_WIDTH = screenSize.width / 2 - 100;
        FRAME_HEIGHT = (int) (rect.getBounds().getHeight() - getSize().getHeight()) / 2 - 200;
        LOCATION_X = FRAME_WIDTH / 2;
        LOCATION_Y = FRAME_HEIGHT / 2;

        startPanel = new StartPanel().getPanel();

        mainContainer = getContentPane();
        mainContainer.setLayout(new BorderLayout());
        setTitle("Сбор статистики файлов");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocation(LOCATION_X, LOCATION_Y);
        setMinimumSize(new Dimension(800, 600));
        setResizable(false);
        setVisible(true);
        mainContainer.add(startPanel, BorderLayout.CENTER, 0);
    }

}
