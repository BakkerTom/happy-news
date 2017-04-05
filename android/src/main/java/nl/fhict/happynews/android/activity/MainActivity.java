package nl.fhict.happynews.android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import nl.fhict.happynews.android.LoadListener;
import nl.fhict.happynews.android.PostManager;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.adapter.FeedAdapter;
import nl.fhict.happynews.android.model.Page;
import nl.fhict.happynews.android.model.Post;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoadListener{

    private PostManager postManager;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FeedAdapter feedAdapter;

    private boolean loading;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private static final int PAGE_SIZE = 20;

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
        postManager.loadPage(0, PAGE_SIZE, this, this);
        loading = true;

        addScrollListener();
    }

    private void addScrollListener(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //Check if scrolled down
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (!loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount){
                            loading = true;
                            Page lastPage = feedAdapter.getLastPage();
                            if (!lastPage.isLast()) {
                                postManager.loadPage(lastPage.getNumber() + 1, PAGE_SIZE, MainActivity.this, MainActivity.this);
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * When this activity is subscribed to a PostManager,
     * this notification will be called when content
     * is finished loading.
     */
    @Override
    public void onFinishedLoading() {
        loading = false;
    }
}
