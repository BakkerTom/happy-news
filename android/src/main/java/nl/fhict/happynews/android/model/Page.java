package nl.fhict.happynews.android.model;

import java.util.ArrayList;

/**
 * Created by tom on 03/04/2017.
 */
public class Page {

    private ArrayList<Post> content;
    private boolean last;
    private int number;

    public Page() {

    }

    public ArrayList<Post> getContent() {
        return content;
    }

    public boolean isLast() {
        return last;
    }

    public int getNumber() {
        return number;
    }
}
