package alex.luxoft.service;

import alex.luxoft.util.FileUtils;
import alex.luxoft.Main;
import alex.luxoft.bean.Line;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileParser implements FileParserInterface {

    private File file;
    private Set<String> globalWordSet = new HashSet<>();
    private int globalMinWordLength = Integer.MAX_VALUE;
    private int globalMaxWordLength = -1;
    private String globalMinWord;
    private String globalMaxWord;
    private int lineNumber = 0;

    public FileParser() {
        this.file = FileUtils.getFile("Выберите файл для разбора статистики");
    }

    public FileParser(File file) {
        this.file = file;
    }

    @Override
    public List<Line> parseFile() {
        System.out.println("parseFile()");
        List<Line> outList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file), "cp1251"/*StandardCharsets.UTF_8*/))) {
            Long fileId = new Date().getTime();
            String fileLine;
            while ((fileLine = reader.readLine()) != null) {
                lineNumber++;
                int lineLength = fileLine.length();
                if (lineLength > 0) {
                    String[] words = fileLine.split(" ");
                    Set<String> wordSet = new HashSet<>();
                    int minWordLength = Integer.MAX_VALUE;
                    int maxWordLength = -1;
                    String minWord = null;
                    String maxWord = null;
                    for (String word : words) {
                        if (word.length() > 0) {
                            if (word.length() < minWordLength) {
                                minWordLength = word.length();
                                minWord = word;
                            }
                            if (word.length() > maxWordLength) {
                                maxWordLength = word.length();
                                maxWord = word;
                            }
                            wordSet.add(word);
                            if (word.length() < globalMinWordLength) {
                                globalMinWordLength = word.length();
                                globalMinWord = word;
                            }
                            if (word.length() > globalMaxWordLength) {
                                globalMaxWordLength = word.length();
                                globalMaxWord = word;
                            }
                            globalWordSet.add(word);
                        }
                    }
                    long wordsLength = 0L;
                    for (String s : wordSet) {
                        wordsLength += s.length();
                    }
                    int averageWordLength;
                    averageWordLength = (int) wordsLength / wordSet.size();
                    Line line = new Line();
                    line.setFileId(fileId);
                    line.setFileName(file.getAbsolutePath());
                    line.setLineNumber(lineNumber);
                    line.setMinWord(minWord);
                    line.setMaxWord(maxWord);
                    line.setMinWordLength(minWordLength);
                    line.setMaxWordLength(maxWordLength);
                    line.setAverageWordLength(averageWordLength);
                    line.setAllWordsLength(wordsLength);
                    line.setWordsCount(wordSet.size());
                    outList.add(line);
                }
            }

        } catch (IOException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
        return outList;
    }

    public Set<String> getGlobalWordSet() {
        return globalWordSet;
    }

    public void setGlobalWordSet(Set<String> globalWordSet) {
        this.globalWordSet = globalWordSet;
    }

    public int getGlobalMinWordLength() {
        return globalMinWordLength;
    }

    public void setGlobalMinWordLength(int globalMinWordLength) {
        this.globalMinWordLength = globalMinWordLength;
    }

    public int getGlobalMaxWordLength() {
        return globalMaxWordLength;
    }

    public void setGlobalMaxWordLength(int globalMaxWordLength) {
        this.globalMaxWordLength = globalMaxWordLength;
    }

    public String getGlobalMinWord() {
        return globalMinWord;
    }

    public void setGlobalMinWord(String globalMinWord) {
        this.globalMinWord = globalMinWord;
    }

    public String getGlobalMaxWord() {
        return globalMaxWord;
    }

    public void setGlobalMaxWord(String globalMaxWord) {
        this.globalMaxWord = globalMaxWord;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

}
