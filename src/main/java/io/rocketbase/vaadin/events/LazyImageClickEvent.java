package io.rocketbase.vaadin.events;

import com.vaadin.flow.component.ComponentEvent;
import io.rocketbase.vaadin.LazyImage;
import io.rocketbase.vaadin.LazyImageItem;
import lombok.Data;

@Data
public class LazyImageClickEvent extends ComponentEvent<LazyImage> {

    LazyImageItem imageItem;

    public LazyImageClickEvent(LazyImage source, boolean fromClient, LazyImageItem imageItem) {
        super(source, fromClient);
        this.imageItem = imageItem;
    }
}
