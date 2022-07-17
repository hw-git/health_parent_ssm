package cn.lanqiao.entity;

/**
 * @Author: Hou
 * @Date: 2021/5/24 18:38
 * @Description:
 */
public class MenuResult {
    private Integer value;//下拉框value ， 等于id
    private String label;//下拉框显示内容

    public MenuResult() {
    }

    public MenuResult(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "MenuResult{" +
                "value=" + value +
                ", label='" + label + '\'' +
                '}';
    }
}
