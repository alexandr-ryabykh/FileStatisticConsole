package alex.luxoft.dao;

import alex.luxoft.bean.Line;
import alex.luxoft.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Types;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository("fileStatisticDao")
public class FileStatisticDao implements FileStatisticDaoInterface {

    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        FileStatisticDao.jdbcTemplate = jdbcTemplate;
    }

    public FileStatisticDao() {
    }

    @Override
    public void writeIntoFile(List<Line> lineList, String inFile) {
        boolean goodFile = true;
        do {
            File file = FileUtils.getFile("Введите название файл для вывода результата.");
            if (!file.getAbsolutePath().equals(inFile)) {
                writeIntoFile(lineList, inFile, file);
                goodFile = false;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Нельзя вывести лог в самого себя.",
                        "Внимание!",
                        JOptionPane.WARNING_MESSAGE);
            }
        } while (goodFile);
    }

    @Override
    public void writeIntoFile(List<Line> lineList, String inFile, String outFileName) {
        if (!outFileName.equals(inFile)) {
            writeIntoFile(lineList, inFile, new File(outFileName));
        }
    }

    @Override
    public void writeIntoFile(List<Line> lineList, String inFile, File outFile) {
        try {
            FileOutputStream os = new FileOutputStream(outFile);
            os.write("\n".getBytes());
            for (Line line : lineList) {
                os.write(line.toString().getBytes());
                os.write("\n".getBytes());
            }
        } catch (IOException ex) {
            Logger.getLogger(FileStatisticDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void writeIntoDB(List<Line> lineList) {

        String fileName = null;
        if (lineList.size() > 0) {
            fileName = lineList.get(0).getFileName();
            /*jdbcTemplate.update("DELETE file_statistic FROM file_statistic WHERE FILE_NAME = ? ",
                    new Object[]{fileName},
                    new int[]{Types.VARCHAR});*/
        }

        for (Line line : lineList) {
            try {
                String SQL_QUERY = "INSERT INTO file_statistic (               "
                        + "  FILE_ID,       "
                        + "  FILE_NAME,     "
                        + "  LINE_NUMBER,   "
                        + "  MIN_WORD,      "
                        + "  MAX_WORD,      "
                        + "  MIN_WORD_LEN,  "
                        + "  MAX_WORD_LEN,  "
                        + "  AVG_WORD_LEN,  "
                        + "  ALL_WORD_LEN,  "
                        + "  WORDS_CNT      "
                        + " )               "
                        + "VALUES (?,?,?,?,?,?,?,?,?,?)";
                jdbcTemplate.update(SQL_QUERY,
                        new Object[]{line.getFileId(), line.getFileName(), line.getLineNumber(), line.getMinWord(),
                                line.getMaxWord(), line.getMinWordLength(), line.getMaxWordLength(),
                                line.getAverageWordLength(), line.getAllWordsLength(), line.getWordsCount()},
                        new int[]{Types.BIGINT, Types.VARCHAR, Types.INTEGER, Types.VARCHAR,
                                Types.VARCHAR, Types.INTEGER, Types.INTEGER,
                                Types.INTEGER, Types.BIGINT, Types.INTEGER});
            } catch (DataAccessException e) {
                Logger.getLogger(FileStatisticDao.class.getName()).log(Level.WARNING, "Ошибка добавления строки № {0} файла {1}.", new Object[]{line.getLineNumber(), line.getFileName()});
                System.out.println("Ошибка добавления строки № " + line.getLineNumber() + " файла " + line.getFileName() + " в базу данных.");
            }
        }
        Logger.getLogger(FileStatisticDao.class.getName()).log(Level.INFO, "В базу данных добавлена информация по файлу {0}, вставлено {1} строк.", new Object[]{fileName, lineList.size()});
    }


}
