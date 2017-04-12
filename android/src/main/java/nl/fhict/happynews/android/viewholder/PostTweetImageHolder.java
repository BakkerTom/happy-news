package nl.fhict.happynews.android.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.model.Post;

/**
 * Created by Sander on 11/04/2017.
 */
public class PostTweetImageHolder extends ViewHolder {

    private TextView tweetTextView;
    private TextView timeTextView;
    private TextView twitterUsernameTextView;
    private ImageView imageView;
    private ProgressBar progressBar;

    /**
     * Creates a new {@link RecyclerView.ViewHolder} for {@link Post}s.
     *
     * @param view The view to use.
     */
    public PostTweetImageHolder(View view) {
        super(view);

        tweetTextView = (TextView) view.findViewById(R.id.tweetTextView);
        timeTextView = (TextView) view.findViewById(R.id.timeTextView);
        twitterUsernameTextView = (TextView) view.findViewById(R.id.twitterUsernameTextView);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        progressBar = (ProgressBar) view.findViewById(R.id.spinner);
    }

    /**
     * Bind a {@link Post} to this view.
     *
     * @param post The post to bind.
     */
    public void bindType(Post post) {
        super.bindType(post);

        tweetTextView.setText(post.getContentText());
        timeTextView.setText(relativeTimeSpan(post.getPublishedAt()));
        twitterUsernameTextView.setText(post.getAuthor());

        Ion.with(imageView)
            .load(post.getImageUrls().get(0))
            .setCallback(new FutureCallback<ImageView>() {
                @Override
                public void onCompleted(Exception e, ImageView result) {
                    progressBar.setVisibility(View.GONE);
                }
            });
    }
}
