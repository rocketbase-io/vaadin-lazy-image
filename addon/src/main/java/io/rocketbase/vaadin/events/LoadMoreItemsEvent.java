package io.rocketbase.vaadin.events;

import com.vaadin.flow.component.ComponentEvent;
import io.rocketbase.vaadin.LazyImage;
import io.rocketbase.vaadin.LazyImageItem;
import io.rocketbase.vaadin.Paging.LazyImagePagingItem;
import lombok.Data;

@Data
public class LoadMoreItemsEvent extends ComponentEvent<LazyImage> {

    private LazyImageItem imageItemList;
    private LazyImagePagingItem.Paging paging;

    public LoadMoreItemsEvent(LazyImage source, boolean fromClient, LazyImageItem img) {
        super(source, fromClient);
        this.imageItemList = img;
    }

    public LoadMoreItemsEvent(LazyImage source, boolean fromClient, LazyImageItem img, LazyImagePagingItem.Paging paging) {
        super(source, fromClient);
        this.imageItemList = img;
        this.paging = paging;
    }
}
