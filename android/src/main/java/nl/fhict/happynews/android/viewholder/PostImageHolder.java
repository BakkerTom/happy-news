package nl.fhict.happynews.android.viewholder;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import nl.fhict.happynews.android.model.Post;
import nl.fhict.happynews.android.R;

import java.util.Date;

/**
 * Created by tom on 27/03/2017.
 */
public class PostImageHolder extends ViewHolder{

    private TextView sourceTextView;
    private TextView timeTextView;
    private TextView headlineTextView;
    private ImageView imageView;
    private ProgressBar progressBar;

    public PostImageHolder(View view) {
        super(view);

        //Initialize views
        sourceTextView = (TextView) view.findViewById(R.id.sourceTextView);
        timeTextView = (TextView) view.findViewById(R.id.timeTextView);
        headlineTextView = (TextView) view.findViewById(R.id.headlineTextView);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        progressBar = (ProgressBar) view.findViewById(R.id.spinner);
    }

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

