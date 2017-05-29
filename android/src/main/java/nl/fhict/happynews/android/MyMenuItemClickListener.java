package nl.fhict.happynews.android;

import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;

/**
 * Created by Sander on 29/05/2017.
 */
public class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

    private int position;

    public MyMenuItemClickListener(int position) {
        this.position = position;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Share:
                return true;
            case R.id.Flag:
                return true;
            default:
        }
        return false;
    }
}
