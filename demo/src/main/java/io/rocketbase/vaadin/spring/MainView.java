package io.rocketbase.vaadin.spring;

import com.google.common.base.Joiner;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.rocketbase.vaadin.LazyImageItem;
import io.rocketbase.vaadin.LazyImageList;
import io.rocketbase.vaadin.LazyLoadingPaging;
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


        /*
        Generates a list of images
         */
        List<LazyImageItem> imageItemList = new ArrayList<>();
        for (int i = 0; i < 55; i++) {
            LazyImageItem build = LazyImageItem.builder().dataSrc("https://picsum.photos/id/" + (((int) (Math.random() * 100) + 1) + "/800/800")).selectable(true).build();
            imageItemList.add(build);
        }

        LazyImageList list = new LazyImageList(new LazyLoadingPaging() {
            @Override
            public int count() {
                return 25;
            }

            @Override
            public List<LazyImageItem> load(int page, int pageSize) {
                return imageItemList.subList(page, page + pageSize);
            }
        }, 10);


        list.setStyleForEachImage("min-height", "800px");


        add(buttonGroup, list.getContent());

        click.addClickListener((loadMoreEventListener) -> {
            list.enableSelectionMode();
        });

        click1.addClickListener((loadMoreEventListener) -> {
            list.disableSelectionMode();
        });

        click2.addClickListener((loadMoreEventListener) -> {
            Notification.show(Joiner.on(", ").join(list.getSelected().stream().map(LazyImageItem::getId).collect(Collectors.toList())));
        });


    }
}
