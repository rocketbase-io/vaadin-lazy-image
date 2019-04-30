package io.rocketbase.vaadin;


import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import io.rocketbase.vaadin.events.LazyImageSelectedEvent;
import io.rocketbase.vaadin.events.LoadMoreItemsEvent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LazyImageList {

    @Getter
    private Div content;

    private List<LazyImage> lazyImageList;

    private int limit;

    private int position = 0;

    private LazyLoadingPaging lazyLoad;

    private List<LazyImageItem> selectedLazyImageList;

    private Boolean selectionMode = false;


    /**
     * @param lazyImageItemList list of allImages
     * @param limit             maximal display images
     */
    public LazyImageList(List<LazyImageItem> lazyImageItemList, int limit) {
        this.limit = limit;
        this.content = new Div();

        this.lazyImageList = convertToLazyImage(lazyImageItemList);
        init();
    }

    /**
     * @param lazyload interface
     * @param limit    maximal display images
     */
    public LazyImageList(LazyLoadingPaging lazyload, int limit) {
        this.lazyLoad = lazyload;
        this.limit = limit;
        this.content = new Div();

        this.lazyImageList = convertToLazyImage(lazyload.load(0, this.limit));
        init();
    }

    private void init() {
        for (int i = 0; i <= this.limit - 1; i++) {
            content.add(lazyImageList.get(i));
            addListeners(lazyImageList.get(i));
        }

        content.add(generatePlaceholder());
    }

    private void loadMore(LoadMoreItemsEvent event) {
        Boolean addPlaceholder = true;
        content.remove(event.getSource());
        this.position += this.limit;
        if (lazyLoad == null) {
            for (int i = this.position + 1; i <= (this.position + this.limit); i++) {
                if (i < lazyImageList.size()) {
                    prepareLazyImage(lazyImageList.get(i));
                    addListeners(lazyImageList.get(i));
                }
            }
        } else {
            if (lazyLoad.count() <= this.position + this.limit) {
                this.limit = this.lazyLoad.count() % this.limit;
                addPlaceholder = false;
            }
            List<LazyImageItem> loadLazyImages = lazyLoad.load(this.position, this.limit);
            List<LazyImage> lazyImages = new ArrayList<>();
            loadLazyImages.forEach(i -> {
                LazyImage lazyImage = new LazyImage(i);
                prepareLazyImage(lazyImage);
                addListeners(lazyImage);
                lazyImages.add(lazyImage);
            });
            this.lazyImageList.addAll(lazyImages);


        }
        if (addPlaceholder) {
            content.add(generatePlaceholder());
        }

        if (this.selectionMode) {
            enableSelectionMode();
        }
    }

    private void prepareLazyImage(LazyImage lazyImage) {
        addListeners(lazyImage);
        content.add(lazyImage);
    }


    private void addListener(LazyImage item) {
        item.addLoadMoreItemsListener(new ComponentEventListener<LoadMoreItemsEvent>() {
            @Override
            public void onComponentEvent(LoadMoreItemsEvent loadMoreItemsEvent) {
                loadMore(loadMoreItemsEvent);
            }
        });
    }

    public List<LazyImage> convertToLazyImage(List<LazyImageItem> lazyImageItemList) {
        this.lazyImageList = new ArrayList<>();
        lazyImageItemList.forEach((item) -> {
            LazyImage lazyImage = new LazyImage(item);
            this.lazyImageList.add(lazyImage);
        });
        return this.lazyImageList;
    }

    public void setStyleForEachImage(String attribute, String value) {
        this.lazyImageList.forEach(i -> i.getElement().getStyle().set(attribute, value));
    }

    private LazyImage generatePlaceholder() {
        LazyImage placeholder = new LazyImage(LazyImageItem
                .builder()
                .dataSrc(LazyImage.EMPTY_IMAGE)
                .placeholder(true)
                .selectable(false)
                .build());
        placeholder.addLoadMoreItemsListener(this::loadMore);
        return placeholder;
    }

    public void enableSelectionMode() {
        this.selectionMode = true;
        this.selectedLazyImageList = new ArrayList<>();
        if (this.lazyImageList != null) {
            this.lazyImageList.forEach((image) -> {
                image.setSelectable(image.getImageItem().getSelectable());
            });
        }
    }

    public void disableSelectionMode() {
        this.selectionMode = false;
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

    private void addListeners(LazyImage item) {
        addLoadMoreListener(item);
        addSelectionListener(item);
    }

    private void addSelectionListener(LazyImage item) {
        item.addLazyImageSelectedEvent((ComponentEventListener<LazyImageSelectedEvent>) lazyImageSelectedEvent -> {

            LazyImageItem image = lazyImageSelectedEvent.getImageItemList();
            SelectAction action = lazyImageSelectedEvent.getAction();

            if (action.equals(SelectAction.ADD)) {
                addToSelectedList(image);
            } else if (action.equals(SelectAction.REMOVE)) {
                removeFromSelectedList(image);
            } else if (action.equals(SelectAction.RESET)) {
                resetSelectedList();
            }

        });
    }

    private void addLoadMoreListener(LazyImage item) {
        if (item.getImageItem().getPlaceholder()) {
            addListener(item);
        }
    }

    public List<LazyImageItem> getSelected() {
        return this.selectedLazyImageList != null ? this.selectedLazyImageList : Collections.emptyList();
    }
}
