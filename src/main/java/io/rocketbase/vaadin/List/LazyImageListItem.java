package io.rocketbase.vaadin.List;

import io.rocketbase.vaadin.LazyImage;
import io.rocketbase.vaadin.LazyImageItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LazyImageListItem {

    private List<LazyImage> lazyImageList;
    private int limit;
    private List<LazyImageItem> lazyImageItemList;


    public LazyImageListItem(List<LazyImageItem> lazyImageItemList, int limit) {
        this.limit = limit;
        this.lazyImageItemList = lazyImageItemList;

        init();
    }


    private void init() {

        for (int i = limit + 1; i <= this.lazyImageItemList.size() - 1; i = i + limit + 1) {
            this.lazyImageItemList.add(i,
                    LazyImageItem
                            .builder()
                            .dataSrc("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=")
                            .placeholder(true)
                            .selectable(false)
                            .build()
            );
        }

        final Integer[] k = {0};
        this.lazyImageItemList.forEach((item) -> {
            item.setId(String.valueOf(k[0]));
            k[0]++;

            if (item.getPlaceholder() == null) {
                item.setPlaceholder(false);
            }

        });
    }

    public List<LazyImage> convertToLazyImage() {
        this.lazyImageList = new ArrayList<>();
        this.lazyImageItemList.forEach((item) -> {
            LazyImage lazyImage = new LazyImage(item);
            this.lazyImageList.add(lazyImage);
        });
        return this.lazyImageList;
    }
}
