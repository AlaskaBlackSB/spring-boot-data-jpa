package com.example.springbootdatajpa.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {
    
    private String url;
    private Page<T> page;
    private Integer totalPages;
    private Integer numPerPage;
    private Integer actualPage;
    private List<PageItem> pages;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.pages = new ArrayList<PageItem>();

        totalPages = page.getTotalPages();
        numPerPage = page.getSize();
        actualPage = page.getNumber() + 1;

        Integer from, to;

        //Muestra el paginador completo ya que son pocos
        if (totalPages <= numPerPage) {
            from = 1;
            to = totalPages;
        }else if(actualPage <= numPerPage / 2){
            from = 1;
            to = numPerPage;
        }else if(actualPage >= totalPages - (numPerPage / 2)){
            from = totalPages - numPerPage + 1;
            to = numPerPage;
        }else{
            from = actualPage - (numPerPage / 2);
            to = numPerPage;
        }

        for (Integer i = 0; i < to; i++) {
            pages.add(new PageItem(from + i, actualPage == from + i));
        }

    }

    public String getUrl() {
        return url;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getActualPage() {
        return actualPage;
    }

    public List<PageItem> getPages() {
        return pages;
    }

    public boolean isFirst(){
        return page.isFirst();
    }

    public boolean isLast(){
        return page.isLast();
    }

    public boolean isHasNext(){
        return page.hasNext();
    }

    public boolean isHasPrevious(){
        return page.hasPrevious();
    }

}