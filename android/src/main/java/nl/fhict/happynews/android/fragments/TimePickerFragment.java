package nl.fhict.happynews.android.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import java.util.Calendar;

/**
 * Created by Sander on 08/05/2017.
 */
public class TimePickerFragment extends DialogFragment {

    private Context context;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        try {
            timeSetListener = (TimePickerDialog.OnTimeSetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnTimeSetListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(context, timeSetListener, hour, minute,
            DateFormat.is24HourFormat(context));
    }
}
