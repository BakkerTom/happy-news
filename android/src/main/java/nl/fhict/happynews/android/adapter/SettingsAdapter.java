package nl.fhict.happynews.android.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import nl.fhict.happynews.android.R;

import java.util.ArrayList;


/**
 * Created by Sander on 03/05/2017.
 */
public class SettingsAdapter extends ArrayAdapter<String> {

    private final Context context;
    private ArrayList<String> settings;

    /**
     * constructor for settingsAdapter, requires list of settings.
     * @param context app context
     * @param resource xml page
     * @param settings names of the settings
     */
    public SettingsAdapter(Context context, @LayoutRes int resource, ArrayList<String> settings) {
        super(context, resource);
        this.settings = settings;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_setting, null, true);
        TextView settingText = (TextView) rowView.findViewById(R.id.settingsTextView);
        ImageView iconImage = (ImageView) rowView.findViewById(R.id.iconImageView);
        Drawable iconDrawable = getIconDrawableByString(settings.get(position));
        iconImage.setImageDrawable(iconDrawable);

        settingText.setText(settings.get(position));
        return rowView;
    }

    @Override
    public int getCount() {
        return settings.size();
    }


    private Drawable getIconDrawableByString(String setting) {
        Drawable iconDrawable = context.getResources().getDrawable(R.drawable.ic_language_black_24dp);
        switch (setting) {
            case "Language":
                iconDrawable = context.getResources().getDrawable(R.drawable.ic_language_black_24dp);
                break;
            case "Notifications":
                iconDrawable = context.getResources().getDrawable(R.drawable.ic_notifications_black_24dp);
                break;
            case "Sources":
                iconDrawable = context.getResources().getDrawable(R.drawable.ic_forum_black_24dp);
                break;
            case "About":
                iconDrawable = context.getResources().getDrawable(R.drawable.ic_info_outline_black_24dp);
                break;
            case "Rate App":
                iconDrawable = context.getResources().getDrawable(R.drawable.ic_grade_black_24dp);
                break;
            default: break;
        }
        return iconDrawable;
    }
}
