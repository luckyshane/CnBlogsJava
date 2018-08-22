package com.luckyshane.cnblogs.model.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "author", strict = false)
public class Author implements Serializable {
    @Element(name = "name", required = false)
    public String name;
    @Element(name = "uri", required = false)
    public String uri;
    @Element(name = "avatar", required = false)
    public String avatar;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Author {");
        stringBuilder.append("name: " + name + ", ");
        stringBuilder.append("uri: " + uri + ", ");
        stringBuilder.append("avatar: " + avatar);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
