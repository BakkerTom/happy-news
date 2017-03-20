package nl.fhict.happynews.android.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.koushikdutta.ion.Ion;
import nl.fhict.happynews.android.Models.Post;
import nl.fhict.happynews.android.R;

import java.util.ArrayList;

/**
 * Created by Sander on 06/03/2017.
 */
public class PostAdapter extends ArrayAdapter<Post> {

    private ArrayList<Post> posts;


    public PostAdapter(Context context, @LayoutRes int resource) {
        super(context, resource);
        posts = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Post post = posts.get(position);
        int xmlType;

        if (post.getImageUrl() == null) {
            xmlType = R.layout.list_item_post;
        } else {
            xmlType = R.layout.list_item_post_image;
        }

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(xmlType, parent, false);
            if (xmlType == R.layout.list_item_post_image) {
                convertView.setTag("image");
            } else {
                convertView.setTag("no-image");
            }
        } else {
            //check if the convertView is the right one, if not, inflate a new one with the right xmltype
            if (convertView.getTag().equals("image") && xmlType == R.layout.list_item_post) {
                convertView = LayoutInflater.from(getContext()).inflate(xmlType, parent, false);
                convertView.setTag("no-image");
            } else if (convertView.getTag().equals("no-image") && xmlType == R.layout.list_item_post_image) {
                convertView = LayoutInflater.from(getContext()).inflate(xmlType, parent, false);
                convertView.setTag("image");
            }
        }

        String headline = post.getTitle();
        String source = post.getSource();
        String publishedAt = relativeTimeSpan(post.getPublishedAt());

        if (headline == null) {
            headline = "Hello World";
        }
        if (source == null) {
            source = "Article";
        }

        // Get the Views as part of the convertView
        TextView sourceTextView = (TextView) convertView.findViewById(R.id.sourceTextView);
        TextView timeTextView = (TextView) convertView.findViewById(R.id.timeTextView);
        TextView headlineTextView = (TextView) convertView.findViewById(R.id.headlineTextView);

        //Set content
        headlineTextView.setText(headline);
        sourceTextView.setText(source);
        timeTextView.setText(publishedAt);

        //If convertView is an image card...
        if (xmlType == R.layout.list_item_post_image) {
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

            //Load image into imageView
            Ion.with(imageView)
                    .load(post.getImageUrl());
        }
        return convertView;
    }

    /**
     * Creates a neatly formatted string displaying the Relative Time Span
     *
     * @param input the start time as a Unix Timestamp
     * @return relative Timestamp (eg. '4 hours ago')
     */
    public String relativeTimeSpan(long input) {

        if (input == 0) {
            return DateUtils.getRelativeTimeSpanString(System.currentTimeMillis()).toString();
        }

        return DateUtils.getRelativeTimeSpanString(input).toString();
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    /**
     * Change the current list of posts with a new list of posts
     * keeps the old list if the new list is null
     * notifies the adapter to show the changes in the app
     *
     * @param posts
     */
    public void updateData(ArrayList<Post> posts) {
        if (posts != null) {
            this.posts = posts;
            this.notifyDataSetChanged();
        }
    }

    @Override
    public Post getItem(int position) {
        return posts.get(position);
    }
}
