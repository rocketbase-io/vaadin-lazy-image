package io.rocketbase.vaadin.spring;

import com.vaadin.flow.component.button.Button;
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
        for (int i = 0; i <= 10; i++) {
            LazyImageItem build = LazyImageItem.builder().dataSrc("https://picsum.photos/id/" + (((int) (Math.random() * 100) + 1) + "/800/800")).selectable(true).build();
            imageItemList.add(build);

        }
        /*
        normal list
         */

//        LazyImageList list = new LazyImageList(new LazyImageListItem(imageItemList, 10));
//
//
//        add(buttonGroup, list.getContent());
//
//        click.addClickListener((listener) -> {
//            list.enableSelectionMode();
//        });
//
//        click1.addClickListener((listener) -> {
//            list.disableSelectionMode();
//        });
//
//        click2.addClickListener((listener) -> {
//            list.getSelectedAndDisable().forEach(item -> {
//                System.out.println(item.getId());
//            });
//        });


        /*
        Paging
         */
        LazyImagePagingItem pagingItem = new LazyImagePagingItem(10, 20, 5, imageItemList);
        LazyImagePaging paging = new LazyImagePaging(pagingItem);
        add(buttonGroup, paging.getContent());

        click.addClickListener((listener) -> {
            paging.enableSelectionMode();
        });

        click1.addClickListener((listener) -> {
            paging.disableSelectionMode();
        });

        click2.addClickListener((listener) -> {
            paging.getSelectedAndDisable().forEach(item -> {
                System.out.println(item.getId());
            });
        });

        paging.addLoadMore(listener -> {
            log.info(String.valueOf(((LazyImageLoadedEvent) listener).getSource().getPaging().getCurrentPage()));
        });
    }
}
