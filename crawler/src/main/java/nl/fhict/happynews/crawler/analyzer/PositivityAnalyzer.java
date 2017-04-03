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
import java.util.*;

/**
 * Rates texts by their positivity score
 * Created by daan_ on 27-3-2017.
 */
@Component
public class PositivityAnalyzer {

    @Autowired
    private ApplicationContext applicationContext;

    private Logger logger = LoggerFactory.getLogger(PositivityAnalyzer.class);

    private HashSet<String> positiveWords;
    private HashSet<String> negativeWords;


    @PostConstruct
    private void init() {
        positiveWords = loadWords("positivewords.txt");
        negativeWords = loadWords("negativewords.txt");
    }


    public double analyzeText(String inputText) {
        HashSet<String> inputWords = getUniqueWords(inputText);
        int pos = 0;
        int neg = 0;
        for (String word : inputWords) {
            if (positiveWords.contains(word)) {
                pos++;
            } else if (negativeWords.contains(word)) {
                neg++;
            }
        }
        return 10 * ((1.5 * pos - neg) / (1.5 * (pos + neg)));
    }

    private HashSet<String> getUniqueWords(String inputText) {
        String[] words = inputText.split("[,.:;\\s\\n]");
        HashSet<String> uniqueWords = new HashSet<>();
        uniqueWords.remove("");
        Collections.addAll(uniqueWords, words);
        return uniqueWords;
    }

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
