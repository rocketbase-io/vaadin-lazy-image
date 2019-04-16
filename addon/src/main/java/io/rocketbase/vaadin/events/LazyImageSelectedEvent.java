package io.rocketbase.vaadin.events;

import com.vaadin.flow.component.ComponentEvent;
import io.rocketbase.vaadin.LazyImage;
import io.rocketbase.vaadin.LazyImageItem;
import io.rocketbase.vaadin.SelectAction;
import lombok.Data;

@Data
public class LazyImageSelectedEvent extends ComponentEvent<LazyImage> {

    private LazyImageItem imageItemList;
    private SelectAction action;

    public LazyImageSelectedEvent(LazyImage source, boolean fromClient, LazyImageItem img, SelectAction action) {
        super(source, fromClient);
        this.imageItemList = img;
        this.action = action;
    }
}
