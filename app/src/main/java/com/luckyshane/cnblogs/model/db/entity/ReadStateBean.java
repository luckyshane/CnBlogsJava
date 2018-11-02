package com.luckyshane.cnblogs.model.db.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;

@Entity
public class ReadStateBean {
    @Id
    public long id;

    @Unique
    public String blogId;

    public ReadStateBean(long id, String blogId) {
        this.id = id;
        this.blogId = blogId;
    }
}
