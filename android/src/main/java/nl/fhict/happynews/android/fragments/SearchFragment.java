package nl.fhict.happynews.android.fragments;

import android.util.Log;
import com.koushikdutta.async.future.FutureCallback;
import nl.fhict.happynews.android.SearchListener;
import nl.fhict.happynews.android.manager.PostManager;
import nl.fhict.happynews.android.model.Page;

public class SearchFragment extends PostFragment implements SearchListener {

    private String query = "";

    @Override
    public void onSearch(String query) {
        Log.d("Search", "Searching for: " + query);

        this.query = query;

        refresh();
    }

    @Override
    protected void doRefresh(PostManager postManager, FutureCallback<Page> callback) {
        postManager.refresh(query, getActivity(), callback);
    }

    @Override
    protected void doLoadNextPage(PostManager postManager, Page lastPage, FutureCallback<Page> callback) {
        postManager.load(query, lastPage.getNumber() + 1, PostManager.DEFAULT_PAGE_SIZE, getActivity(), callback);
    }
}
