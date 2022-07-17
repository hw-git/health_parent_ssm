package cn.lanqiao.pojo;

import java.io.Serializable;

/**
 * 食品库
 */
public class FoodBase implements Serializable {
    private static final long serialVersionUID = -1921987274478869139L;
    private Integer foodid;
    private String foodcode;
    private String foodname;
    private String foodtype;
    private String foodremark;

    public FoodBase() {
    }

    public FoodBase(String foodcode, String foodname, String foodtype, String foodremark) {
        this.foodcode = foodcode;
        this.foodname = foodname;
        this.foodtype = foodtype;
        this.foodremark = foodremark;
    }

    public Integer getFoodid() {
        return foodid;
    }

    public void setFoodid(Integer foodid) {
        this.foodid = foodid;
    }

    public String getFoodcode() {
        return foodcode;
    }

    public void setFoodcode(String foodcode) {
        this.foodcode = foodcode;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getFoodtype() {
        return foodtype;
    }

    public void setFoodtype(String foodtype) {
        this.foodtype = foodtype;
    }

    public String getFoodremark() {
        return foodremark;
    }

    public void setFoodremark(String foodremark) {
        this.foodremark = foodremark;
    }
}
