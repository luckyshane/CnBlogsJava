package com.luckyshane.cnblogs.model.entity;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "feed", strict = false)
public class CommentResponse {

    @ElementList(name = "entry", inline = true)
    public List<CommentEntry> comments;

}
