package cn.lanqiao.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Hou
 * @Date: 2021/4/15 19:11
 * @Description:分页分装对象
 */
public class PageResult implements Serializable {
    private Long total;//总记录数
    private List rows;//当前页结果

    public PageResult(Long total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
