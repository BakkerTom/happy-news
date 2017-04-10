package nl.fhict.happynews.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.model.Page;
import nl.fhict.happynews.android.model.Post;
import nl.fhict.happynews.android.viewholder.PostHolder;
import nl.fhict.happynews.android.viewholder.PostImageHolder;
import nl.fhict.happynews.android.viewholder.PostQuoteHolder;

import java.util.ArrayList;

/**
 * Created by tom on 27/03/2017.
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int NEWS = 0;
    public static final int NEWSIMAGE = 1;
    public static final int QUOTE = 2;


    private final Context context;
    private Page lastPage;
    private ArrayList<Post> posts;

    public FeedAdapter(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public int getItemViewType(int position) {
        //TODO: Switch on correct type of post
        //Current implementation switches on the availability of a thumbnail image

        Post post = posts.get(position);

        if (post.getImageUrls().size() > 0) {
            return NEWSIMAGE;
        } else if (post.getType() == Post.Type.QUOTE) {
            return QUOTE;
        }

        return NEWS;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            default:
            case NEWS:
                view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.list_item_post, parent, false);
                return new PostHolder(view);
            case NEWSIMAGE:
                view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.list_item_post_image, parent, false);
                return new PostImageHolder(view);
            case QUOTE:
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.list_item_post_quote, parent, false);
                return new PostQuoteHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Post post = posts.get(position);

        switch (holder.getItemViewType()) {
            default:
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
     * Adds the content of a page to the current list of posts.
     *
     * @param page the loaded page element
     */
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
