package com.android4.model;

public class MainItem {

    private String title;

    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MainItem(String title, String content) {
        super();
        this.title = title;
        this.content = content;
    }

}
