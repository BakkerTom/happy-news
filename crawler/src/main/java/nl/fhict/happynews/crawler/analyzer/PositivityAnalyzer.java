package nl.fhict.happynews.crawler.analyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Rates texts by a positivity score
 * Created by daan_ on 27-3-2017.
 */
@Component
public class PositivityAnalyzer {

    @Autowired
    private ApplicationContext applicationContext;

    private Logger logger = LoggerFactory.getLogger(PositivityAnalyzer.class);

    private HashSet<String> positiveWords;
    private HashSet<String> negativeWords;


    /**
     * Loads the wordlists on application startup.
     */
    @PostConstruct
    private void init() {
        positiveWords = loadWords("positivewords.txt");
        negativeWords = loadWords("negativewords.txt");
    }


    /**
     * rates the positivity of a text based on the words it contains
     *
     * @param inputText the text to be analyzed
     * @return true if positive
     */
    public boolean analyzeText(String inputText) {
        HashMap<String, Integer> inputWords = getUniqueWords(inputText);
        AtomicInteger pos = new AtomicInteger();
        AtomicInteger neg = new AtomicInteger();
        inputWords.forEach((word, count) -> {
            if (positiveWords.contains(word)) {
                pos.addAndGet(count);
            } else if (negativeWords.contains(word)) {
                neg.addAndGet(count);
            }
        });

        int positive = pos.get();
        int negative = neg.get();
        boolean isPositive = 0.7203 * positive - negative > 3;

        logger.info("Positive? " + (isPositive ? "Yes" : "No"));

        return isPositive;
    }

    /**
     * Gets the unique words per text and their frequency.
     *
     * @param inputText
     * @return
     */
    private HashMap<String, Integer> getUniqueWords(String inputText) {
        String[] words = inputText.split("[,.:;\\s\\n]");
        HashMap<String, Integer> uniqueWords = new HashMap<>();
        for (String word : words) {
            uniqueWords.merge(word, 1, (integer, integer2) -> integer + integer2);
        }
        uniqueWords.remove("");
        return uniqueWords;
    }

    /**
     * Load word from a file
     *
     * @param file filename for file located in resource folder
     * @return set of words
     */
    private HashSet<String> loadWords(String file) {
        HashSet<String> words = new HashSet<>();
        try {
            Resource resource = applicationContext.getResource("classpath:/" + file);
            BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()));
            String word;
            while ((word = reader.readLine()) != null) {
                words.add(word);
            }
        } catch (IOException e) {
            logger.error("Error reading from wordfile", e);
        }
        return words;
    }

}
