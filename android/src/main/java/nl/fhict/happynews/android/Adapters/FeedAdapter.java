package nl.fhict.happynews.android.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import nl.fhict.happynews.android.Models.Post;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.ViewHolders.PostHolder;

import java.util.ArrayList;

/**
 * Created by tom on 27/03/2017.
 */
public class FeedAdapter extends RecyclerView.Adapter<PostHolder> {

    private final Context mContext;
    private final ArrayList<Post> posts;

    public FeedAdapter(Context mContext, ArrayList<Post> posts) {
        this.mContext = mContext;
        this.posts = posts;
    }

    @Override
    public int getItemViewType(int position) {
        //TODO: Switch on correct type of post
        //Current implementation switches on the availability of a thumbnail image

        Post post = posts.get(position);

        if (post.getImageUrl() != null){
            return 1;
        }

        return 0;
    }

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType){
            case 0:
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.list_item_post, parent, false);
                return new PostHolder(view);
            case 1:
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.list_item_post_image, parent, false);
                return new PostHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(PostHolder holder, int position) {
        Post post = posts.get(position);
        holder.bindType(post);
    }

    @Override
    public int getItemCount() {
        //Returns the size of the posts Array
        return posts.size();
    }
}
