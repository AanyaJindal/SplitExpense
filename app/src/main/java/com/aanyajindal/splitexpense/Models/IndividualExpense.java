package com.aanyajindal.splitexpense.Models;

/**
 * Created by aanyajindal on 22/10/16.
 */

public class IndividualExpense {

    String title;
    Float amount;
    String desc;
    String date;
    String time;
    String mode;
    String receipt;
    String category;
    String tags;

    public IndividualExpense() {
    }

    public IndividualExpense(String title, Float amount, String desc, String date, String time, String mode, String receipt, String category, String tags) {
        this.title = title;
        this.amount = amount;
        this.desc = desc;
        this.date = date;
        this.time = time;
        this.mode = mode;
        this.receipt = receipt;
        this.category = category;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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
}
