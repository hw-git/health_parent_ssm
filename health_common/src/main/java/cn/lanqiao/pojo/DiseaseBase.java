package cn.lanqiao.pojo;

import java.io.Serializable;

public class DiseaseBase implements Serializable {
    private static final long serialVersionUID = -1784944021147084073L;
    private Integer diseaseid;
    private String number;
    private String diseasename;
    private String diseasetype;
    private String diseasestate;

    public DiseaseBase() {
    }

    public DiseaseBase(String number, String diseasename, String diseasetype, String diseasestate) {
        this.number = number;
        this.diseasename = diseasename;
        this.diseasetype = diseasetype;
        this.diseasestate = diseasestate;
    }

    public Integer getDiseaseid() {
        return diseaseid;
    }

    public void setDiseaseid(Integer diseaseid) {
        this.diseaseid = diseaseid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDiseasename() {
        return diseasename;
    }

    public void setDiseasename(String diseasename) {
        this.diseasename = diseasename;
    }

    public String getDiseasetype() {
        return diseasetype;
    }

    public void setDiseasetype(String diseasetype) {
        this.diseasetype = diseasetype;
    }

    public String getDiseasestate() {
        return diseasestate;
    }

    public void setDiseasestate(String diseasestate) {
        this.diseasestate = diseasestate;
    }
}
