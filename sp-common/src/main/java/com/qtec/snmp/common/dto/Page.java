package com.qtec.snmp.common.dto;

/**
 * 封装分页的请求参数
 */
public class Page {
    /**
     * 当前页
     */
    private int page;
    /**
     * 每页显示的条数
     */
    private int rows;

    /**
     * 偏移量（每页第一条的索引号）
     * 这些东西符合javabean规范的，即使没有属性，有getter一样可以调用
     */
    //private int offset;
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getOffset() {
        return (page - 1) * rows;
    }

}
