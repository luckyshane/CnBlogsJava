package com.luckyshane.cnblogs.model.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "feed", strict = false)
public class BlogResponse {

    @Element(name = "title", required = false)
    public String title;

    @Element(name = "updated")
    public String updateTimeStamp;

    @ElementList(name = "entry", inline = true)
    public List<BlogEntry> blogEntryList;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BlogResponse {");
        sb.append("title: " + title + ", ");
        sb.append("updated: " + updateTimeStamp + ", ");
        if (blogEntryList != null) {
            sb.append("blogEntryList: {");
            for (BlogEntry entry : blogEntryList) {
                sb.append(entry + "\n");
            }
            sb.append("}");
        }
        sb.append("}");
        return sb.toString();
    }
}
