package io.rocketbase.vaadin.Paging;

import io.rocketbase.vaadin.LazyImage;
import io.rocketbase.vaadin.LazyImageItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class LazyImagePagingItem {

    private Paging paging;
    private List<LazyImageItem> lazyImageItemList;


    public LazyImagePagingItem(Integer limit, Integer offset, Integer currentPage, List<LazyImageItem> lazyImageItemList) {
        this.paging = new Paging(limit, offset, currentPage);
        this.lazyImageItemList = lazyImageItemList;

        init();
    }


    private void init() {

        this.lazyImageItemList.add(this.paging.offset + 1,
                LazyImageItem
                        .builder()
                        .dataSrc("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=")
                        .placeholder(true)
                        .selectable(false)
                        .build()
        );
    }

    public List<LazyImage> convertToLazyImage() {
        List<LazyImage> list = new ArrayList<>();
        this.lazyImageItemList.forEach((item) -> {
            list.add(new LazyImage(item));
        });
        return list;
    }

    @Data
    @AllArgsConstructor
    public class Paging {
        private Integer limit;
        private Integer offset;
        private Integer currentPage;
    }
}
