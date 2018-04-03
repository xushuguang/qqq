package com.qtec.snmp.pojo.vo;

/**
 * User: james.xu
 * Date: 2018/3/6
 * Time: 15:18
 * Version:V1.0
 */
public class NodeVo {
    private int category;
    private String name;
    private String label;
    private int symbolSize;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getSymbolSize() {
        return symbolSize;
    }

    public void setSymbolSize(int symbolSize) {
        this.symbolSize = symbolSize;
    }
}
