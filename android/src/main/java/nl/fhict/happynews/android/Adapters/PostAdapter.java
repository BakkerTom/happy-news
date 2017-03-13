package nl.fhict.happynews.android.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.widget.ArrayAdapter;
import nl.fhict.happynews.android.Models.Post;

/**
 * Created by Sander on 06/03/2017.
 */
public class PostAdapter extends ArrayAdapter<Post> {
    public PostAdapter(Context context, @LayoutRes int resource) {
        super(context, resource);
    }
}
