package com.example.notimportant.unicomicsviewer.POJO;


public class Comics {
    private String title;
    private String engTitle;
    private String thumbUrl;
    private String comicsUrl;

    public Comics(String title, String engTitle, String thumbUrl, String comicsUrl) {
        this.title = title;
        this.engTitle = engTitle;
        this.thumbUrl = thumbUrl;
        this.comicsUrl = comicsUrl;
    }

    public Comics(String title, String thumbUrl, String comicsUrl) {
        this.title = title;
        this.engTitle = "eng title lol";
        this.thumbUrl = thumbUrl;
        this.comicsUrl = comicsUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEngTitle() {
        return engTitle;
    }

    public void setEngTitle(String engTitle) {
        this.engTitle = engTitle;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getComicsUrl() {
        return comicsUrl;
    }

    public void setComicsUrl(String comicsUrl) {
        this.comicsUrl = comicsUrl;
    }
}
