package nl.fhict.happynews.android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import nl.fhict.happynews.android.model.Post;

/** clickerlistener.
 * Created by Sander on 29/05/2017.
 */
public class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

    private int position;
    private Context context;
    private Post post;

    /**
     *  constructor for listener.
     * @param position position in feedadapter
     * @param context current application context
     * @param post relevant post
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
                String shareText = post.getUrl() + "\n";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                shareIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(shareIntent, "Share some positivity!"));
                return true;
            case R.id.Flag:
                return true;
            default:
        }
        return false;
    }
}
