package nl.fhict.happynews.android.ViewHolders;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.koushikdutta.ion.Ion;
import nl.fhict.happynews.android.Models.Post;
import nl.fhict.happynews.android.R;

/**
 * Created by tom on 27/03/2017.
 */
public class PostImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Post post;
    private TextView sourceTextView;
    private TextView timeTextView;
    private TextView headlineTextView;
    private ImageView imageView;

    public PostImageHolder(View view) {
        super(view);

        //Initialize views
        sourceTextView = (TextView) view.findViewById(R.id.sourceTextView);
        timeTextView = (TextView) view.findViewById(R.id.timeTextView);
        headlineTextView = (TextView) view.findViewById(R.id.headlineTextView);
        imageView = (ImageView) view.findViewById(R.id.imageView);

        view.setOnClickListener(this);
    }

    public void bindType(Post post) {
        this.post = post;

        sourceTextView.setText(this.post.getSource());
        timeTextView.setText(relativeTimeSpan(this.post.getPublishedAt()));
        headlineTextView.setText(this.post.getTitle());

        Ion.with(imageView)
                .load(this.post.getImageUrl());
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

    @Override
    public void onClick(View v) {

        Context context = itemView.getContext();

        if (!post.getUrl().isEmpty()) {
            Uri uri = Uri.parse(post.getUrl());
            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
            customTabsIntent.launchUrl(context, uri);
        } else {
            Toast.makeText(context, "Link not found",
                    Toast.LENGTH_SHORT).show();
        }
    }
}

