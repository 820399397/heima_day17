package cn.xuguowen.pojo;

import java.util.List;

/**
 * @author xuguowen
 * @create 2021-05-08 16:17
 * @Description 分页对象 为了该对象的复用性，提高可扩展性，我们可以将其设置为泛型
 *                      这样既可以存入User对象的数据，将来也可以存入其他实体类的数据
 */
public class PageBean<T> {
    private int totalCount; // 总记录数
    private int totalPage;  // 总共的页数
    private List<T> list;      // 每个分页对象中存储的用户数据，在此项目中默认存储 5 条，因为每页就显示 5 条
    private int current;    // 当前页码
    private int rows;       // 每页显示的条数

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public PageBean(int totalCount, int totalPage, List<T> list, int current, int rows) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.list = list;
        this.current = current;
        this.rows = rows;
    }

    public PageBean() {
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", list=" + list +
                ", current=" + current +
                ", rows=" + rows +
                '}';
    }
}
