package nl.fhict.happynews.android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import nl.fhict.happynews.android.adapter.FeedAdapter;
import nl.fhict.happynews.android.model.Post;
import nl.fhict.happynews.android.PostManager;
import nl.fhict.happynews.android.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private PostManager postManager;
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
    }
}
