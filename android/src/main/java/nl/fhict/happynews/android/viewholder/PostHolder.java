package nl.fhict.happynews.android.viewholder;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.model.Post;

import java.util.Date;

/**
 * Created by tom on 27/03/2017.
 */
public class PostHolder extends ViewHolder {

    private TextView sourceTextView;
    private TextView timeTextView;
    private TextView headlineTextView;

    public PostHolder(View view) {
        super(view);

        //Initialize views
        sourceTextView = (TextView) view.findViewById(R.id.sourceTextView);
        timeTextView = (TextView) view.findViewById(R.id.timeTextView);
        headlineTextView = (TextView) view.findViewById(R.id.headlineTextView);

    }

    public void bindType(Post post) {
        super.bindType(post);

        sourceTextView.setText(post.getSource());
        timeTextView.setText(relativeTimeSpan(post.getPublishedAt()));
        headlineTextView.setText(post.getTitle());
    }

}
