package com.luckyshane.cnblogs.model;

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

}
