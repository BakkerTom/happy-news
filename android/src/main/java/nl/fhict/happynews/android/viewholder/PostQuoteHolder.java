package nl.fhict.happynews.android.viewholder;

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

    public PostQuoteHolder(View view) {
        super(view);

        //Initialize views
        sourceTextView = (TextView) view.findViewById(R.id.sourceTextView);
        authorTextView = (TextView) view.findViewById(R.id.authorTextView);
        headlineTextView = (TextView) view.findViewById(R.id.headlineTextView);
    }

    public void bindType(Post post) {
        super.bindType(post);

        sourceTextView.setText(post.getSource());
        headlineTextView.setText(post.getTitle());
        authorTextView.setText(post.getAuthor());
    }
}
