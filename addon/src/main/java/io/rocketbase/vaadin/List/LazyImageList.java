package io.rocketbase.vaadin.List;


import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import io.rocketbase.vaadin.AbstractLazyImageSelector;
import io.rocketbase.vaadin.LazyImage;
import io.rocketbase.vaadin.events.LoadMoreItemsEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LazyImageList extends AbstractLazyImageSelector {


    private LazyImageListItem listItem;


    public LazyImageList(LazyImageListItem listItem) {
        this.content = new Div();
        this.listItem = listItem;
        this.lazyImageList = listItem.convertToLazyImage();


        lazyImageList.forEach((item) -> {
            addListeners(item);
        });


        for (int i = 0; i <= listItem.getLimit(); i++) {
            content.add(lazyImageList.get(i));
        }
    }

    protected void loadMore(LoadMoreItemsEvent event) {
        content.remove(event.getSource());
        for (int i = Integer.parseInt(event.getImageItemList().getId()) + 1; i <= (Integer.parseInt(event.getImageItemList().getId()) + listItem.getLimit() + 1); i++) {
            if (i < lazyImageList.size()) {
                content.add(lazyImageList.get(i));
            }
        }
    }

    @Override
    protected void addListener(LazyImage item) {
        item.addLoadMoreItemsListener(new ComponentEventListener<LoadMoreItemsEvent>() {
            @Override
            public void onComponentEvent(LoadMoreItemsEvent loadMoreItemsEvent) {
                loadMore(loadMoreItemsEvent);
            }
        });
    }
}
