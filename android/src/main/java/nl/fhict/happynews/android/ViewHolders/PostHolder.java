package nl.fhict.happynews.android.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import nl.fhict.happynews.android.Models.Post;
import nl.fhict.happynews.android.R;

/**
 * Created by tom on 27/03/2017.
 */
public class PostHolder extends RecyclerView.ViewHolder {

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

    public void bindType(Post post){
        sourceTextView.setText(post.getSource());
        timeTextView.setText(relativeTimeSpan(post.getPublishedAt()));
        headlineTextView.setText(post.getTitle());
    }

    /**
     * Creates a neatly formatted string displaying the Relative Time Span
     *
     * @param input the start time as a Unix Timestamp
     * @return relative Timestamp (eg. '4 hours ago')
     */
    private String relativeTimeSpan(long input) {

        if (input == 0) {
            return DateUtils.getRelativeTimeSpanString(System.currentTimeMillis()).toString();
        }

        return DateUtils.getRelativeTimeSpanString(input).toString();
    }
}
