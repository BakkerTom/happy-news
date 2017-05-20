package nl.fhict.happynews.android.viewholder;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Toast;
import nl.fhict.happynews.android.controller.ReadingHistoryController;
import nl.fhict.happynews.android.model.Post;

import java.util.Date;

/**
 * Created by tom on 27/03/2017.
 */
public abstract class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Post post;

    /**
     * Creates a new {@link RecyclerView.ViewHolder} for {@link Post}s.
     *
     * @param view The view to use.
     */
    public ViewHolder(View view) {
        super(view);

        view.setOnClickListener(this);
    }

    /**
     * Binds type.
     * @param post The post to bind.
     */
    public void bindType(Post post) {
        this.post = post;
        itemView.setAlpha(ReadingHistoryController.getInstance().postIsRead(post) ? .3f : 1f);
    }

    /**
     * Creates a neatly formatted string displaying the Relative Time Span.
     *
     * @param input the start time as a Date object
     * @return relative Timestamp (eg. '4 hours ago')
     */
    public String relativeTimeSpan(Date input) {
        long unixTime;

        if (input != null) {
            unixTime = input.getTime();
        } else {
            unixTime = System.currentTimeMillis();
        }

        return DateUtils.getRelativeTimeSpanString(unixTime).toString();
    }

    @Override
    public void onClick(View v) {
        Context context = itemView.getContext();

        if (!post.getUrl().isEmpty()) {
            Uri uri = Uri.parse(post.getUrl());
            ReadingHistoryController.getInstance().addReadPost(post);
            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
            customTabsIntent.launchUrl(context, uri);
        } else {
            Toast.makeText(context, "Link not found",
                Toast.LENGTH_SHORT).show();
        }
    }
}
