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
            posts.add(new Post("source", "Henk van tiggel", "Vanaf vandaag peren voor een EUROOO", "vandaag blabla blalbal ksjdhskdfs sdf", "https://segunfamisa.com", "dit is de link naar een foto", "asd"));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Post p = posts.get(position);
        int xml_type;
        TextView postname;

        if(position % 2 == 0){
            xml_type = R.layout.list_item_post;
        }
        else{
            xml_type = R.layout.list_item_post_2;
        }

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(xml_type, parent, false);
            postname = (TextView) convertView.findViewById(R.id.postTitle);
            postname.setText(p.getTitle());
        }


        return convertView;
    }

    public int getCount() {
        return posts.size();
    }

    public void updateData(ArrayList<Post> posts){
        if(posts != null){
            this.posts = posts;
            this.notifyDataSetChanged();
        }
    }

    public Post getItem(int position) {
        return posts.get(position);
    }
}
