package com.zhitou.job.main.been;

/**
 * Created by LCH on 2018/9/13.
 */
public class BaseBean {

    /**
     * Datas : null
     * State : 10001
     * Total : 0
     * PageCount : 1
     * Content : 发送失败:isv.AMOUNT_NOT_ENOUGH:账户余额不足
     * PageIndex : 1
     * PageSize : 10
     */

    private Object Datas;
    private int State;
    private int Total;
    private int PageCount;
    private String Content;
    private int PageIndex;
    private int PageSize;

    public Object getDatas() {
        return Datas;
    }

    public void setDatas(Object Datas) {
        this.Datas = Datas;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int PageCount) {
        this.PageCount = PageCount;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public int getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(int PageIndex) {
        this.PageIndex = PageIndex;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int PageSize) {
        this.PageSize = PageSize;
    }
}
