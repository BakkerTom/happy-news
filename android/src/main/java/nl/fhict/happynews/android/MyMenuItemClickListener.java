package nl.fhict.happynews.android;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
     *
     * @param position pos
     * @param context  context
     * @param post     post
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
                flag();
                return true;
            default:
        }
        return false;
    }

    private void flag() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Choose reason")
            .setSingleChoiceItems(R.array.flag_options, -1, null)
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    PostManager.getInstance(context).flagPost(post.getUuid(), "it deaded very mug");
                }
            })
            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
        builder.show();
    }
}
