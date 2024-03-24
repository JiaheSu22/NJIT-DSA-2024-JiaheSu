package oy.tol.tira.books;

import java.io.*;

public class MyBook implements Book {
    private String bookFilePath;
    private String ignoreFilePath;
    private KeyValueHashTable<String, Integer> wordCounts; // Using KeyValueHashTable for word counting
    private SimpleList ignoreWords; // Assume SimpleList is a custom list implementation
    private int totalWordCount = 0;

    public MyBook() {
        this.wordCounts = new KeyValueHashTable<>(); // Initialize with default capacity
        this.ignoreWords = new SimpleList(); // Assume SimpleList is already implemented
    }

    @Override
    public void setSource(String fileName, String ignoreWordsFile) throws FileNotFoundException {
        this.bookFilePath = fileName;
        this.ignoreFilePath = ignoreWordsFile;
        verifyFileExists(bookFilePath);
        verifyFileExists(ignoreFilePath);
    }

    private void verifyFileExists(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }
    }

    @Override
    public void countUniqueWords() throws IOException, OutOfMemoryError {
        loadIgnoreWords();
        try (BufferedReader reader = new BufferedReader(new FileReader(bookFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        }
    }

    private void loadIgnoreWords() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(ignoreFilePath))) {
            String[] words = reader.readLine().split(",");
            for (String word : words) {
                ignoreWords.add(word.trim()); // Assume add method exists in SimpleList
            }
        }
    }

    private void processLine(String line) {
        for (String word : line.split("\\P{IsAlphabetic}+")) {
            word = word.toLowerCase();
            if (word.length() > 1 && !ignoreWords.contains(word)) { // Assume contains method exists in SimpleList
                Integer count = wordCounts.find(word);
                count = (count == null) ? 1 : count + 1;
                wordCounts.add(word, count);
                totalWordCount++;
            }
        }
    }

    @Override
    public void report() {
        // Convert the hash table to a sorted array. The sorting is assumed to be by word count in descending order.
        Pair<String, Integer>[] sortedWords = wordCounts.toSortedArray();

        // Print the top 100 words or the total number of unique words, whichever is smaller.
        System.out.println("Top Words by Occurrence:");
        for (int i = 0; i < Math.min(sortedWords.length, 100); i++) {
            System.out.println((i + 1) + ". " + sortedWords[i].getKey() + " - " + sortedWords[i].getValue());
        }

        // Print counts and statistics after the list
        System.out.println("\nStatistics:");
        System.out.println("Total number of words: " + totalWordCount);
        System.out.println("Number of unique words: " + wordCounts.size());
        System.out.println("Number of words ignored: " + ignoreWords.size());
        System.out.println("Number of words from the ignore list that appeared in the book: " + calculateIgnoredWordsInBook());

        // Print hash table specific statistics
        System.out.println("\nHash Table Statistics:");
        System.out.println(wordCounts.getStatus());
    }

    private int calculateIgnoredWordsInBook() {
        int ignoredWordCountInBook = 0;
        for (int i = 0; i < ignoreWords.size(); i++) {
            String ignoredWord = ignoreWords.get(i);
            if (wordCounts.find(ignoredWord) != null) {
                ignoredWordCountInBook++;
            }
        }
        return ignoredWordCountInBook;
    }

    @Override
    public void close() {
        wordCounts = new KeyValueHashTable<>(); // Reset the word counts hash table
        ignoreWords.clear(); // Assume clear method exists in SimpleList
    }

    @Override
    public int getUniqueWordCount() {
        return wordCounts.size();
    }

    @Override
    public int getTotalWordCount() {
        return totalWordCount;
    }

    @Override
    public String getWordInListAt(int position) {
        // This method will need a way to access a word by position, which might not be straightforward with a hash table.
        // One approach could be to convert the hash table to an array and then access by position.
        Pair<String, Integer>[] sortedWords = wordCounts.toSortedArray(); // Assuming toSortedArray is implemented
        if (position >= 0 && position < sortedWords.length) {
            return sortedWords[position].getKey();
        }
        return null; // If position is out of bounds
    }

    @Override
    public int getWordCountInListAt(int position) {
        Pair<String, Integer>[] sortedWords = wordCounts.toSortedArray(); // Assuming toSortedArray is implemented
        if (position >= 0 && position < sortedWords.length) {
            return sortedWords[position].getValue();
        }
        return -1; // If position is out of bounds
    }
}

