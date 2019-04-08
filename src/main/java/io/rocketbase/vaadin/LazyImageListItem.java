package io.rocketbase.vaadin;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class LazyImageListItem {

    private int maxImages;
    private List<LazyImageItem> lazyImageItemList;

    public LazyImageListItem(int maxImages, List<LazyImageItem> lazyImageItemList) {
        this.maxImages = maxImages;
        this.lazyImageItemList = lazyImageItemList;

        init();
    }


    private void init() {

        for (int i = maxImages + 1; i <= this.lazyImageItemList.size() - 1; i = i + maxImages + 1) {
            this.lazyImageItemList.add(i,
                    LazyImageItem
                            .builder()
                            .dataSrc("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=")
                            .placeholder(true)
                            .build()
            );
        }

        /*
        for (int j = 0; j <= this.lazyImageItemList.size() - 1; j++) {
            this.lazyImageItemList.get(j).setId(String.valueOf(j));
        }*/
        final Integer[] k = {0};
        this.lazyImageItemList.forEach((item) -> {
            item.setId(String.valueOf(k[0]));
            k[0]++;
        });
    }

    public List<LazyImage> convertToLazyImage() {
        List<LazyImage> list = new ArrayList<>();
        this.lazyImageItemList.forEach((item) -> {
            list.add(new LazyImage(item));
        });
        return list;
    }
}
