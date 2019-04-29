package io.rocketbase.vaadin.spring;

import com.google.common.base.Joiner;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.rocketbase.vaadin.LazyImage;
import io.rocketbase.vaadin.LazyImageItem;
import io.rocketbase.vaadin.Paging.LazyImagePaging;
import io.rocketbase.vaadin.Paging.LazyImagePagingItem;
import io.rocketbase.vaadin.events.LazyImageLoadedEvent;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Route
@Slf4j
public class MainView extends VerticalLayout {


    public MainView() throws IOException {
        VerticalLayout lazyImageLayout = new VerticalLayout();

        HorizontalLayout buttonGroup = new HorizontalLayout();
        Button click = new Button("enable selection");
        Button click1 = new Button("disable selection");
        Button click2 = new Button("get selected images");
        buttonGroup.add(click, click1, click2);


        LazyImage lazyImage1 = new LazyImage(LazyImageItem.builder().dataSizes("auto").dataSrc("https://picsum.photos/300").selectable(false).build());
        lazyImageLayout.add(lazyImage1);


        List<LazyImageItem> imageItemList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            LazyImageItem build = LazyImageItem.builder().dataSrc("https://picsum.photos/id/" + (((int) (Math.random() * 100) + 1) + "/800/800")).selectable(true).build();
            imageItemList.add(build);

        }

        /*
        Paging
         */
        LazyImagePagingItem pagingItem = new LazyImagePagingItem(10, 0, 1, imageItemList);
        LazyImagePaging paging = new LazyImagePaging(pagingItem);
        add(buttonGroup, paging.getContent());

        click.addClickListener((listener) -> {
            paging.enableSelectionMode();
        });

        click1.addClickListener((listener) -> {
            paging.disableSelectionMode();
        });

        click2.addClickListener((listener) -> {
            Notification.show(Joiner.on(", ").join(paging.getSelected().stream().map(LazyImageItem::getId).collect(Collectors.toList())));
        });

        paging.addLoadMore(listener -> {
            log.info(String.valueOf(((LazyImageLoadedEvent) listener).getSource().getPaging().getCurrentPage()));
        });
    }
}
