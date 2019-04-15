package io.rocketbase.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.rocketbase.vaadin.List.LazyImageList;
import io.rocketbase.vaadin.List.LazyImageListItem;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Route("")
public class DemoView extends Div {

    VerticalLayout main = new VerticalLayout();

    public DemoView() {

        VerticalLayout lazyImageLayout = new VerticalLayout();
        Button click = new Button("Klick mich");
        Button click1 = new Button("disable");
        Button click2 = new Button("getAll");


        LazyImage lazyImage1 = new LazyImage(LazyImageItem.builder().dataSizes("auto").dataSrc("https://picsum.photos/300").selectable(false).build());
        lazyImageLayout.add(lazyImage1);

        add(click, click1, click2);


        List<LazyImageItem> imageItemList = new ArrayList<>();
        for (int i = 0; i <= 60; i++) {
            LazyImageItem build = LazyImageItem.builder().dataSrc("https://picsum.photos/" + ((Math.random() * 10) + 800) + "?random").selectable(true).build();
            imageItemList.add(build);

        }

        LazyImageListItem build = new LazyImageListItem(imageItemList, 10);
        LazyImageList list = new LazyImageList(build);

        add(list.getContent());

        click.addClickListener((listener) -> {
            list.enableSelectionMode();
        });

        click1.addClickListener((listener) -> {
            list.disableSelectionMode();
        });

        click2.addClickListener((listener) -> {
            list.getSelectedAndDisable().forEach(item -> {
                System.out.println(item.getId());
            });
        });
    }
}
