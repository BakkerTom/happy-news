package nl.fhict.happynews.android.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.model.Post;

/**
 * Created by Tobi on 10-Apr-17.
 */
public class PostQuoteHolder extends ViewHolder {

    private TextView sourceTextView;
    private TextView headlineTextView;
    private TextView authorTextView;

    /**
     * Create a new {@link RecyclerView.ViewHolder} for {@link Post}s of the type quote.
     *
     * @param view The view to use.
     */
    public PostQuoteHolder(View view) {
        super(view);

        //Initialize views
        sourceTextView = (TextView) view.findViewById(R.id.sourceTextView);
        authorTextView = (TextView) view.findViewById(R.id.authorTextView);
        headlineTextView = (TextView) view.findViewById(R.id.headlineTextView);
    }

    /**
     * Bind a {@link Post} to this view.
     *
     * @param post The post to bind.
     */
    public void bindType(Post post) {
        super.bindType(post);

        sourceTextView.setText(post.getSource());
        headlineTextView.setText(post.getTitle());
        authorTextView.setText(post.getAuthor());
    }
}
