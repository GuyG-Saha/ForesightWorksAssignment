package com.example.codingassignment.datamodel;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
@Entity
public class ProjectStory {
    @Id
    private String uid;
    private String name;
    private String type;
    private Date startDate;
    private Date endDate;
    private String parentUid;
    private Integer completionStatusPercentage;

    public ProjectStory() {
    }

    public ProjectStory(String uid, String name, String type, Date startDate, Date endDate, String parentUid
            , Integer completionStatusPercentage) {
        this.uid = uid;
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.parentUid = parentUid;
        this.completionStatusPercentage = completionStatusPercentage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getParentUid() {
        return parentUid;
    }

    public void setParentUid(String parentUid) {
        this.parentUid = parentUid;
    }

    public Integer getCompletionStatusPercentage() {
        return completionStatusPercentage;
    }

    public void setCompletionStatusPercentage(Integer completionStatusPercentage) {
        this.completionStatusPercentage = completionStatusPercentage;
    }

    @Override
    public String toString() {
        return "ProjectStory{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", parentUid='" + parentUid + '\'' +
                ", completionStatusPercentage=" + completionStatusPercentage +
                '}';
    }
}
