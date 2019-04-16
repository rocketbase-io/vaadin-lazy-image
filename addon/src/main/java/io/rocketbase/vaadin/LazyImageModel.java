package io.rocketbase.vaadin;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface LazyImageModel extends TemplateModel {

    void setImg(LazyImageItem img);

    void setSelectable(Boolean selectable);

}
