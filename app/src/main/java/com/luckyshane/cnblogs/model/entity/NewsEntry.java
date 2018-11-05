package com.luckyshane.cnblogs.model.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;

@Root(name = "entry", strict = false)
public final class NewsEntry {
    @Element(name = "id")
    public String id;
    @Element(name = "title")
    public String title;
    @Element(name = "summary", data = true)
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

    @Element(name = "topicIcon", required = false)
    public String topicIcon;

    @Element(name = "sourceName")
    public String sourceName;

    public Date publishDate;

    public Date updateDate;

}
