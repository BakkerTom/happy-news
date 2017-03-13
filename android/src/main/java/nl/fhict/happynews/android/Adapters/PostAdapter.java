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

    public PostAdapter(Context context, @LayoutRes int resource, ArrayList<Post> posts ) {
        super(context, resource);
        this.posts = posts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Post p = posts.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_post, parent, false);
        }

        TextView postName = (TextView) convertView.findViewById(R.id.postTitle);
        postName.setText(p.getTitle());

        return convertView;
    }

    public int getCount() {
        return posts.size();
    }

    public void updateData(ArrayList<Post> posts){
        this.posts = posts;
        this.notifyDataSetChanged();
    }
}
