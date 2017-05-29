package nl.fhict.happynews.android.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.model.Post;

/**
 * Created by tom on 27/03/2017.
 */
public class PostHolder extends ViewHolder {

    private TextView sourceTextView;
    private TextView timeTextView;
    private TextView headlineTextView;
    private ImageButton popupMenuImage;

    /**
     * Create a new {@link RecyclerView.ViewHolder} for {@link Post}s of the type article.
     *
     * @param view The view to use.
     */
    public PostHolder(View view) {
        super(view);

        //Initialize views
        sourceTextView = (TextView) view.findViewById(R.id.sourceTextView);
        timeTextView = (TextView) view.findViewById(R.id.timeTextView);
        headlineTextView = (TextView) view.findViewById(R.id.headlineTextView);
        popupMenuImage = (ImageButton) view.findViewById(R.id.popupMenuImage);
    }

    /**
     * Bind a {@link Post} to this view.
     *
     * @param post The post to bind.
     */
    @Override
    public void bindType(Post post) {
        super.bindType(post);

        sourceTextView.setText(post.getSource());
        timeTextView.setText(relativeTimeSpan(post.getPublishedAt()));
        headlineTextView.setText(post.getTitle());
        popupMenuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v, getAdapterPosition());
            }
        });
    }
}
