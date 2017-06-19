package nl.fhict.happynews.android.fragments;

import com.koushikdutta.async.future.FutureCallback;
import nl.fhict.happynews.android.manager.PostManager;
import nl.fhict.happynews.android.model.Page;

public class MainFragment extends PostFragment {

    @Override
    protected void doRefresh(PostManager postManager, FutureCallback<Page> callback) {
        postManager.refresh(getActivity(), callback);
    }

    @Override
    protected void doLoadNextPage(PostManager postManager, Page lastPage, FutureCallback<Page> callback) {
        postManager.load(lastPage.getNumber() + 1, PostManager.DEFAULT_PAGE_SIZE, getActivity(), callback);
    }
}
