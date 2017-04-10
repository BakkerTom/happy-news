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
 * Created by tom on 27/03/2017.
 */
public class PostImageHolder extends ViewHolder {

    private TextView sourceTextView;
    private TextView timeTextView;
    private TextView headlineTextView;
    private ImageView imageView;
    private ProgressBar progressBar;

    /**
     * Create a new {@link RecyclerView.ViewHolder} for {@link Post}s of the type article with an image.
     *
     * @param view The view to use.
     */
    public PostImageHolder(View view) {
        super(view);

        //Initialize views
        sourceTextView = (TextView) view.findViewById(R.id.sourceTextView);
        timeTextView = (TextView) view.findViewById(R.id.timeTextView);
        headlineTextView = (TextView) view.findViewById(R.id.headlineTextView);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        progressBar = (ProgressBar) view.findViewById(R.id.spinner);
    }

    /**
     * Bind a {@link Post} to this view.
     *
     * @param post The post to bind.
     */
    @Override
    public void bindType(Post post) {
        super.bindType(post);

        progressBar.setVisibility(View.VISIBLE);

        sourceTextView.setText(post.getSource());
        timeTextView.setText(relativeTimeSpan(post.getPublishedAt()));
        headlineTextView.setText(post.getTitle());

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

