package nl.fhict.happynews.android.adapter;

import android.text.format.DateUtils;
import nl.fhict.happynews.android.R;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowSystemClock;

/**
 * Created by tom on 15/03/2017.
 */
@RunWith(RobolectricTestRunner.class)
public class PostAdapterTest {

    @Test
    public void relativeTimeSpan() throws Exception {
        PostAdapter adapter = new PostAdapter(RuntimeEnvironment.application.getApplicationContext(), R.layout.activity_main);

        //Get current time and 4 Hours ago
        long now = System.currentTimeMillis();
        long fourHoursAgo = now - DateUtils.HOUR_IN_MILLIS * 4;

        //Set shadow system clock to now
        ShadowSystemClock.setCurrentTimeMillis(now);

        //Process the time
        String actual = adapter.relativeTimeSpan(fourHoursAgo);

        Assert.assertEquals("4 hours ago", actual);
    }


}