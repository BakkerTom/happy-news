package nl.fhict.happynews.android.viewholder;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Toast;
import nl.fhict.happynews.android.model.Post;

import java.util.Date;

/**
 * Created by tom on 27/03/2017.
 */
public abstract class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Post post;

    public ViewHolder(View view) {
        super(view);

        view.setOnClickListener(this);
    }

    public void bindType(Post post){
        this.post = post;
    }

    /**
     * Creates a neatly formatted string displaying the Relative Time Span
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
            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
            customTabsIntent.launchUrl(context, uri);
        } else {
            Toast.makeText(context, "Link not found",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
