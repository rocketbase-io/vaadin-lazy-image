package io.rocketbase.vaadin;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.shared.Registration;
import io.rocketbase.vaadin.events.LazyImageClickEvent;
import io.rocketbase.vaadin.events.LazyImageLoadedEvent;
import io.rocketbase.vaadin.events.LoadMoreItemsEvent;

import java.util.UUID;

@Tag("vaadin-lazy-image")
@HtmlImport("frontend://html/lazy-image.html")
@JavaScript("bower_components/lazysizes/lazysizes.js")
public class LazyImage extends PolymerTemplate<LazyImageModel> {
    private static final String src = "https://picsum.photos/900?random";

    private static final PropertyDescriptor<String, String> dataSrcProperty = PropertyDescriptors.propertyWithDefault("src", src);
    private static final PropertyDescriptor<String, String> dataSizesProperts = PropertyDescriptors.propertyWithDefault("sizes", "auto");
    private static final PropertyDescriptor<String, String> dataSrcSetProperty = PropertyDescriptors.propertyWithDefault("srcset", "");
    private static final PropertyDescriptor<String, String> idProperty = PropertyDescriptors.propertyWithDefault("id", "");
    private static final PropertyDescriptor<String, String> placeholderProperty = PropertyDescriptors.propertyWithDefault("placeholder", "false");

    private LazyImageItem imageItem;


    public LazyImage(LazyImageItem img) {
        super();
        if (img.getId() == null) {
            String id = UUID.randomUUID().toString();
            img.setId(id);
            setID(id);
            img.setPlaceholder(false);
            setPlaceholder("false");
        } else if (img.getPlaceholder() == null) {
            img.setPlaceholder(false);
            setPlaceholder("false");
        } else if (img.getPlaceholder()) {
            setPlaceholder("true");
        }
        if (img.getDataSrc() != null) setSrc(img.getDataSrc());
        if (img.getDataSrcSet() != null) setSrcSet(img.getDataSrcSet());
        if (img.getDataSizes() != null) setSizes(img.getDataSizes());

        this.imageItem = img;

        getModel().setImg(img);
    }

    private void setID(String id) {
        set(idProperty, id);
    }

    public void setSrcSet(String srcset) {
        set(dataSrcSetProperty, srcset);
    }

    private void setPlaceholder(String val) {
        set(placeholderProperty, val);
    }

    public void setSrc(String src) {
        set(dataSrcProperty, src);
    }

    public void setSizes(String size) {
        set(dataSizesProperts, size);
    }


    @EventHandler
    private void clickListener() {
        System.out.println("Received a handle click event");
        LazyImageClickEvent event = new LazyImageClickEvent(this, true, this.imageItem);
        fireEvent(event);
    }

    @ClientCallable
    private void imgLoaded() {
        System.out.println("Received a handle loaded event " + this.imageItem.getId());
        LazyImageLoadedEvent event = new LazyImageLoadedEvent(this, true, this.imageItem);
        fireEvent(event);
    }

    @ClientCallable
    private void placeholderLoaded() {
        System.out.println("Received placeholder Event" + this.imageItem.getId());
        LoadMoreItemsEvent event = new LoadMoreItemsEvent(this, true, this.imageItem);
        fireEvent(event);
    }

    public Registration addLazyImageLoadedListener(ComponentEventListener<LazyImageLoadedEvent> listener) {
        return addListener(LazyImageLoadedEvent.class, listener);
    }

    public Registration addLazyImageClickListener(ComponentEventListener<LazyImageClickEvent> listener) {
        return addListener(LazyImageClickEvent.class, listener);
    }

    public Registration addLoadMoreItemsListener(ComponentEventListener<LoadMoreItemsEvent> listener) {
        return addListener(LoadMoreItemsEvent.class, listener);
    }
}
