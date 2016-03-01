package com.example.notimportant.unicomicsviewer.POJO;

/**
 * Created by NotimPortant on 20.02.2016.
 */
public class Series {
    private String title;
    private String engTitle;
    private String thumbURL;
    private String seriesURL;

    public Series(String title, String engTitle, String thumbURL, String seriesURL) {
        this.title = title;
        this.engTitle = engTitle;
        this.thumbURL = thumbURL;
        this.seriesURL = seriesURL;
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

    public String getThumbURL() {
        return thumbURL;
    }

    public void setThumbURL(String thumbURL) {
        this.thumbURL = thumbURL;
    }

    public String getSeriesURL() {
        return seriesURL;
    }

    public void setSeriesURL(String seriesURL) {
        this.seriesURL = seriesURL;
    }
}
