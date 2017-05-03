package nl.fhict.happynews.android.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        settingText.setText(settings.get(position));
        return rowView;
    }

    @Override
    public int getCount() {
        return settings.size();
    }
}
