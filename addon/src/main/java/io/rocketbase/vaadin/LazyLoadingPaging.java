package io.rocketbase.vaadin;

import java.util.List;


public interface LazyLoadingPaging {
    //maximum number of elements
    int count();

    //Passes the next elements of LazyImageItem
    List<LazyImageItem> load(int page, int pageSize);
}
