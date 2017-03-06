package nl.fhict.happynews.android.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import nl.fhict.happynews.android.PostManager;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.shared.*;


public class MainActivity extends AppCompatActivity {

    private PostManager pm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pm = PostManager.getInstance();
    }
}
