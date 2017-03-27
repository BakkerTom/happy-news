package nl.fhict.happynews.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
    ApplicationContext applicationContext;

    private HashSet<String> positiveWords;
    private HashSet<String> negativeWords;


    public int analyzeText(String inputText) {
        HashSet<String> inputWords = getUniqueWords(inputText);
        loadPositiveWords();
        loadNegativeWords();
        int score = 0;
        Iterator iterator = inputWords.iterator();
        while (iterator.hasNext()) {
            String word = (String) iterator.next();
            if(positiveWords.contains(word)){
                score++;
            }
            else if(negativeWords.contains(word)){
                score++;
            }
        }

        return score;
    }

    private HashSet<String> getUniqueWords(String inputText) {
        String[] words = inputText.split("\\w");
        HashSet<String> uniqueWords = new HashSet<>();
        for (String word : words) {
            uniqueWords.add(word);
        }
        return uniqueWords;
    }

    private void loadPositiveWords() {
        positiveWords = new HashSet<>();
        try {
            Resource resource = applicationContext.getResource("classpath:/positivewords.txt");
            BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()));
            String word;
            while ((word = reader.readLine()) != null) {
                positiveWords.add(word);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadNegativeWords() {
        negativeWords = new HashSet<>();
        try {
            Resource resource = applicationContext.getResource("classpath:/negativewords.txt");
            BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()));
            String word;
            while ((word = reader.readLine()) != null) {
                negativeWords.add(word);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
