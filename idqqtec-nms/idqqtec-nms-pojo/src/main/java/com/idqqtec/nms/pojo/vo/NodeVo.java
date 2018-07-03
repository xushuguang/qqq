package com.idqqtec.nms.pojo.vo;

/**
 * User: james.xu
 * Date: 2018/3/6
 * Time: 15:18
 * Version:V1.0
 */
public class NodeVo {
    private int category;
    private String name;
    private int symbolSize;
    private String symbol;
    private ItemStyle itemStyle;
    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSymbolSize() {
        return symbolSize;
    }

    public void setSymbolSize(int symbolSize) {
        this.symbolSize = symbolSize;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public ItemStyle getItemStyle() {
        return itemStyle;
    }

    public void setItemStyle(ItemStyle itemStyle) {
        this.itemStyle = itemStyle;
    }
}
