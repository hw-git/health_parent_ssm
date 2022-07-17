package cn.lanqiao.pojo;

import java.io.Serializable;

/**
 * 运动库
 */
public class SportBase implements Serializable {
    private static final long serialVersionUID = -9148919755768319817L;
    private Integer sportid;
    private String sportcode;
    private String sportname;
    private String sporttype;
    private String sportremark;

    public SportBase() {
    }

    public SportBase(String sportcode, String sportname, String sporttype, String sportremark) {
        this.sportcode = sportcode;
        this.sportname = sportname;
        this.sporttype = sporttype;
        this.sportremark = sportremark;
    }

    public Integer getSportid() {
        return sportid;
    }

    public void setSportid(Integer sportid) {
        this.sportid = sportid;
    }

    public String getSportcode() {
        return sportcode;
    }

    public void setSportcode(String sportcode) {
        this.sportcode = sportcode;
    }

    public String getSportname() {
        return sportname;
    }

    public void setSportname(String sportname) {
        this.sportname = sportname;
    }

    public String getSporttype() {
        return sporttype;
    }

    public void setSporttype(String sporttype) {
        this.sporttype = sporttype;
    }

    public String getSportremark() {
        return sportremark;
    }

    public void setSportremark(String sportremark) {
        this.sportremark = sportremark;
    }
}
