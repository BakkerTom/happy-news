package nl.fhict.happynews.android;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import nl.fhict.happynews.android.manager.PostManager;
import nl.fhict.happynews.android.model.Post;

/**
 * clickerlistener.
 * Created by Sander on 29/05/2017.
 */
public class PostMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

    private static final int POSITIVE_BUTTON_ID  = -1;

    private int position;
    private Context context;
    private Post post;


    /**
     * Constructor.
     *
     * @param position pos
     * @param context  context
     * @param post     post
     */
    public PostMenuItemClickListener(int position, Context context, Post post) {
        this.position = position;
        this.context = context;
        this.post = post;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                share();
                return true;
            case R.id.flag:
                flag();
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

    /**
     * flags a post in the API.
     */
    private void flag() {
        final String[] reasons = context.getResources().getStringArray(R.array.flag_options);
        final int[] selectedIndex = {-1};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
            .setTitle(context.getString(R.string.flag_content_title))
            .setSingleChoiceItems(reasons, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AlertDialog ad = (AlertDialog) dialog;
                    ad.getButton(POSITIVE_BUTTON_ID).setEnabled(true);
                    selectedIndex[0] = which;
                }
            })
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (selectedIndex[0] > -1) {
                        PostManager.getInstance(context).flagPost(context,post.getUuid(), reasons[selectedIndex[0]]);
                    }
                }
            })
            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            }).show()
            .getButton(POSITIVE_BUTTON_ID)
            .setEnabled(false);

    }
}
