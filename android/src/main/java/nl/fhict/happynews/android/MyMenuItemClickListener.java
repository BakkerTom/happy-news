package nl.fhict.happynews.android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import nl.fhict.happynews.android.model.Post;

/**
 * clickerlistener.
 * Created by Sander on 29/05/2017.
 */
public class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

    private int position;
    private Context context;
    private Post post;

    /**
     * constructor for listener.
     *
     * @param position position in feedadapter
     * @param context  current application context
     * @param post     relevant post
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
                share();
                return true;
            case R.id.Flag:
                return true;
            default:
        }
        return false;
    }

    /**
     * creates the share intent and based on the type of post it creates a certain share message.
     */
    private void share() {
        String shareText;
        if (post.getType() == Post.Type.QUOTE) {
            shareText = post.getContentText() + " -- " + post.getAuthor() + "\n"
                + context.getString(R.string.share_text_disclaimer);
        } else {
            shareText = post.getUrl() + "\n" + context.getString(R.string.share_text_disclaimer);
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        shareIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.intent_chooser_message)));
    }
}
