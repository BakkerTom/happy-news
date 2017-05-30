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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.koushikdutta.async.future.FutureCallback;
import nl.fhict.happynews.android.LoadListener;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.adapter.FeedAdapter;
import nl.fhict.happynews.android.manager.PostManager;
import nl.fhict.happynews.android.model.Page;
import nl.fhict.happynews.android.model.Post;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A {@link Fragment} that displays a list of posts. The posts that will be displayed can be determined by overriding
 * {@link #doRefresh(PostManager, FutureCallback)} and {@link #doLoadNextPage(PostManager, Page, FutureCallback)}.
 *
 * <p>A custom layout can be added by overriding {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}. Example:
 *
 * <pre>
 * &#64;Override
 * public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 *     View view = inflater.inflate(R.layout.my_fragment, container, false);
 *
 *     ViewGroup postContainer = (ViewGroup) view.findViewById(R.id.post_container);
 *     View posts = super.onCreateView(inflater, postContainer, savedInstanceState);
 *
 *     postContainer.addView(posts);
 *
 *     return view;
 * }
 * </pre>
 */
public abstract class PostFragment extends Fragment implements LoadListener {

    private PostManager postManager;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FeedAdapter feedAdapter;

    private boolean loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        postManager = PostManager.getInstance(getActivity().getApplicationContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);

        feedAdapter = new FeedAdapter(getActivity(), new ArrayList<Post>());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(feedAdapter);
        recyclerView.setLayoutManager(layoutManager);

        // Display an error if not connected on start of this activity
        refresh();

        recyclerView.addOnScrollListener(onScrollListener);
        swipeRefresh.setOnRefreshListener(onRefreshListener);

        return view;
    }

    /**
     * When this fragment is subscribed to a PostManager, this notification will be called when content is finished
     * loading.
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

    /**
     * OnScrollListener for the RecyclerView. Loads the next page when the end of the list is reached.
     */
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        private int pastVisibleItems;
        private int visibleItemCount;
        private int totalItemCount;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (dy > 0) { //Check if scrolled down
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                if (!loading) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        loading = true;
                        Page lastPage = feedAdapter.getLastPage();
                        if (!lastPage.isLast()) {
                            doLoadNextPage(postManager, lastPage, nextPageCallback);
                        }
                    }
                }
            }
        }
    };

    /**
     * OnRefreshListener for the SwipeRefreshLayout. Reloads the list of posts.
     */
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            swipeRefresh.setRefreshing(false);
            loading = false;
            refresh();
        }
    };

    /**
     * Callback that is called when the next page of posts has been loaded.
     */
    private FutureCallback<Page> nextPageCallback = new FutureCallback<Page>() {
        @Override
        public void onCompleted(Exception e, Page result) {
            if (e != null) {
                Toast.makeText(getActivity(), R.string.bad_request_toast, Toast.LENGTH_SHORT).show();
            }

            feedAdapter.addPage(result);

            onFinishedLoading();
        }
    };

    /**
     * Callback that is called when the list of posts has been loaded.
     */
    private FutureCallback<Page> refreshCallback = new FutureCallback<Page>() {
        @Override
        public void onCompleted(Exception e, Page result) {
            if (e != null) {
                Toast.makeText(getActivity(), R.string.bad_request_toast, Toast.LENGTH_SHORT).show();
            }

            feedAdapter.setPage(result);

            onFinishedLoading();
        }
    };

    /**
     * Refresh the content.
     *
     * <p>Shows an error message when the app is not connected to the internet.
     */
    protected void refresh() {
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
                    refresh();
                }
            });

            alert.show();
        } else {
            swipeRefresh.setRefreshing(true);
            loading = true;

            doRefresh(postManager, refreshCallback);
        }
    }

    /**
     * Clear the current list of posts and load a fresh one.
     *
     * @param postManager The PostManager that can be queried for posts.
     * @param callback    The callback that receives the results.
     */
    protected abstract void doRefresh(PostManager postManager, FutureCallback<Page> callback);

    /**
     * Load the next page with posts.
     *
     * @param postManager The PostManager that can be queried for posts.
     * @param lastPage    The last page that was loaded.
     * @param callback    The callback that receives the results.
     */
    protected abstract void doLoadNextPage(PostManager postManager, Page lastPage, FutureCallback<Page> callback);

}
