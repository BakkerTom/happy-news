package nl.fhict.happynews.android.Activitys;

import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import nl.fhict.happynews.android.Adapters.PostAdapter;
import nl.fhict.happynews.android.Models.Post;
import nl.fhict.happynews.android.PostManager;
import nl.fhict.happynews.android.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private PostManager postManager;
    private ListView postList;
    private PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postManager = PostManager.getInstance();
        postList = (ListView) findViewById(R.id.listView);
        adapter = new PostAdapter(getApplicationContext(), R.layout.activity_main, getMockPosts());
        postList.setAdapter(adapter);
        Date now = Calendar.getInstance().getTime();

        ArrayList<Post> updatedList = getMockPosts();
        updatedList.add(new Post("source", "Henk van tiggel", "Vanaf vandaag peren voor een EUROOO", "vandaag blabla blalbal ksjdhskdfs sdf", "https://segunfamisa.com", "dit is de link naar een foto", now));
        adapter.updateData(updatedList);

        postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri;
                Post clickedPost = adapter.getItem(position);
                if(!clickedPost.getUrl().isEmpty()){
                    uri = Uri.parse(clickedPost.getUrl());
                }
                else{
                    uri = Uri.parse("https://segunfamisa.com");
                }

                CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = intentBuilder.build();
                customTabsIntent.launchUrl(MainActivity.this, uri);

            }
        });
    }

    /**
     * method that returns a few mock posts to test the adapter
     * @return
     */
    public ArrayList<Post> getMockPosts(){
        ArrayList<Post> mockPosts = new ArrayList<>();
        Date now = Calendar.getInstance().getTime();
        mockPosts.add(new Post("source", "Henk van tiggel", "Vanaf vandaag peren voor een EUROOO", "vandaag blabla blalbal ksjdhskdfs sdf", "https://segunfamisa.com", "dit is de link naar een foto", now));
        mockPosts.add(new Post("source", "Henk van tiggel", "Vanaf vandaag peren voor een EUROOO", "vandaag blabla blalbal ksjdhskdfs sdf", "https://segunfamisa.com", "dit is de link naar een foto", now));
        return mockPosts;
    }
}
