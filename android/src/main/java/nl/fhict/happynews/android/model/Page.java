package nl.fhict.happynews.android.model;

import java.util.ArrayList;

/**
 * Created by tom on 03/04/2017.
 */
public class Page {

    ArrayList<Post> content;
    boolean first;
    boolean last;
    int number;
    int numberOfElements;
    int size;
    int totalElements;
    int totalPages;

    public Page() {

    }

    public ArrayList<Post> getContent() {
        return content;
    }

    public boolean isFirst() {
        return first;
    }

    public boolean isLast() {
        return last;
    }

    public int getNumber() {
        return number;
    }
}
