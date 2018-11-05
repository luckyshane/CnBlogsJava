package com.luckyshane.cnblogs.model.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "NewsBody", strict = false)
public class NewsContentResponse {
    @Element(name = "Title")
    public String title;
    @Element(name = "SourceName")
    public String sourceName;
    @Element(name = "SubmitDate")
    public String submitDate;
    @Element(name = "Content")
    public String content;
    @Element(name = "PrevNews")
    public String prevNewsId;
    @Element(name = "NextNews")
    public String nextNewsId;
    @Element(name = "CommentCount")
    public int commentCount;

}
