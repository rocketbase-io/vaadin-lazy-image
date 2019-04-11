package io.rocketbase.vaadin;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import io.rocketbase.vaadin.events.LazyImageSelectedEvent;
import io.rocketbase.vaadin.events.LoadMoreItemsEvent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLazyImageSelector {


    @Getter
    protected Div content;

    protected List<LazyImage> lazyImageList;


    private List<LazyImageItem> selectedLazyImageList;

    protected void enableSelectionMode() {
        this.selectedLazyImageList = new ArrayList<>();
        lazyImageList.forEach((image) -> {
            image.setSelectable(image.getImageItem().getSelectable());
        });
    }

    protected void disableSelectionMode() {
        lazyImageList.forEach((image) -> {
            image.setSelectable(false);
        });
        this.selectedLazyImageList = null;
    }

    protected void addToSelectedList(LazyImageItem lazyImageItem) {
        this.selectedLazyImageList.add(lazyImageItem);
    }

    protected void removeFromSelectedList(LazyImageItem lazyImageItem) {
        this.selectedLazyImageList.remove(lazyImageItem);
    }

    public void resetSelectedList() {
        this.selectedLazyImageList = new ArrayList<>();
    }

    protected void addListeners(LazyImage item) {
        addLoadMoreListener(item);
        addSelectionListener(item);
    }

    private void addSelectionListener(LazyImage item) {
        item.addLazyImageSelectedEvent(new ComponentEventListener<LazyImageSelectedEvent>() {
            @Override
            public void onComponentEvent(LazyImageSelectedEvent lazyImageSelectedEvent) {

                LazyImageItem image = lazyImageSelectedEvent.getImageItemList();
                SelectAction action = lazyImageSelectedEvent.getAction();

                if (action.equals(action.ADD)) {
                    addToSelectedList(image);
                } else if (action.equals(SelectAction.REMOVE)) {
                    removeFromSelectedList(image);
                } else if (action.equals(SelectAction.RESET)) {
                    resetSelectedList();
                }

            }
        });
    }

    private void addLoadMoreListener(LazyImage item) {
        if (item.getImageItem().getPlaceholder()) {
            item.addLoadMoreItemsListener(new ComponentEventListener<LoadMoreItemsEvent>() {
                @Override
                public void onComponentEvent(LoadMoreItemsEvent loadMoreItemsEvent) {
                    loadMore(loadMoreItemsEvent);
                }
            });
        }
    }

    public List<LazyImageItem> getSelectedAndDisable() {
        List<LazyImageItem> tmp = this.selectedLazyImageList;
        disableSelectionMode();
        if (tmp.size() > 0) {
            return tmp;
        } else {
            return new ArrayList<>();
        }
    }

    protected abstract void loadMore(LoadMoreItemsEvent event);
}
