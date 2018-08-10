package com.luckyshane.cnblogs.model.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "feed", strict = false)
public class CommentResponse {

    @ElementList(name = "entry", inline = true)
    public List<Entry> comments;

    @Element(name = "entry")
    public static final class Entry {
        @Element(name = "id")
        public String id;
        @Element(name = "published")
        public String published;
        @Element(name = "updated")
        public String updated;
        @Element(name = "author")
        public Author author;
    }


}
