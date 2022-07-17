package cn.lanqiao.pojo;

import java.io.Serializable;
import java.util.List;

public class IntervationTemplate implements Serializable {
    private static final long serialVersionUID = -6778471392727132823L;
    private Integer templateid;//模板id
    private String templatename;//模板名称
    private String templatestring;//模板关键字
    private String apply;//适用人群
    private String target;//方案目标
    private String templatestremark;//备注
    private Boolean templatestate;//模板状态
    private List<FoodBase> foodBases;//一个模板包含多个食品
    private List<SportBase> sportBases;//一个检查组合包含多个运动

    public IntervationTemplate() {
    }

    public IntervationTemplate(String templatename, String templatestring, String apply, String target, String templatestremark, Boolean templatestate, List<FoodBase> foodBases, List<SportBase> sportBases) {
        this.templatename = templatename;
        this.templatestring = templatestring;
        this.apply = apply;
        this.target = target;
        this.templatestremark = templatestremark;
        this.templatestate = templatestate;
        this.foodBases = foodBases;
        this.sportBases = sportBases;
    }

    public Integer getTemplateid() {
        return templateid;
    }

    public void setTemplateid(Integer templateid) {
        this.templateid = templateid;
    }

    public String getTemplatename() {
        return templatename;
    }

    public void setTemplatename(String templatename) {
        this.templatename = templatename;
    }

    public String getTemplatestring() {
        return templatestring;
    }

    public void setTemplatestring(String templatestring) {
        this.templatestring = templatestring;
    }

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTemplatestremark() {
        return templatestremark;
    }

    public void setTemplatestremark(String templatestremark) {
        this.templatestremark = templatestremark;
    }

    public Boolean getTemplatestate() {
        return templatestate;
    }

    public void setTemplatestate(Boolean templatestate) {
        this.templatestate = templatestate;
    }

    public List<FoodBase> getFoodBases() {
        return foodBases;
    }

    public void setFoodBases(List<FoodBase> foodBases) {
        this.foodBases = foodBases;
    }

    public List<SportBase> getSportBases() {
        return sportBases;
    }

    public void setSportBases(List<SportBase> sportBases) {
        this.sportBases = sportBases;
    }

    @Override
    public String toString() {
        return "IntervationTemplate{" +
                "templateid=" + templateid +
                ", templatename='" + templatename + '\'' +
                ", templatestring='" + templatestring + '\'' +
                ", apply='" + apply + '\'' +
                ", target='" + target + '\'' +
                ", templatestremark='" + templatestremark + '\'' +
                ", templatestate=" + templatestate +
                ", foodBases=" + foodBases +
                ", sportBases=" + sportBases +
                '}';
    }
}
