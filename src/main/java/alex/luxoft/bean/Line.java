package alex.luxoft.bean;

public class Line {
    private long fileId;
    private String fileName;
    private int lineNumber;
    private String maxWord;
    private String minWord;
    private int minWordLength;
    private int maxWordLength;
    private int averageWordLength;
    private long allWordsLength;
    private int wordsCount;

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getMaxWord() {
        return maxWord;
    }

    public void setMaxWord(String maxWord) {
        this.maxWord = maxWord;
    }

    public String getMinWord() {
        return minWord;
    }

    public void setMinWord(String minWord) {
        this.minWord = minWord;
    }

    public int getMinWordLength() {
        return minWordLength;
    }

    public void setMinWordLength(int minWordLength) {
        this.minWordLength = minWordLength;
    }

    public int getMaxWordLength() {
        return maxWordLength;
    }

    public void setMaxWordLength(int maxWordLength) {
        this.maxWordLength = maxWordLength;
    }

    public int getAverageWordLength() {
        return averageWordLength;
    }

    public void setAverageWordLength(int averageWordLength) {
        this.averageWordLength = averageWordLength;
    }

    public long getAllWordsLength() {
        return allWordsLength;
    }

    public void setAllWordsLength(long allWordsLength) {
        this.allWordsLength = allWordsLength;
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(int wordsCount) {
        this.wordsCount = wordsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        if (fileId != line.fileId) return false;
        if (lineNumber != line.lineNumber) return false;
        if (minWordLength != line.minWordLength) return false;
        if (maxWordLength != line.maxWordLength) return false;
        if (averageWordLength != line.averageWordLength) return false;
        if (allWordsLength != line.allWordsLength) return false;
        if (wordsCount != line.wordsCount) return false;
        if (fileName != null ? !fileName.equals(line.fileName) : line.fileName != null) return false;
        if (maxWord != null ? !maxWord.equals(line.maxWord) : line.maxWord != null) return false;
        return minWord != null ? minWord.equals(line.minWord) : line.minWord == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (fileId ^ (fileId >>> 32));
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + lineNumber;
        result = 31 * result + (maxWord != null ? maxWord.hashCode() : 0);
        result = 31 * result + (minWord != null ? minWord.hashCode() : 0);
        result = 31 * result + minWordLength;
        result = 31 * result + maxWordLength;
        result = 31 * result + averageWordLength;
        result = 31 * result + (int) (allWordsLength ^ (allWordsLength >>> 32));
        result = 31 * result + wordsCount;
        return result;
    }

    @Override
    public String toString() {
        return "Line{" +
                "fileId=" + fileId +
                ", fileName='" + fileName + '\'' +
                ", lineNumber=" + lineNumber +
                ", maxWord='" + maxWord + '\'' +
                ", minWord='" + minWord + '\'' +
                ", minWordLength=" + minWordLength +
                ", maxWordLength=" + maxWordLength +
                ", averageWordLength=" + averageWordLength +
                ", allWordsLength=" + allWordsLength +
                ", wordsCount=" + wordsCount +
                '}';
    }
}
