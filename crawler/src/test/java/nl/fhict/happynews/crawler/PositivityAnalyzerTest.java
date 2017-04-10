package nl.fhict.happynews.crawler;

import nl.fhict.happynews.crawler.analyzer.PositivityAnalyzer;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by daan_ on 27-3-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PositivityAnalyzerTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PositivityAnalyzer positivityAnalyzer;

    @Test
    public void testAnalyzeText() throws IOException {
        Resource resource = applicationContext.getResource("classpath:/positiveText.txt");
        boolean positive = positivityAnalyzer.analyzeText(FileUtils.readFileToString(resource.getFile()));

        assertThat(positive, is(true));
    }

    /**
     * Test if texts with and without lowercase work.
     */
    @Test
    public void testLowercase() {
        String text = "abounds abundance accomplished accomplishment accomplishments accurate";
        boolean positive = positivityAnalyzer.analyzeText(text);
        assertThat(positive, is(true));
        text = "AboUnds abUndance ACcomplished accomplISHment accomplishments aCCurAte";
        positive = positivityAnalyzer.analyzeText(text);
        assertThat(positive, is(true));
    }
}
