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
        Post p = posts.get(position);
        int xml_type;
        //TextView postname;

        if(position % 2 == 0){
            xml_type = R.layout.list_item_post;
        }
        else{
            xml_type = R.layout.list_item_post_image;
        }

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(xml_type, parent, false);
            //postname = (TextView) convertView.findViewById(R.id.postTitle);
            //postname.setText(p.getTitle());
        }


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
