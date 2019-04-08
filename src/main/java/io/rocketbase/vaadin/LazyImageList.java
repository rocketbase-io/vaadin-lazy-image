package io.rocketbase.vaadin;


import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import io.rocketbase.vaadin.events.LoadMoreItemsEvent;
import lombok.Getter;

import java.util.List;

public class LazyImageList {

    @Getter
    private Div content;

    private List<LazyImage> lazyImageList;
    private LazyImageListItem listItem;


    public LazyImageList(LazyImageListItem listItem) {
        this.content = new Div();
        this.listItem = listItem;
        this.lazyImageList = listItem.convertToLazyImage();


        lazyImageList.forEach((item) -> {
            addLoadMoreListener(item);
        });

        for (int i = 0; i <= listItem.getMaxImages() + 1; i++) {
            content.add(lazyImageList.get(i));
        }
    }

    private void addLoadMoreListener(LazyImage item) {
        item.addLoadMoreItemsListener(new ComponentEventListener<LoadMoreItemsEvent>() {
            @Override
            public void onComponentEvent(LoadMoreItemsEvent loadMoreItemsEvent) {
                loadMore(loadMoreItemsEvent);
            }
        });
    }


    private void loadMore(LoadMoreItemsEvent event) {
        for (int i = Integer.parseInt(event.getImageItemList().getId()); i <= (Integer.parseInt(event.getImageItemList().getId()) + listItem.getMaxImages() + 1); i++) {
            content.add(lazyImageList.get(i));
        }
    }
}
