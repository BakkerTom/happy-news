package nl.fhict.happynews.crawler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by daan_ on 27-3-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PositivityAnalyzerTest {


    private static final String text = "A man who began a recycling project as a way to bond with his son ha raised $400,000 (£320,100) and donated every penny to children in need. \n" +
            "\n" +
            "“It’s more a blessing to give than to receive,” said 86-year-old Johnny Jennings. \n" +
            "\n" +
            "The octogenerian from the US state of Georgia said he started the project in 1985, as a way to bond with his son, Brent who was 17-years-old at the time.  \n" +
            "\n" +
            "    Read more\n" +
            "\n" +
            "The apps separated partners are using to co-parent ther children\n" +
            "\n" +
            "The pair would collect and sell recyclable newspapers, magazines, and cans and then put their earnings into a savings account. \n" +
            "\n" +
            "Thirty-two years later, all that recycling has added up, with Mr Jennings recycling more than 9 million pounds of paper – and saving up more than $400,000 in his bank account. \n" +
            "\n" +
            "Last May, the father donated every single penny to a local charity, the Georgia Baptist Children’s Home & Family Ministries. \n" +
            "\n" +
            "“They’re really proud,\" Brent said. \"I took the cheque down on Mother’s Day to a trustee meeting and they said ‘well, that will pay a lot of bills’.”\n" +
            "Play Video\n" +
            "Play\n" +
            "0:00\n" +
            "/\n" +
            "1:04\n" +
            " \n" +
            "Share\n" +
            "Fullscreen\n" +
            "999 call from child helps save his mother's life\n" +
            "\n" +
            "He added that he had always planned to donate the money he raised to a children’s charity, ever since a heartbreaking experience as a young man. \n" +
            "\n" +
            "“The reason I started was, when I was 18 years old, I visited a children’s home and I was asked by these three little boys, ‘would you be my daddy?’ and that just broke my heart,” he said.\n" +
            "\n" +
            "The family tradition had continued, as he now recycles with his son Johnny. \n" +
            "Read more\n" +
            "\n" +
            "    Trump planning to scrap programme that has fed 40 million children\n" +
            "    Huge rise in children under four hospitalised to have teeth removed\n" +
            "    Immigrant children improve school results, says Michael Gove\n" +
            "\n" +
            "His father said his son was able to pay the down payment on his first home with his own savings earned from recycling. \n" +
            "\n" +
            "“We knew it would pay off,” he said. “My boy had recycled, you know, when he was growing up, and then he had enough money in the bank saved up from recycling to buy the down payment when he married.”\n" +
            "\n" +
            "Even after his $400,000 donation, Mr Jennings Sr said he planned on raising more funds for the charity through recycling “until the undertaker turns my toes up”. \n" +
            "\n" +
            "“I just hope I can keep on going as long as I can,” he said. “As long as my health can keep up... It feels great. That’s worth more than money.”";


    @Autowired
    PositivityAnalyzer positivityAnalyzer;

    @Test
    public void testAnalyzeText() {
        double score = positivityAnalyzer.analyzeText(text);
        System.out.println("Score: " + score);
    }
}
