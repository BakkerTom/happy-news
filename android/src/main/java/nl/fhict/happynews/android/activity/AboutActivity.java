package nl.fhict.happynews.android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import nl.fhict.happynews.android.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.title_settings_about);
    }
}
