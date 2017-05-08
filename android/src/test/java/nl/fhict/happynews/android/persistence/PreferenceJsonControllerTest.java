package nl.fhict.happynews.android.persistence;

import nl.fhict.happynews.android.BuildConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Tobi on 08-May-17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class PreferenceJsonControllerTest {

    @Test
    public void get() throws Exception {
        PreferenceJsonController.put(RuntimeEnvironment.application, "abc", 12345);
        assertEquals(PreferenceJsonController.getAsInt(RuntimeEnvironment.application, "abc"), new Integer(12345));

        //TODO: maybe make test better
        ArrayList<String> collection = new ArrayList<String>();
        collection.add("abc");
        collection.add("123");
        PreferenceJsonController.put(RuntimeEnvironment.application, "123", collection);
        assertTrue(PreferenceJsonController.getAsCollection(RuntimeEnvironment.application, "123").contains("123"));
    }
}
