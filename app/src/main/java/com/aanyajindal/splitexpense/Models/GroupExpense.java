package com.aanyajindal.splitexpense.Models;

import java.util.Map;

/**
 * Created by aanyajindal on 22/10/16.
 */

public class GroupExpense {

    String title;
    Float total;
    String desc;
    String date;
    String receipt;
    String category;
    String tags;
    Map<String, Float> amounts;

    public GroupExpense() {
    }

    public GroupExpense(String title, Float total, String desc, String date, String receipt, String category, String tags, Map<String, Float> amounts) {
        this.title = title;
        this.total = total;
        this.desc = desc;
        this.date = date;
        this.receipt = receipt;
        this.category = category;
        this.tags = tags;
        this.amounts = amounts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Map<String, Float> getAmounts() {
        return amounts;
    }

    public void setAmounts(Map<String, Float> amounts) {
        this.amounts = amounts;
    }
}
