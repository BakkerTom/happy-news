package nl.fhict.happynews.android.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import nl.fhict.happynews.android.Adapters.PostAdapter;
import nl.fhict.happynews.android.Models.Post;
import nl.fhict.happynews.android.PostManager;
import nl.fhict.happynews.android.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private PostManager pm;
    private ListView postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pm = PostManager.getInstance();
        postList = (ListView) findViewById(R.id.listView);
        final PostAdapter adapter = new PostAdapter(getApplicationContext(), R.layout.activity_main, getMockPosts());
        postList.setAdapter(adapter);
    }

    public ArrayList<Post> getMockPosts(){
        ArrayList<Post> mockPosts = new ArrayList<>();
        Date now = Calendar.getInstance().getTime();
        mockPosts.add(new Post("source", "Henk van tiggel", "Vanaf vandaag peren voor een EUROOO", "vandaag blabla blalbal ksjdhskdfs sdf", "asdffhasddf", "dit is de link naar een foto", now));
        mockPosts.add(new Post("source", "Henk van tiggel", "Vanaf vandaag peren voor een EUROOO", "vandaag blabla blalbal ksjdhskdfs sdf", "asdffhasddf", "dit is de link naar een foto", now));
        return mockPosts;
    }


}
