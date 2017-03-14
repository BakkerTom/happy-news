package nl.fhict.happynews.android.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
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
        Post p = posts.get(position);
        int xml_type;
        if (position % 2 == 0) {
            xml_type = R.layout.list_item_post;
        } else {
            xml_type = R.layout.list_item_post_2;
        }
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(xml_type, parent, false);
            TextView postName = (TextView) convertView.findViewById(R.id.postTitle);
            postName.setText(p.getTitle());
        }
        return convertView;
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
