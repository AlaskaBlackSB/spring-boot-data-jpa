package com.example.springbootdatajpa.util.paginator;

public class PageItem {
    
    private Integer num;
    private boolean actual;

    public PageItem(Integer num, boolean actual) {
        this.num = num;
        this.actual = actual;
    }

    /**
     * @return Integer return the num
     */
    public Integer getNum() {
        return num;
    }

    /**
     * @return boolean return the actual
     */
    public boolean isActual() {
        return actual;
    }

}