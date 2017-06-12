package nl.fhict.happynews.android;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import nl.fhict.happynews.android.manager.PostManager;
import nl.fhict.happynews.android.model.Post;

/**
 * Created by Sander on 29/05/2017.
 */
public class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

    private int position;
    private Context context;
    private Post post;

    /**
     * Constructor.
     * @param position pos
     * @param context context
     * @param post post
     */
    public MyMenuItemClickListener(int position, Context context, Post post) {
        this.position = position;
        this.context = context;
        this.post = post;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Share:
                return true;
            case R.id.Flag:
                PostManager.getInstance(context).flagPost(post.getUuid(), "It deaded");
                return true;
            default:
        }
        return false;
    }
}
