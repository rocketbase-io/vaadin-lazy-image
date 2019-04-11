package io.rocketbase.vaadin;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.shared.Registration;
import io.rocketbase.vaadin.events.LazyImageClickEvent;
import io.rocketbase.vaadin.events.LazyImageLoadedEvent;
import io.rocketbase.vaadin.events.LazyImageSelectedEvent;
import io.rocketbase.vaadin.events.LoadMoreItemsEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;


//@StyleSheet("frontend://css/lazy-image.css")

@Slf4j
@Tag("vaadin-lazy-image")
@HtmlImport("frontend://html/lazy-image.html")
@JavaScript("bower_components/lazysizes/lazysizes.js")
public class LazyImage extends PolymerTemplate<LazyImageModel> implements HasStyle, HasSize, HasElement {

    private static final PropertyDescriptor<String, String> dataSrcProperty = PropertyDescriptors.propertyWithDefault("src", "");
    private static final PropertyDescriptor<String, String> dataSizesProperts = PropertyDescriptors.propertyWithDefault("sizes", "auto");
    private static final PropertyDescriptor<String, String> dataSrcSetProperty = PropertyDescriptors.propertyWithDefault("srcset", "");
    private static final PropertyDescriptor<String, String> idProperty = PropertyDescriptors.propertyWithDefault("id", "");
    private static final PropertyDescriptor<String, String> placeholderProperty = PropertyDescriptors.propertyWithDefault("placeholder", "false");
    //    private static final PropertyDescriptor<String, String> selectableProperty = PropertyDescriptors.attributeWithDefault("selectable", "false");
//
    @Getter
    private LazyImageItem imageItem;


    public LazyImage(LazyImageItem img) {
        super();
        if (img.getId() == null) {
            String id = UUID.randomUUID().toString();
            img.setId(id);

            img.setPlaceholder(false);
            setPlaceholder("false");
        } else if (img.getPlaceholder()) {
            setPlaceholder("true");
            this.getElement().setAttribute("placeholder", true);
        }
        setSrc(img.getDataSrc());
        setSizes(img.getDataSizes());
        setSrcSet(img.getDataSrcSet());
        setID(img.getId());


        //this.addClassName("lazyload");
        this.imageItem = img;
        getModel().setImg(img);
    }

    private void setID(String id) {
        set(idProperty, id);
    }


    public void setSrcSet(String srcset) {
        if (srcset != null) {
            set(dataSrcSetProperty, srcset);
        }
    }

    public void setSelectable(Boolean selectable) {
        if (selectable != null) {
            getModel().setSelectable(selectable);
        }
    }

    private void setPlaceholder(String val) {
        if (val != null) {
            set(placeholderProperty, val);
        }
    }

    public void setSrc(String src) {
        if (src != null) {
            set(dataSrcProperty, src);
        }
    }

    public void setSizes(String size) {
        if (size != null) {
            set(dataSizesProperts, size);
        }
    }


    @EventHandler
    private void clickListener() {
        System.out.println(("KAm an"));
        fireEvent(new LazyImageClickEvent(this, true, this.imageItem));
    }

    @ClientCallable
    private void addSelectedImageToList() {
        log.info("Received selectable event");
        fireEvent(new LazyImageSelectedEvent(this, true, this.imageItem, SelectAction.ADD));
    }

    @ClientCallable
    private void imgLoaded() {
        fireEvent(new LazyImageLoadedEvent(this, true, this.imageItem));
    }

    @ClientCallable
    private void placeholderLoaded() {
        fireEvent(new LoadMoreItemsEvent(this, true, this.imageItem));
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

    public Registration addLazyImageSelectedEvent(ComponentEventListener<LazyImageSelectedEvent> listener) {
        return addListener(LazyImageSelectedEvent.class, listener);
    }
}
