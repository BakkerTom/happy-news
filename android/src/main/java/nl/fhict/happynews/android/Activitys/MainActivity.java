package nl.fhict.happynews.android.Activitys;

import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import nl.fhict.happynews.android.Adapters.FeedAdapter;
import nl.fhict.happynews.android.Adapters.PostAdapter;
import nl.fhict.happynews.android.Models.Post;
import nl.fhict.happynews.android.PostManager;
import nl.fhict.happynews.android.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private PostManager postManager;
    private ListView postList;
    private PostAdapter adapter;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FeedAdapter feedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postManager = PostManager.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        feedAdapter = new FeedAdapter(this, new ArrayList<Post>());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(feedAdapter);
        recyclerView.setLayoutManager(layoutManager);

        postManager.setFeedAdapter(feedAdapter);
        postManager.updatePosts(this);

        //postList = (ListView) findViewById(R.id.listView);

        //adapter = new PostAdapter(this, R.layout.activity_main);
        //postList.setAdapter(adapter);

        //postManager.setPostAdapter(adapter);
        //postManager.updatePosts(getApplicationContext());

        /*
        postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post clickedPost = adapter.getItem(position);
                if (!clickedPost.getUrl().isEmpty()) {
                    Uri uri = Uri.parse(clickedPost.getUrl());
                    CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
                    customTabsIntent.launchUrl(MainActivity.this, uri);
                } else {
                    Toast.makeText(getApplicationContext(), "Link not found",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        */
    }
}
