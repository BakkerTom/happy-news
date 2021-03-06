package nl.fhict.happynews.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.activity.SourcesSettingsActivity;
import nl.fhict.happynews.android.controller.SourceController;
import nl.fhict.happynews.android.model.SourceSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sander on 08/05/2017.
 */
public class SourceSettingsAdapter extends ArrayAdapter<SourceSetting> {

    private final Context context;
    private List<SourceSetting> sources;
    private SourcesSettingsActivity parentActivity;

    private static final int PARENT = 0;
    private static final int CHILD = 1;

    /**
     * Constructor for settingsAdapter
     *
     * @param context  the applicationContext.
     * @param resource resource layout.
     * @param sources  List of sources settings objects.
     */
    public SourceSettingsAdapter(Context context,
                                 @LayoutRes int resource,
                                 @NonNull List<SourceSetting> sources) {
        super(context, resource, sources);
        this.context = context;
        this.sources = sources;
        sort();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SourceSetting sourceSetting = sources.get(position);

        View v = convertView;
        int type = getItemViewType(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        switch (type) {
            case PARENT:
                v = inflater.inflate(R.layout.list_item_setting_source_parent, parent, false);
                break;
            case CHILD:
                v = inflater.inflate(R.layout.list_item_setting_source_child, parent, false);
                break;
            default:
                break;
        }

        final TextView sourceNameTextView = (TextView) v.findViewById(R.id.sourceTextView);
        final Switch sourceSwitch = (Switch) v.findViewById(R.id.sourceSwitch);

        sourceNameTextView.setText(sourceSetting.getCleanName());

        SourceSetting src = SourceController.getInstance().getSource(context, sourceSetting.getName());

        sourceSwitch.setChecked(src != null && src.isEnabled());
        if (!sourceSwitch.isChecked()) {
            sourceNameTextView.setTextColor(Color.GRAY);
        } else {
            sourceNameTextView.setTextColor(Color.BLACK);
        }

        sourceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SourceController.getInstance().toggleSource(getContext(), sourceSetting.getName());
                boolean refresh = false;
                if (sourceSetting.isParent()) {
                    SourceController.getInstance().toggleSourceChildren(getContext(),
                        sourceSetting.getName(),
                        sourceSwitch.isChecked());
                    refresh = true;
                }
                if (sourceSwitch.isChecked()) {
                    sourceNameTextView.setTextColor(Color.BLACK);
                } else {
                    sourceNameTextView.setTextColor(Color.GRAY);
                }
                sort();

                if (refresh) {
                    parentActivity.refreshActivity();
                }
            }
        });
        return v;
    }


    @Override
    public int getItemViewType(int position) {
        SourceSetting sourceSetting = sources.get(position);
        if (sourceSetting.isParent()) {
            return PARENT;
        } else {
            return CHILD;
        }
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * Set the parent activity to call updateChanges to save the preference changes.
     *
     * @param parentActivity sourceSettingsActivity
     */
    public void setParentActivity(SourcesSettingsActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    /**
     * Method for sorting the sources settings.
     * First adds all the parent sources to a list and then adds the child sources.`
     */
    private void sort() {
        ArrayList<SourceSetting> sortedSources = new ArrayList<>();
        HashMap<String, Integer> parentSettingsIndexes = new HashMap();
        for (SourceSetting sourceSetting : sources) {
            if (sourceSetting.isParent()) {
                sortedSources.add(sourceSetting);
                parentSettingsIndexes.put(sourceSetting.getName(), sortedSources.indexOf(sourceSetting));
            }
        }
        for (SourceSetting sourceSetting : sources) {
            if (!sourceSetting.isParent()) {
                int insertIndex = parentSettingsIndexes.get(sourceSetting.getParent().getName()) + 1;
                sortedSources.add(insertIndex, sourceSetting);
                parentSettingsIndexes.put(sourceSetting.getParent().getName(), insertIndex);
            }
        }
        this.sources = sortedSources;
    }
}
