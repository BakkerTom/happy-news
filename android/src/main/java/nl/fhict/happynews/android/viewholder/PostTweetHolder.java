package nl.fhict.happynews.android.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.model.Post;

/**
 * Created by Sander on 11/04/2017.
 */
public class PostTweetHolder extends ViewHolder {

    private TextView tweetTextView;
    private TextView timeTextView;
    private TextView twitterUsernameTextView;
    private ImageButton popupMenuImage;

    /**
     * Creates a new {@link RecyclerView.ViewHolder} for {@link Post}s.
     *
     * @param view The view to use.
     */
    public PostTweetHolder(View view) {
        super(view);

        tweetTextView = (TextView) view.findViewById(R.id.tweetTextView);
        timeTextView = (TextView) view.findViewById(R.id.timeTextView);
        twitterUsernameTextView = (TextView) view.findViewById(R.id.twitterUsernameTextView);
        popupMenuImage = (ImageButton) view.findViewById(R.id.popupMenuImage);
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
        popupMenuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v, getAdapterPosition());
            }
        });
    }
}
