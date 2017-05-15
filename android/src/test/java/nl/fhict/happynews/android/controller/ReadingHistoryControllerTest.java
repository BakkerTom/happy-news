package nl.fhict.happynews.android.controller;

import nl.fhict.happynews.android.BuildConfig;
import nl.fhict.happynews.android.model.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by Tobi on 08-May-17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ReadingHistoryControllerTest {

    @Test
    public void addReadPostTest() throws Exception {
        ReadingHistoryController.getInstance().initialize(RuntimeEnvironment.application);

        Post posta = new Post();
        posta.setUuid("abc");
        Post postb = new Post();
        postb.setUuid("123");

        ReadingHistoryController.getInstance().addReadPost(posta);
        assertTrue(ReadingHistoryController.getInstance().postIsRead(posta));
        assertFalse(ReadingHistoryController.getInstance().postIsRead(postb));
    }

}
