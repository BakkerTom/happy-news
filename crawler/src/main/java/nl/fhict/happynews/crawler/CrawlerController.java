package nl.fhict.happynews.crawler;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;


/**Service that runs the gathering and exporting of the data
 * Created by daan_ on 6-3-2017.
 */
@Service
public class CrawlerController{




    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedDelayString = "${crawler.delay}")
    public void reportCurrentTime() {
        System.out.println("The time is now "+ dateFormat.format(new Date()));
    }


}
