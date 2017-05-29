package nl.fhict.happynews.android.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import nl.fhict.happynews.android.LoadListener;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.adapter.FeedAdapter;
import nl.fhict.happynews.android.manager.PostManager;
import nl.fhict.happynews.android.model.Page;
import nl.fhict.happynews.android.model.Post;

import java.util.ArrayList;

public class MainFragment extends Fragment implements LoadListener {

    private PostManager postManager;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FeedAdapter feedAdapter;

    private boolean loading;
    private int pastVisiblesItems;
    private int visibleItemCount;
    private int totalItemCount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        postManager = PostManager.getInstance(getActivity().getApplicationContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);

        feedAdapter = new FeedAdapter(getActivity(), new ArrayList<Post>());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(feedAdapter);
        recyclerView.setLayoutManager(layoutManager);

        postManager.setFeedAdapter(feedAdapter);

        swipeRefresh.setRefreshing(true);
        postManager.refresh(getActivity(), this);
        loading = true;

        addScrollListener();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postManager.refresh(getActivity(), MainFragment.this);
            }
        });

        return view;
    }

    private void addScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //Check if scrolled down
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (!loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = true;
                            Page lastPage = feedAdapter.getLastPage();
                            if (!lastPage.isLast()) {
                                postManager.load(
                                    lastPage.getNumber() + 1,
                                    PostManager.DEFAULT_PAGE_SIZE,
                                    getActivity(),
                                    MainFragment.this);
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
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        feedAdapter.notifyDataSetChanged();
    }
}
