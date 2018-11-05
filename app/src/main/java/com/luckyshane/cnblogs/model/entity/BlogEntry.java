package com.luckyshane.cnblogs.model.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "entry", strict = false)
public class BlogEntry implements Serializable {

    @Element(name = "id")
    public String id;

    @Element(name = "title")
    public String title;

    @Element(name = "summary")
    public String summary;

    @Element(name = "published")
    public String publishedTimeStamp;

    @Element(name = "updated")
    public String updateTimeStamp;

    @Element(name = "diggs")
    public int raiseCount;

    @Element(name = "views")
    public int viewCount;

    @Element(name = "comments")
    public int commentCount;

    @Element(name = "author")
    public Author author;

    @Element(name = "blogapp", required = false)
    public String blogApp;

    /**
     * 是否已经阅读过
     */
    public boolean isRead;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("entry {");
        stringBuilder.append("id: " + id + ", ");
        stringBuilder.append("title: " + title + ", ");
        stringBuilder.append("summary: " + summary + ", ");
        stringBuilder.append("author: " + author + ", ");
        stringBuilder.append("publishedTimeStamp: " + publishedTimeStamp + ", ");
        stringBuilder.append("updateTimeStamp: " + updateTimeStamp + ", ");
        stringBuilder.append("raiseCount: " + raiseCount + ", ");
        stringBuilder.append("viewCount: " + viewCount + ", ");
        stringBuilder.append("commentCount: " + commentCount + "");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }


}
