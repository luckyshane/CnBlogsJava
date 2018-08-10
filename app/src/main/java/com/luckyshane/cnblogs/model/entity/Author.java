package com.luckyshane.cnblogs.model.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "author", strict = false)
public class Author {
    @Element(name = "name")
    public String name;
    @Element(name = "uri")
    public String uri;
    @Element(name = "avatar")
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
