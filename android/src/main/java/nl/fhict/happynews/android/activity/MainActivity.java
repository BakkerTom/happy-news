package nl.fhict.happynews.android.activity;

import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import nl.fhict.happynews.android.adapter.PostAdapter;
import nl.fhict.happynews.android.model.Post;
import nl.fhict.happynews.android.PostManager;
import nl.fhict.happynews.android.R;


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

        adapter = new PostAdapter(this, R.layout.activity_main);
        postList.setAdapter(adapter);

        postManager.setPostAdapter(adapter);
        postManager.updatePosts(getApplicationContext());

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
    }
}
