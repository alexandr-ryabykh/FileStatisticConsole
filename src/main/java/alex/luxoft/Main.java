package alex.luxoft;

import alex.luxoft.gui.MainFrame;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.context.i18n.LocaleContextHolder.setLocale;

public class Main {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("beans.xml");
        try {
            Locale loc = new Locale("ru", "RU");
            setLocale(loc);
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            new InitMainFrame().run();
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
        }

    }

    public static class InitMainFrame implements Runnable {

        public InitMainFrame() {
        }

        @Override
        public void run() {
            try {
                new MainFrame();
            } catch (BadLocationException | SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
