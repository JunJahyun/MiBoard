package com.midasit.miboard;

/**
 * Created by JiyoungPark on 2017. 1. 5..
 */
public class MiboardData {
    protected String id, title, content, imageName;

    public MiboardData() {
        this.id = UserInfo.getInstance().id;
        this.title = null;
        this.content = null;
        this.imageName = null;
    }

    public String getId() {
        return id;
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
