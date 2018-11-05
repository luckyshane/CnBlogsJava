package com.luckyshane.cnblogs.model.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "entry")
public final class CommentEntry {
    @Element(name = "id")
    public String id;
    @Element(name = "published")
    public String published;
    @Element(name = "updated")
    public String updated;
    @Element(name = "author")
    public Author author;
}
