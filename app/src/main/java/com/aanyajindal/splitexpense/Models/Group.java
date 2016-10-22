package com.aanyajindal.splitexpense.Models;

/**
 * Created by aanyajindal on 22/10/16.
 */

public class Group {

    String name;
    String desc;
    String creationTime;
    String pic;

    public Group() {
    }

    public Group(String name, String desc, String creationTime, String pic) {
        this.name = name;
        this.desc = desc;
        this.creationTime = creationTime;
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
