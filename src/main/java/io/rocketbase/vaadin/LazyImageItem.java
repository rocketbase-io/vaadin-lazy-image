package io.rocketbase.vaadin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LazyImageItem {
    private String dataSrc;
    private String dataSizes;
    private String dataSrcSet;
    private String id;
    private Boolean placeholder;
    private String style;
    private Boolean selectable;
}
