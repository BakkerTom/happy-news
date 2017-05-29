package nl.fhict.happynews.android.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import nl.fhict.happynews.android.LoadListener;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.SearchListener;
import nl.fhict.happynews.android.adapter.FeedAdapter;
import nl.fhict.happynews.android.manager.PostManager;
import nl.fhict.happynews.android.model.Page;
import nl.fhict.happynews.android.model.Post;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class SearchFragment extends Fragment implements LoadListener, SearchListener {

    private PostManager postManager;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FeedAdapter feedAdapter;

    private boolean loading;
    private int pastVisiblesItems;
    private int visibleItemCount;
    private int totalItemCount;

    private String query = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        postManager = PostManager.getInstance(getActivity().getApplicationContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);

        feedAdapter = new FeedAdapter(getActivity(), new ArrayList<Post>());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(feedAdapter);
        recyclerView.setLayoutManager(layoutManager);

        postManager.setFeedAdapter(feedAdapter);

        addScrollListener();

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
                                    query,
                                    lastPage.getNumber() + 1,
                                    PostManager.DEFAULT_PAGE_SIZE,
                                    getActivity(),
                                    SearchFragment.this);
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

    @Override
    public void onSearch(String query) {
        Log.d("Search", "Searching for: " + query);

        this.query = query;

        checkConnection();

        postManager.refresh(query, getActivity(), this);
    }

    /**
     * Shows an error message when the app is not connected to the internet.
     */
    private void checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context
            .CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();

        if (!isConnected) {
            AlertDialog alert = new AlertDialog.Builder(getActivity()).create();

            alert.setTitle(getString(R.string.network_error_title));
            alert.setMessage(getString(R.string.no_internet_message));
            alert.setButton(RESULT_OK, getString(R.string.retry_button), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Display an error if still not connected
                    checkConnection();
                }
            });

            alert.show();
        } else {
            swipeRefresh.setRefreshing(true);
            loading = true;
            postManager.refresh(getActivity(), SearchFragment.this);
        }
    }
}
