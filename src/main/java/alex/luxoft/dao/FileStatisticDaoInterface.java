package alex.luxoft.dao;

import alex.luxoft.bean.Line;

import java.io.File;
import java.util.List;

public interface FileStatisticDaoInterface {
    void writeIntoFile(List<Line> lineList, String inFile);

    void writeIntoFile(List<Line> lineList, String inFile, File outFile);

    void writeIntoFile(List<Line> lineList, String inFile, String outFileName);

    void writeIntoDB(List<Line> lineList);
}
