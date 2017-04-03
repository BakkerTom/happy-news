package nl.fhict.happynews.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import nl.fhict.happynews.android.model.Page;
import nl.fhict.happynews.android.model.Post;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.viewholder.PostHolder;
import nl.fhict.happynews.android.viewholder.PostImageHolder;

import java.util.ArrayList;

/**
 * Created by tom on 27/03/2017.
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private Page lastPage;
    private ArrayList<Post> posts;

    public FeedAdapter(Context mContext, ArrayList<Post> posts) {
        this.mContext = mContext;
        this.posts = posts;
    }

    @Override
    public int getItemViewType(int position) {
        //TODO: Switch on correct type of post
        //Current implementation switches on the availability of a thumbnail image

        Post post = posts.get(position);

        if (post.getImageUrls().size() > 0) {
            return 1;
        }

        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case 0:
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.list_item_post, parent, false);
                return new PostHolder(view);
            case 1:
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.list_item_post_image, parent, false);
                return new PostImageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Post post = posts.get(position);

        switch (holder.getItemViewType()) {
            case 0:
                PostHolder postHolder = (PostHolder) holder;
                postHolder.bindType(post);
                break;
            case 1:
                PostImageHolder postImageHolder = (PostImageHolder) holder;
                postImageHolder.bindType(post);
                break;
        }
    }

    @Override
    public int getItemCount() {
        //Returns the size of the posts Array
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
            Log.d("FeedAdapter", "Updated Data");
        }
    }

    public void addPage(Page page) {
        if (page != null) {
            this.lastPage = page;
            this.posts.addAll(page.getContent());
            this.notifyDataSetChanged();
        }
    }

    public Page getLastPage() {
        return lastPage;
    }
}
