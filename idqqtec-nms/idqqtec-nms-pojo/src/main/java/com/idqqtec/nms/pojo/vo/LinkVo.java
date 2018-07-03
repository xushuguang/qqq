package com.idqqtec.nms.pojo.vo;

/**
 * User: james.xu
 * Date: 2018/3/6
 * Time: 16:19
 * Version:V1.0
 */
public class LinkVo {
    private String source;
    private String target;
    private ItemStyle lineStyle;
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public ItemStyle getLineStyle() {
        return lineStyle;
    }

    public void setLineStyle(ItemStyle lineStyle) {
        this.lineStyle = lineStyle;
    }
}
