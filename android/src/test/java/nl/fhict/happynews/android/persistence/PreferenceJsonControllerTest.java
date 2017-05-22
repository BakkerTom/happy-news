package nl.fhict.happynews.android.persistence;

import com.google.gson.reflect.TypeToken;
import nl.fhict.happynews.android.BuildConfig;
import nl.fhict.happynews.android.model.SourceSetting;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

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
        PreferenceJsonController<SourceSetting> preferences = new PreferenceJsonController<>();

        preferences.put(RuntimeEnvironment.application, "abc", 12345);
        assertEquals(preferences.getAsInt(RuntimeEnvironment.application, "abc"), new Integer(12345));

        //TODO: maybe make test better
        List<String> collection = new ArrayList<String>();
        collection.add("abc");
        collection.add("123");
        preferences.put(RuntimeEnvironment.application, "123", collection);
        assertTrue(preferences.getAsList(RuntimeEnvironment.application, "123", new TypeToken<List<String>>(){})
            .contains("123"));
    }
}
