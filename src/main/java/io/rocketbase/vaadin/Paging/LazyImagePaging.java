package io.rocketbase.vaadin.Paging;


import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import io.rocketbase.vaadin.LazyImage;
import io.rocketbase.vaadin.events.LoadMoreItemsEvent;
import lombok.Getter;

import java.util.List;

public class LazyImagePaging {

    @Getter
    private Div content;

    private List<LazyImage> lazyImageList;
    private LazyImagePagingItem pagingItem;


    public LazyImagePaging(LazyImagePagingItem pagingItem) {
        this.content = new Div();
        this.pagingItem = pagingItem;
        this.lazyImageList = pagingItem.convertToLazyImage();


        lazyImageList.forEach((item) -> {
            if (item.getImageItem().getPlaceholder()) {
                addLoadMoreListener(item);
            }
        });

        for (int i = pagingItem.getPaging().getOffset(); i <= pagingItem.getPaging().getLimit() + 1; i++) {
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


    private LazyImagePagingItem.Paging loadMore(LoadMoreItemsEvent event) {
        content.remove(event.getSource());
        return pagingItem.getPaging();
    }

    public void setNewLazyImageList(List<LazyImage> lazyImageList, Integer offset) {
        this.lazyImageList = lazyImageList;
        this.pagingItem.getPaging().setOffset(offset);
    }
}
