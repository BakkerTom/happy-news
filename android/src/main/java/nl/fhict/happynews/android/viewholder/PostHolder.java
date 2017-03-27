package nl.fhict.happynews.android.viewholder;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import nl.fhict.happynews.android.model.Post;
import nl.fhict.happynews.android.R;

import java.util.Date;

/**
 * Created by tom on 27/03/2017.
 */
public class PostHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Post post;

    private TextView sourceTextView;
    private TextView timeTextView;
    private TextView headlineTextView;

    public PostHolder(View view) {
        super(view);

        //Initialize views
        sourceTextView = (TextView) view.findViewById(R.id.sourceTextView);
        timeTextView = (TextView) view.findViewById(R.id.timeTextView);
        headlineTextView = (TextView) view.findViewById(R.id.headlineTextView);

        //Set OnClick Listener
        view.setOnClickListener(this);
    }

    public void bindType(Post post) {
        this.post = post;

        sourceTextView.setText(this.post.getSource());
        timeTextView.setText(relativeTimeSpan(this.post.getPublishedAt()));
        headlineTextView.setText(this.post.getTitle());
    }

    /**
     * Creates a neatly formatted string displaying the Relative Time Span
     *
     * @param input the start time as a Date object
     * @return relative Timestamp (eg. '4 hours ago')
     */
    private String relativeTimeSpan(Date input) {

        long unixTime = input.getTime();

        if (unixTime == 0) {
            return DateUtils.getRelativeTimeSpanString(System.currentTimeMillis()).toString();
        }

        return DateUtils.getRelativeTimeSpanString(unixTime).toString();
    }

    @Override
    public void onClick(View v) {
        Context context = itemView.getContext();

        if (!post.getUrl().isEmpty()) {
            Uri uri = Uri.parse(post.getUrl());
            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
            customTabsIntent.launchUrl(context, uri);
        } else {
            Toast.makeText(context, "Can't find link",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
