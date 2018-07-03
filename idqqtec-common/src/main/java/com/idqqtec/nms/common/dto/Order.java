package com.idqqtec.nms.common.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装请求参数的排序信息
 */
public class Order {
    private String sort;
    private String order;

    public List<String> getOrderParams() {
        String[] sorts = this.sort.split(","); //id,title
        String[] orders = this.order.split(",");//asc,desc
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < sorts.length; i++) {
            String str = sorts[i] + " " + orders[i];
            list.add(str);
        }
        return list;//[id asc,title desc]
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
