package io.rocketbase.vaadin.events;

import com.vaadin.flow.component.ComponentEvent;
import io.rocketbase.vaadin.LazyImage;
import io.rocketbase.vaadin.LazyImageItem;
import lombok.Data;

@Data
public class LoadMoreItemsEvent extends ComponentEvent<LazyImage> {

    private LazyImageItem imageItemList;

    public LoadMoreItemsEvent(LazyImage source, boolean fromClient, LazyImageItem img) {
        super(source, fromClient);
        this.imageItemList = img;
    }
}
