package io.rocketbase.vaadin;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("")
public class DemoView extends Div {

    VerticalLayout main = new VerticalLayout();

    public DemoView() {

        VerticalLayout lazyImageLayout = new VerticalLayout();

//        LazyImageItem img1 = LazyImageItem.builder().dataSizes("auto").dataSrc("https://picsum.photos/1000").build();
//        LazyImageItem img2 = LazyImageItem.builder().dataSizes("auto").dataSrc("https://picsum.photos/900").build();
//        LazyImageItem img3 = LazyImageItem.builder().dataSizes("auto").dataSrc("https://picsum.photos/800").build();
//
//        LazyImage lazyImage1 = new LazyImage(img1);
//        LazyImage lazyImage2 = new LazyImage(img2);
//        LazyImage lazyImage3 = new LazyImage(img3);
//        lazyImageLayout.add(lazyImage1, lazyImage2, lazyImage3);
//
//        add(lazyImageLayout);


        List<LazyImageItem> imageItemList = new ArrayList<>();
        for (int i = 0; i <= 300; i++) {
            imageItemList.add(LazyImageItem.builder().dataSrc("https://picsum.photos/" + ((Math.random() * 10) + 800) + "?random").build());
        }

        LazyImageListItem build = LazyImageListItem.builder().lazyImageItemList(imageItemList).maxImages(3).build();
        LazyImageList list = new LazyImageList(build);

        add(list.getContent());

//        lazyImage1.setSrc("https://picsum.photos/300?random");
    }
}
