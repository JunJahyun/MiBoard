package com.midasit.miboard;

/**
 * Created by JiyoungPark on 2017. 1. 7..
 */
public class ReadData {
    private String id, title, content, imageName;

    public ReadData() {
        this.id = null;
        this.title = null;
        this.content = null;
        this.imageName = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
